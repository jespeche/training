package com.training.project.service.implementation

import com.training.project.aspect.CheckedParameter
import com.training.project.service.MailService
import org.springframework.stereotype.Service

@Service
class MailServiceImpl : MailService {
    @CheckedParameter
    override fun sendEmail(email: String) {
        println("Sending email")
    }

    @CheckedParameter
    override fun doSomething(other: Int) {
        println("Should't apply")
    }
}