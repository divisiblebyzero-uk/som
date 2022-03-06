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
import org.springframework.stereotype.Service

@Service
class WorkflowInstantiationService (
    private val zeebeClient: ZeebeClient
) {
    private val logger = KotlinLogging.logger {}

    fun instantiateWorkflow() {
        logger.info("instantiating workflow...")
        val data: MutableMap<String, Any> = HashMap()
        data["transactionId"] = 1234;
        data["transactionType"] = "dvp"


        val wfInstance = zeebeClient.newCreateInstanceCommand()
            .bpmnProcessId("settlement-process")
            .latestVersion()
            .variables(data)
            .send()
            .join();
        val wfInstanceKey = wfInstance.processInstanceKey;

        logger.info("Instantiation complete. Key: {}", wfInstanceKey)
    }


}