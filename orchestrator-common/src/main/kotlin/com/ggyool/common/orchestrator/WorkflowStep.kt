package com.ggyool.common.orchestrator

import com.ggyool.common.messages.Response

interface WorkflowStep<T : Response> :
    RequestSender,
    RequestCompensator,
    ResponseProcessor<T>,
    WorkflowChain