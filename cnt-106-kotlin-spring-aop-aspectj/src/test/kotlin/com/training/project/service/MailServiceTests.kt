package com.training.project.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MailServiceTests {

    @Autowired
    lateinit var service: MailService

    @Test
    fun `Check service`() {
        service.sendEmail("info@nytimes.com")
    }

    @Test
    fun `Check aspect`() {
        assertThrows<IllegalArgumentException> { service.sendEmail("") }
    }
}
