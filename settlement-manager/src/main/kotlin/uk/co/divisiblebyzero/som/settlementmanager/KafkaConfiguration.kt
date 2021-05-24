/*
 *     Copyright (C) 2021  Divisible By Zero
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package uk.co.divisiblebyzero.som.settlementmanager

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