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

import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment
import uk.co.divisiblebyzero.som.clientgateway.model.RequestResponse
import uk.co.divisiblebyzero.som.clientgateway.repositories.AccountRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.InstrumentRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.ReceiveAgainstPaymentRepository


@CrossOrigin(origins = ["http://localhost:4200", "http://localhost:8888"])
@RestController
class ClientRequestController(
        val rapRepository: ReceiveAgainstPaymentRepository,
        val instrumentRepository: InstrumentRepository,
        val accountRepository: AccountRepository,
        val kafkaTemplate: KafkaTemplate<String, Any>) {
    private val logger = KotlinLogging.logger {}

    fun validateInstrument(receiveAgainstPayment: ReceiveAgainstPayment, errors: MutableList<String>) {
        if (instrumentRepository.findAllBySymbol(receiveAgainstPayment.instrumentId).count() != 1) {
            errors.add("Invalid Instrument")
        }
    }

    fun validateAccount(accountId: String, partyId: String, errors: MutableList<String>) {
        if (accountRepository.findByAccountIdAndPartyId(accountId, partyId).count() != 1) {
            errors.add("Invalid account/party combination")
        }
    }

    fun validateSenderAccount(receiveAgainstPayment: ReceiveAgainstPayment, errors: MutableList<String>) {
        validateAccount(receiveAgainstPayment.senderAccountId, receiveAgainstPayment.senderPartyId, errors)
    }

    fun validateReceiverAccount(receiveAgainstPayment: ReceiveAgainstPayment, errors: MutableList<String>) {
        validateAccount(receiveAgainstPayment.receiverAccountId, receiveAgainstPayment.receiverPartyId, errors)
    }


    @MessageMapping("/settlement-request")
    @SendTo("/topic/settlement-request-response")
    @PostMapping("/requestARAP")
    fun processRequestPost(@RequestBody receiveAgainstPayment: ReceiveAgainstPayment): RequestResponse {
        logger.info("Receive post request")
        rapRepository.save(receiveAgainstPayment)
        val errors: MutableList<String> = ArrayList<String>()
        validateInstrument(receiveAgainstPayment, errors)
        validateSenderAccount(receiveAgainstPayment, errors)
        validateReceiverAccount(receiveAgainstPayment, errors)
        if (errors.size > 0) {
            return RequestResponse(originalRequest = receiveAgainstPayment, status = "REJECTED", message = errors[0])
        }

        sendMessage(receiveAgainstPayment, receiveAgainstPayment.id.toString(), "settlement-manager.request")
        return RequestResponse(originalRequest = receiveAgainstPayment, status = "ACCEPTED", message = "ACCEPTED")
    }

    fun sendMessage(payload: ReceiveAgainstPayment, id: String, topic: String) {

        val listenableFuture = kafkaTemplate.send("settlement-manager.request", id, payload)

        listenableFuture.addCallback(object : ListenableFutureCallback<SendResult<String, Any>?> {
            override fun onSuccess(result: SendResult<String, Any>?) {
                logger.info {
                    "Sent message=[$payload] to topic=[/settlement-manager.request] with offset=[result.recordMetadata.offset()]"
                }
            }

            override fun onFailure(ex: Throwable) {
                logger.error(ex) {
                    "Unable to send message=[$payload] due to: $ex"
                }
            }
        })
    }

}