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

package uk.co.divisiblebyzero.som.clientgateway.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import uk.co.divisiblebyzero.som.clientgateway.DummySettlementManager
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment
import java.util.concurrent.TimeUnit

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = [ "listeners=PLAINTEXT://localhost:9092", "port=9092" ])
class ClientGatewayApplicationTests() {

	@Autowired
    lateinit var controller: ClientRequestController

	@Autowired
    lateinit var manager: DummySettlementManager

    @Test
	fun sendInvalidMessage() {
		val request = "{\"id\": 12312, \"senderReference\": \"REF001\",\"senderPartyId\": \"CLIENTX\",\"senderAccountId\": \"ACC001\",\"instrumentId\": \"X\",\"receiverPartyId\": \"COUNTERPARTYX\",  \"receiverAccountId\": \"ACC100\",\"currency\": \"GBP\",\"amount\": 50000}"
		val objectMapper = jacksonObjectMapper()
		val rap: ReceiveAgainstPayment = objectMapper.readValue(request)
		val response = controller.processRequestPost(rap)
		manager.latch.await(100, TimeUnit.MILLISECONDS)

		assertEquals(manager.latch.count, 1)
		assertEquals(response.status, "REJECTED")
		assertNotNull(response.message)
	}


	@Test
	fun sendMessage() {
		val request = "{\"id\": 12312, \"senderReference\": \"REF001\",\"senderPartyId\": \"CLIENT1\",\"senderAccountId\": \"ACC001\",\"instrumentId\": \"US02079K1079\",\"receiverPartyId\": \"COUNTERPARTY1\",  \"receiverAccountId\": \"ACC100\",\"currency\": \"GBP\",\"amount\": 50000}"
		val objectMapper = jacksonObjectMapper()
		val rap: ReceiveAgainstPayment = objectMapper.readValue(request)

		controller.processRequestPost(rap)

		manager.latch.await(10000, TimeUnit.MILLISECONDS)

		assertEquals(manager.latch.count, 0)
		assertNotNull(manager.request)
		assertEquals(manager.request!!.id, rap.id)
		assertEquals("ACCEPTED", manager.response!!.status)
	}

}