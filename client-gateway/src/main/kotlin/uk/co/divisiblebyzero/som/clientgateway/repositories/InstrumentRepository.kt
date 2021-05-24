package uk.co.divisiblebyzero.som.clientgateway.repositories

import org.springframework.data.repository.CrudRepository
import uk.co.divisiblebyzero.som.clientgateway.model.Instrument

interface InstrumentRepository : CrudRepository<Instrument, Long> {
    fun findAllBySymbol(symbol: String): Iterable<Instrument>
}