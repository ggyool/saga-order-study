package com.ggyool.common.util

import com.ggyool.common.exception.EventAlreadyProcessedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val log: Logger = LoggerFactory.getLogger("DuplicateEventHelper")

suspend fun <T> validateDuplicateEvent(
    isDuplicate: suspend () -> Boolean,
    process: suspend () -> T
): T {
    if (isDuplicate()) {
        log.warn("Duplicate event")
        throw EventAlreadyProcessedException()
    }
    return process()
}