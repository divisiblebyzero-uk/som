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
import io.camunda.zeebe.client.api.response.DeploymentEvent
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class WorkflowDeploymentService(
    private val zeebeClient: ZeebeClient
) {
    private val logger = KotlinLogging.logger {}

    fun deployWorkflow() {
        logger.info("deploying workflow...")
        val deployment: DeploymentEvent = zeebeClient.newDeployCommand()
            .addResourceFromClasspath("Transaction Workflow.bpmn")
            .send()
            .join()

        val version = deployment.processes.get(0).getVersion()

        logger.info("Deployment complete. Version: {}", version)
    }

    fun configureConnectors() {
        logger.info("configuring connectors")
        // curl -X POST -H "Content-Type: application/json" --data @kafka-connect-zeebe-source.json http://localhost:8083/connectors
        // curl -X POST -H "Content-Type: application/json" --data @kafka-connect-zeebe-sink.json http://localhost:8083/connectors
    }

}