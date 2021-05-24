package uk.co.divisiblebyzero.som.clientgateway.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.stereotype.Service
import uk.co.divisiblebyzero.som.clientgateway.model.ReceiveAgainstPayment

@RepositoryRestResource(collectionResourceRel = "receive-against-payments", path = "receive-against-payments")
interface ReceiveAgainstPaymentRepository : PagingAndSortingRepository<ReceiveAgainstPayment, Long> {
}