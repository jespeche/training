package com.training.project.services.mailing

import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class MailingService {
    fun sendWelcomeMail(email: String) {
        println("[ Mailing Service ] - Welcome mail is being sent $email.")
        sleep(5000)
        println("[ Mailing Service ] - Welcome email was sent to $email.")
    }
}