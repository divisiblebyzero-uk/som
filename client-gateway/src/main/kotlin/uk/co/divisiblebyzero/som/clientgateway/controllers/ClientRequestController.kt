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

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import uk.co.divisiblebyzero.som.clientgateway.model.HelloMessage
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment
import uk.co.divisiblebyzero.som.clientgateway.model.RequestResponse
import uk.co.divisiblebyzero.som.clientgateway.repositories.AccountRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.InstrumentRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.ReceiveAgainstPaymentRepository


@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
class ClientRequestController(
        val rapRepository: ReceiveAgainstPaymentRepository,
        val instrumentRepository: InstrumentRepository,
        val accountRepository: AccountRepository,
        val kafkaTemplate: KafkaTemplate<String, Any>) {
    private val logger = KotlinLogging.logger {}


    @MessageMapping("/settlement-request")
    @SendTo("/topic/settlement-request-response")
    fun processRequest(receiveAgainstPayment: ReceiveAgainstPayment): RequestResponse {
        logger.info("Processing request")
        rapRepository.save(receiveAgainstPayment)
        if (!validateInstrument(receiveAgainstPayment)) {
            return RequestResponse(originalRequest = receiveAgainstPayment, status = "Error", message = "Invalid Instrument")
        }
        if (!validateSenderAccount(receiveAgainstPayment)) {
            return RequestResponse(originalRequest = receiveAgainstPayment, status = "Error", message = "Invalid Sender Account")
        }
        if (!validateReceiverAccount(receiveAgainstPayment)) {
            return RequestResponse(originalRequest = receiveAgainstPayment, status = "Error", message = "Invalid Receiver Account")
        }
        kafkaTemplate.send("settlement-manager.request", receiveAgainstPayment)
        return RequestResponse(originalRequest = receiveAgainstPayment, status = "Accepted", message = "Accepted")
    }

    fun validateInstrument(receiveAgainstPayment: ReceiveAgainstPayment): Boolean {
        return (instrumentRepository.findAllBySymbol(receiveAgainstPayment.instrumentId).count() == 1)
    }

    fun validateAccount(accountId: String, partyId: String): Boolean {
        return (accountRepository.findByAccountIdAndPartyId(accountId, partyId).count() == 1)
    }

    fun validateSenderAccount(receiveAgainstPayment: ReceiveAgainstPayment): Boolean {
        return validateAccount(receiveAgainstPayment.senderAccountId, receiveAgainstPayment.senderPartyId)
    }

    fun validateReceiverAccount(receiveAgainstPayment: ReceiveAgainstPayment): Boolean {
        return validateAccount(receiveAgainstPayment.receiverAccountId, receiveAgainstPayment.receiverPartyId)
    }

/*    @PostMapping("/requestARAP")
    fun processRequestPost(@RequestBody receiveAgainstPayment: ReceiveAgainstPayment): String {
        logger.info("Receive post request")
        rapRepository.save(receiveAgainstPayment)
        if (!validateInstrument(receiveAgainstPayment)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid instrument")
        }
        if (!validateSenderAccount(receiveAgainstPayment)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sender account")
        }
        if (!validateReceiverAccount(receiveAgainstPayment)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid receiver account")
        }
        kafkaTemplate.send("settlement-manager.request", receiveAgainstPayment)
        return receiveAgainstPayment.toString()
    }*/

}