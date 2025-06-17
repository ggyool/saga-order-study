package com.ggyool.order.application.entity

import com.ggyool.order.common.enums.WorkflowAction
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("order_workflow_action")
class OrderWorkflowAction(
    @Id
    private var id: UUID? = null,
    val orderId: UUID,
    val action: WorkflowAction,
    val createdAt: Instant,
) : Persistable<UUID> {

    override fun getId(): UUID? = orderId

    override fun isNew(): Boolean {
        val isNew = id == null
        this.id = if (isNew) UUID.randomUUID() else this.id
        return isNew
    }
}
