package uk.co.divisiblebyzero.som.clientgateway.controllers

import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import uk.co.divisiblebyzero.som.clientgateway.model.Account
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment
import uk.co.divisiblebyzero.som.clientgateway.repositories.AccountRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.InstrumentRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.ReceiveAgainstPaymentRepository

@RestController
class ClientRequestController(
        val rapRepository: ReceiveAgainstPaymentRepository,
        val instrumentRepository: InstrumentRepository,
        val accountRepository: AccountRepository,
        val kafkaTemplate: KafkaTemplate<String, Any>) {


    @GetMapping("/request")
    fun index(): String {
        return "received"
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

    @PostMapping("/request")
    fun processRequest(@RequestBody receiveAgainstPayment: ReceiveAgainstPayment): String {
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
    }

}