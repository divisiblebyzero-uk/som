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
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaConfiguration {

    @Value("\${som.settlement-manager.kafka.topic.request}")
    private val requestTopic: String = "request"

    @Value("\${som.settlement-manager.kafka.topic.response}")
    private val responseTopic: String = "response"

    @Bean
    fun requestTopic(): NewTopic {
        return TopicBuilder.name(requestTopic)
            .compact()
            .build()
    }

    @Bean
    fun responseTopic(): NewTopic {
        return TopicBuilder.name(responseTopic)
            .partitions(1)
            .replicas(1)
            .compact()
            .build()
    }


}