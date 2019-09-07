package com.training.project.aspect

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class ValidationAspect {

    @Pointcut("execution(* com.training.project.service.MailService.sendEmail (java.lang.String))")
    fun sendMail() {
    }

    @Before("sendMail() && args(email)")
    fun beforeSendMail(email: String) {
        println("Executing aspect")
        if (email.isBlank()) {
            throw IllegalArgumentException("Email must not be blank!")
        }
    }
}
