package com.training.project.aspect

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import com.training.project.logger.Logger.aspect as Logger

@Aspect
@Component
class ValidationAspect {

    @Pointcut("@annotation(CheckedParameter)")
    private fun annotated() {
    }

    @Pointcut("execution(* com.training.project.service.MailService..* (..))")
    private fun mailServiceMethod() {
    }

    @Before("mailServiceMethod() && annotated() && args(email)")
    fun beforeMailFunctionExecution(email: String) {
        Logger.info("Processing advice beforeMailFunctionExecution")
        if (email.isBlank()) {
            Logger.info("Invalid argument detected")
            throw IllegalArgumentException("Email must not be blank!")
        }
        Logger.info("Processing advice beforeMailFunctionExecution finished")
    }
}
