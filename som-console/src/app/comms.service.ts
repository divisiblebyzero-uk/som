import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';

@Injectable({
  providedIn: 'root'
})
export class CommsService {

  clientGatewayUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private rxStompService: RxStompService) { }

  getReceiveAgainstPayments() {
    return this.http.get(this.clientGatewayUrl + "/receive-against-payments");
  }

  sendReceiveAgainstPayment() {
    const payload = {"id": 12312, "senderReference": "REF001","senderPartyId": "CLIENT1","senderAccountId": "ACC001","instrumentId": "US02079K1079","receiverPartyId": "COUNTERPARTY1",  "receiverAccountId": "ACC100","currency": "GBP","amount": 50000};
    const helloPayload = {name: 'Matthew'};
    //this.rxStompService.publish({destination: '/app/hello-request', body: JSON.stringify(helloPayload) });
    this.rxStompService.publish({destination: '/app/settlement-request', body: JSON.stringify(payload) });
  }
  
}
