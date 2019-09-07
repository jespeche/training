package com.training.project

import com.training.project.messaging.EmployeeHired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.Date
import java.util.UUID

@SpringBootApplication
@EnableKafka
@EnableScheduling
class SpringApplication(val kafkaTemplate: KafkaTemplate<String, Any>) {

    @Scheduled(fixedDelay = 1000)
    fun periodicTask() {
        val timestamp = Date(System.currentTimeMillis())
        println("Publishing event at $timestamp")
        kafkaTemplate.send("office", EmployeeHired(UUID.randomUUID(), "John [Sent $timestamp]"));
    }
}

fun main(args: Array<String>) {
    runApplication<SpringApplication>(*args)
}
