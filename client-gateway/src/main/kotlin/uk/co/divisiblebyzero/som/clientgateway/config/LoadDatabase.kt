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

package uk.co.divisiblebyzero.som.clientgateway.config

import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.divisiblebyzero.som.clientgateway.model.Account
import uk.co.divisiblebyzero.som.clientgateway.model.Instrument
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment
import uk.co.divisiblebyzero.som.clientgateway.repositories.AccountRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.InstrumentRepository
import uk.co.divisiblebyzero.som.clientgateway.repositories.ReceiveAgainstPaymentRepository

@Configuration
class LoadDatabase {
    private val logger = KotlinLogging.logger {}

    fun initInstruments(instrumentRepository: InstrumentRepository) {
        instrumentRepository.save(Instrument(symbol = "US02079K1079", description = "Alphabet Inc C Class"))
    }

    fun initReceiveAgainstPayments(receiveAgainstPaymentRepository: ReceiveAgainstPaymentRepository) {
        val rap1 = ReceiveAgainstPayment(
                senderReference = "REF001",
                senderPartyId = "CLIENT1",
                senderAccountId = "ACC001",
                instrumentId = "US02079K1079",
                receiverPartyId = "COUNTERPARTY1",
                receiverAccountId = "ACC100",
                currency = "USD",
                amount = 50000.0)
        receiveAgainstPaymentRepository.save(rap1)
    }

    fun initAccounts(accountRepository: AccountRepository) {
        accountRepository.save(Account(accountId = "ACC001", partyId = "CLIENT1"))
        accountRepository.save(Account(accountId = "ACC002", partyId = "CLIENT1"))
        accountRepository.save(Account(accountId = "ACC100", partyId = "COUNTERPARTY1"))
    }

    @Bean
    fun initClientDatabase(receiveAgainstPaymentRepository: ReceiveAgainstPaymentRepository,
                           accountRepository: AccountRepository,
                           instrumentRepository: InstrumentRepository) = CommandLineRunner {
        initInstruments(instrumentRepository)
        initReceiveAgainstPayments(receiveAgainstPaymentRepository)
        initAccounts(accountRepository)
    }
}