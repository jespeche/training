package com.training.project.messaging.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Configuration
@Import(EmbeddedKafkaConfig::class)
internal annotation class EnableEmbeddedKafka