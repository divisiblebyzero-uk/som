package uk.co.divisiblebyzero.som.settlementmanager

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Controller

@Controller
class RequestListener {
    @KafkaListener(id = "settlementmanager", topics = ["settlement-manager.request"])
    fun listen(input: String) {
        System.out.println(input)
    }
}