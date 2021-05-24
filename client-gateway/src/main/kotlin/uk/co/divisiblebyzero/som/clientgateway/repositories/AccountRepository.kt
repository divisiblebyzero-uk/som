package uk.co.divisiblebyzero.som.clientgateway.repositories

import org.springframework.data.repository.CrudRepository
import uk.co.divisiblebyzero.som.clientgateway.model.Account

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByAccountIdAndPartyId(accountId: String, partyId: String): Iterable<Account>

}