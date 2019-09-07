package com.training.project.services.user

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class UserService(val publisher: ApplicationEventPublisher) {
    fun register(user: String, email: String, password: String) {
        println("[ User Service ] - User $user with email $email was registered.")
        publish(UserRegistered(user, email))
    }

    fun unregister(user: String) {
        println("[ User Service ] - User $user was Unregistered.")
        publish(UserUnregistered(user))
    }

    fun publish(event: UserServiceEvent) = publisher.publishEvent(event)
}

interface UserServiceEvent
data class UserRegistered(val user: String, val email: String): UserServiceEvent
data class UserUnregistered(val user: String): UserServiceEvent
