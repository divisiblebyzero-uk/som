package uk.co.divisiblebyzero.som.clientgateway.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Instrument (@Id @GeneratedValue val id: Long? = null, val symbol: String, val description: String)