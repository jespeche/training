package com.training.project.services

import com.training.project.services.user.UserRegistered
import com.training.project.services.user.UserUnregistered
import org.springframework.context.event.EventListener
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.scheduling.annotation.Async

interface TestEventHandler {

    @Async
    @EventListener
    fun handleUserRegistered(event: UserRegistered)

    @EventListener
    fun handleUserUnregistered(event: UserUnregistered)
}