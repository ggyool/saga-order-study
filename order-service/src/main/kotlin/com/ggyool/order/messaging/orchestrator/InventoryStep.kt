package com.ggyool.order.messaging.orchestrator

import com.ggyool.common.messages.Request
import com.ggyool.common.messages.inventory.InventoryResponse
import com.ggyool.common.orchestrator.WorkflowStep
import kotlinx.coroutines.flow.Flow

interface InventoryStep : WorkflowStep<InventoryResponse> {


    override fun process(response: InventoryResponse): Flow<Request> {
        return when (response) {
            is InventoryResponse.Deducted -> this.onSuccess(response)
            is InventoryResponse.Declined -> this.onFailure(response)
        }
    }

    fun onSuccess(response: InventoryResponse.Deducted): Flow<Request>

    fun onFailure(response: InventoryResponse.Declined): Flow<Request>
}