package uk.co.divisiblebyzero.som.clientgateway.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaConfiguration {

    @Bean
    fun admin(): KafkaAdmin {
        val configs = hashMapOf<String, Any>()
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        return KafkaAdmin(configs)
    }

    @Bean
    fun requestTopic(): NewTopic {
        return TopicBuilder.name("settlement-manager.request")
                .partitions(1)
                .replicas(1)
                .compact()
                .build()
    }

    @Bean
    fun responseTopic(): NewTopic {
        return TopicBuilder.name("settlement-manager.response")
                .partitions(1)
                .replicas(1)
                .compact()
                .build()
    }


}