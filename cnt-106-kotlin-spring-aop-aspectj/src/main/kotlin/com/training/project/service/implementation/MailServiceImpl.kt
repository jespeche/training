package com.training.project.service.implementation

import com.training.project.service.MailService
import org.springframework.stereotype.Service

@Service
class MailServiceImpl : MailService {
    override fun sendEmail(email: String) {
        println("Sending email")
    }
}