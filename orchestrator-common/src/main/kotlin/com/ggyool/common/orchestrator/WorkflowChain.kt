package com.ggyool.common.orchestrator

interface WorkflowChain {

    fun setPreviousStep(previousStep: RequestCompensator)

    fun setNextStep(nextStep: RequestSender)
}