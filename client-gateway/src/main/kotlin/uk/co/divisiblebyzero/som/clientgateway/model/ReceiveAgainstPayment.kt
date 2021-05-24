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
