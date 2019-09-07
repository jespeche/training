package com.training.project.messaging.handlers

import com.training.project.messaging.EmployeeHired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(
    id = "consumers-2",
    topics = ["office"]
)
internal class KafkaReceiverTwo {

    @KafkaHandler
    fun handle(event: EmployeeHired) {
        println("[KafkaReceiverTwo] Event received $event")
    }
}