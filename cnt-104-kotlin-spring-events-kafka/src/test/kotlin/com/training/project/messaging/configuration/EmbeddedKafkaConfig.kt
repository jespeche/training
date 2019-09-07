package com.training.project.messaging.configuration

import org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.utils.KafkaTestUtils.consumerProps
import org.springframework.kafka.test.utils.KafkaTestUtils.producerProps

/**
 * Configuration for enabling EmbeddedKafka.
 *
 * References:
 * https://objectpartners.com/2018/08/21/testing-with-spring-kafka-and-mockschemaregistryclient/
 */
@Configuration
@EnableKafka
internal class EmbeddedKafkaConfig(@Autowired private val embeddedKafka: EmbeddedKafkaBroker) {

    @Bean(value = ["globalKafkaConsumerConfigMap"])
    fun kafkaConsumerConfigMap(): Map<String, Any> {
        val consumerGroupId = "embedded-kakfa-test-config-group"
        return consumerProps(consumerGroupId, "true", embeddedKafka).apply {
            putAll(mapOf(
                KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
                VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java.name
            ))
        }
    }

    fun kafkaProducerConfigMap(): Map<String, Any> =
        producerProps(embeddedKafka).apply {
            putAll(mapOf(
                KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.name,
                VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java.name
            ))
        }

    /**
     * Configures the kafka producer factory to use the overridden KafkaAvroDeserializer so that the
     * MockSchemaRegistryClient is used rather than trying to reach out via HTTP to a schema registry
     * @return DefaultKafkaProducerFactory instance
     */
    @Bean
    fun producerFactory(): DefaultKafkaProducerFactory<String, Any> =
        DefaultKafkaProducerFactory(kafkaProducerConfigMap(), StringSerializer(), JsonSerializer())

    /**
     * A default kakfa template.
     */
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Any> = KafkaTemplate(producerFactory()).apply {
        defaultTopic = "office"
    }

    /**
     * Configures the kafka consumer factory to use the overridden KafkaAvroSerializer so that the
     * MockSchemaRegistryClient is used rather than trying to reach out via HTTP to a schema
     * registry
     * @return DefaultKafkaConsumerFactory instance
     */
    @Bean
    fun consumerFactory(): DefaultKafkaConsumerFactory<String, Any> =
        DefaultKafkaConsumerFactory(kafkaConsumerConfigMap(), StringDeserializer(), JsonDeserializer<Any>().apply { addTrustedPackages("com.training.project.messaging") })

    /**
     * Configure the ListenerContainerFactory to use the overridden consumer factory so that the
     * MockSchemaRegistryClient is used under the covers by all consumers when deserializing Avro
     * data.
     * @return ConcurrentKafkaListenerContainerFactory instance
     */
    @Bean
    fun kafkaListenerContainerFactory() =
        ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            consumerFactory = consumerFactory()
        }
}