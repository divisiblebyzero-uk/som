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

package uk.co.divisiblebyzero.som.clientgateway

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment
import java.util.concurrent.CountDownLatch

@Component
class DummySettlementManager(
    var request: ReceiveAgainstPayment? = null,
    var response: ReceiveAgainstPayment? = null,
    var latch: CountDownLatch = CountDownLatch(1),
    @Value("\${som.settlement-manager.kafka.topic.request}") private val requestTopic: String = "request",
    @Value("\${som.settlement-manager.kafka.topic.response}") private val responseTopic: String = "response",
    var kafkaTemplate: KafkaTemplate<String, ReceiveAgainstPayment>,
    ) {

    private val logger = KotlinLogging.logger {}


    @KafkaListener(topics=["\${som.settlement-manager.kafka.topic.request}"])
    fun receive(incoming: ReceiveAgainstPayment) {
        logger.info("Received: ${incoming}")
        request = incoming
        latch.countDown()
        response = incoming.copy(status = "ACCEPTED")
        kafkaTemplate.send(responseTopic, response!!.id.toString(), response)
    }

}