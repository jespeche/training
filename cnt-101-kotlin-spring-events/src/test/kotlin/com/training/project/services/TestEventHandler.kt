package com.training.project.services

import com.training.project.services.user.UserRegistered
import com.training.project.services.user.UserUnregistered
import org.springframework.context.event.EventListener
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order

interface TestEventHandler {

    @EventListener
    @Order(HIGHEST_PRECEDENCE)
    fun handleUserRegistered(event: UserRegistered)

    @EventListener
    fun handleUserUnregistered(event: UserUnregistered)
}