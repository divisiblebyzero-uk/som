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

package uk.co.divisiblebyzero.som.clientgateway.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment
@CrossOrigin(origins = ["http://localhost:4200", "http://localhost:8888"])
@RepositoryRestResource(collectionResourceRel = "receive-against-payments", path = "receive-against-payments")
interface ReceiveAgainstPaymentRepository : PagingAndSortingRepository<ReceiveAgainstPayment, Long> {
}