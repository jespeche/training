package com.training.project.services.mailing

import com.training.project.services.user.UserRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UsersEventHandler(@Autowired val service: MailingService) {

    @EventListener
    @Async
    fun handleUserRegistered(event: UserRegistered) {
        println("[ Mailing Service ] - <UserRegistered> event received for ${event.user}")
        service.sendWelcomeMail(event.email)
        println("[ Mailing Service ] - <UserRegistered> event processed.")
    }
}