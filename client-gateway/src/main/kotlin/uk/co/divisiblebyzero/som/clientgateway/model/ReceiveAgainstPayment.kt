package uk.co.divisiblebyzero.som.clientgateway.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ReceiveAgainstPayment(

        val senderReference: String,
        val senderPartyId: String,
        val senderAccountId: String,
        val instrumentId: String,
        val receiverPartyId: String,
        val receiverAccountId: String,
        val currency: String,
        val amount: Double,
        @Id @GeneratedValue val id: Long? = null
        )
