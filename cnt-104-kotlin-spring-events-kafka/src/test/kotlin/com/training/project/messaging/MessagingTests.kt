package com.training.project.messaging

import com.training.project.messaging.configuration.EnableEmbeddedKafka
import com.training.project.messaging.handlers.KafkaReceiverOne
import com.training.project.messaging.handlers.KafkaReceiverThree
import com.training.project.messaging.handlers.KafkaReceiverTwo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTimeoutPreemptively
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Duration.ofSeconds
import java.util.UUID

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ContextConfiguration(classes = [KafkaReceiverOne::class, KafkaReceiverTwo::class, KafkaReceiverThree::class])
@EmbeddedKafka
@EnableEmbeddedKafka
@TestInstance(PER_CLASS)
internal class MessagingTests(
    @Autowired private val kafka: EmbeddedKafkaBroker,
    @Autowired private val kafkaTemplate: KafkaTemplate<String, Any>
) {

    /**
     * Added to avoid FileNotFoundException that could break the test:
     * https://stackoverflow.com/a/49010500/1499171
     */
    @AfterAll
    fun teardown() {
        kafka.kafkaServers.forEach { it.shutdown() }
        kafka.kafkaServers.forEach { it.awaitShutdown() }
    }

    @Test
    fun test() {
        assertTimeoutPreemptively(ofSeconds(5)) {
            println("Publishing event")
            kafkaTemplate.sendDefault("SOME_ID", EmployeeHired(UUID.randomUUID(), "John")).get()
            println("Publishing event done.")
        }
    }
}