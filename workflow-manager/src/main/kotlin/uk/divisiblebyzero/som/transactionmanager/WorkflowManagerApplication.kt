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

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application(
    private val workflowDeploymentService: WorkflowDeploymentService,
    private val workflowInstantiationService: WorkflowInstantiationService,
    private val validateTransactionWorkerService: ValidateTransactionWorkerService
    ) {

    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner = CommandLineRunner {
        workflowDeploymentService.deployWorkflow()
        workflowInstantiationService.instantiateWorkflow()
        //validateTransactionWorkerService.register()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}