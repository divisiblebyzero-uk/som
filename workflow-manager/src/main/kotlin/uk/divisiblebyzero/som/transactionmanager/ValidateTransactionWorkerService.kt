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
import io.camunda.zeebe.client.api.worker.JobWorker
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ValidateTransactionWorkerService (
    private val zeebeClient: ZeebeClient
) {
    private val logger = KotlinLogging.logger {}

    fun register() {

        val jobWorker: JobWorker = zeebeClient.newWorker()
            .jobType("validate-transaction")
            .handler { jobClient, job ->
                val variables: Map<String, Any> = job.getVariablesAsMap()
                logger.info("Validating transaction: {}", variables["transactionId"])
                logger.info("Validation complete")
                val result: MutableMap<String, Any> = HashMap()
                result["validationStatus"] = "success"
                jobClient.newCompleteCommand(job.getKey())
                    .variables(result)
                    .send()
                    .join()
            }
            .fetchVariables("transactionId")
            .open()

    }



}