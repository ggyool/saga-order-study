package com.ggyool.order.messaging.orchestrator.impl

import com.ggyool.common.orchestrator.RequestCompensator
import com.ggyool.common.orchestrator.RequestSender
import com.ggyool.common.orchestrator.WorkflowStep
import kotlinx.coroutines.flow.flow
import java.util.UUID

class Workflow private constructor(
    val firstStep: WorkflowStep<*>,
) {

    private var lastStep: WorkflowStep<*> = firstStep

    fun thenNext(newStep: WorkflowStep<*>): Workflow {
        this.lastStep.setNextStep(newStep)
        newStep.setPreviousStep(this.lastStep)
        this.lastStep = newStep
        return this
    }

    fun doOnSuccess(block: suspend (UUID) -> Unit): Workflow {
        lastStep.setNextStep(RequestSender { id ->
            flow {
                block(id)
            }
        })
        return this
    }

    fun doOnFailure(block: suspend (UUID) -> Unit): Workflow {
        firstStep.setPreviousStep(RequestCompensator { id ->
            flow {
                block(id)
            }
        })
        return this
    }

    companion object {
        fun startWith(step: WorkflowStep<*>): Workflow {
            return Workflow(step)
        }
    }
}