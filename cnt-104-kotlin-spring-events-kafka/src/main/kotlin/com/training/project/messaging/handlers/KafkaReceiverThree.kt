package com.training.project.messaging.handlers

import com.training.project.messaging.EmployeeHired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(
    id = "consumers-3",
    topics = ["office"]
)
internal class KafkaReceiverThree {

    @KafkaHandler
    fun handle(event: EmployeeHired) {
        println("[KafkaReceiverThree] Event received $event")
    }
}
