package com.training.project.services.mailing

import com.training.project.services.user.UserRegistered
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UsersEventHander(@Autowired val service: MailingService) {

    @EventListener
    fun handleUserRegistered(event: UserRegistered) {
        println("[ Mailing Service ] - <UserRegistered> event received for ${event.user}")
        service.sendWelcomeMail(event.email)
        println("[ Mailing Service ] - <UserRegistered> event processed.")
    }
}