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

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment

class SerialisationTests {
    private val logger = KotlinLogging.logger {}
    @Test
    fun testDeserialisation() {
        val request = "{\"senderReference\":\"REF001\",\"senderPartyId\":\"CLIENT1\",\"senderAccountId\":\"ACC001\",\"instrumentId\":\"US02079K1079\",\"receiverPartyId\":\"COUNTERPARTY1\",\"receiverAccountId\":\"ACC100\",\"currency\":\"GBP\",\"amount\":50000.0,\"id\":12312,\"status\":\"NEW\"}"
        logger.info("Received: " + request)
        val objectMapper = jacksonObjectMapper()
        val rap: ReceiveAgainstPayment = objectMapper.readValue(request)
        logger.info("Mapped to: " + rap)

    }
}