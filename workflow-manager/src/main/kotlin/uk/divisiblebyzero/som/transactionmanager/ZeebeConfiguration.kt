/*
 *     Copyright (C) 2022  Divisible By Zero
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

package uk.divisiblebyzero.som.transactionmanager

import io.camunda.zeebe.client.ZeebeClient
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ZeebeConfiguration {
    @Value("\${som.transaction-manager.zeebe.address}")
    private val zeebeGatewayAddress: String = "request"

    private val logger = KotlinLogging.logger {}

    @Bean
    fun zeebeClient(): ZeebeClient {
        val client = ZeebeClient.newClientBuilder()
            //.gatewayAddress(zeebeGatewayAddress)
            .usePlaintext()
            .build()
        logger.info("client initiated")
        return client
    };
}