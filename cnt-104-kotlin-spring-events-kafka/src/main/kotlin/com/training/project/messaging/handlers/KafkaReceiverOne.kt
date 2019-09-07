package com.training.project.messaging.handlers

import com.training.project.messaging.EmployeeHired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.util.Date

@Component
@KafkaListener(
    id = "consumers-1",
    topics = ["office"]
)
internal class KafkaReceiverOne {

    @KafkaHandler
    fun handle(event: EmployeeHired, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) ts: Long) {
        println("[KafkaReceiverOne] Event received $event [Received ${Date(ts)}]")
    }
}
