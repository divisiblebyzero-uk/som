import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Observable, Subject } from 'rxjs';
import { environment } from '../environments/environment';
import { ReceiveAgainstPayment, RequestResponse } from './entities';
import { Message } from '@stomp/stompjs';
import { map } from 'rxjs/operators'

import {RxStompState} from '@stomp/rx-stomp';

@Injectable({
  providedIn: 'root'
})
export class CommsService {


  public connectionStatus$: Observable<string>
  public receiveAgainstPayments$: Subject<ReceiveAgainstPayment> = new Subject<ReceiveAgainstPayment>()

  clientGatewayUrl = environment.apiUrl + '/api';

  constructor(private http: HttpClient, private rxStompService: RxStompService) {
    this.connectionStatus$ = this.rxStompService.connectionState$.pipe(
      map(state => {
        // convert numeric RxStompState to string
        return RxStompState[state];
      })
    );
    this.setupSubscriptions();
   }

  setupSubscriptions() {
    console.log("Subscribing...");
    this.rxStompService.watch('/topic/settlement-request-response').subscribe((message: Message) => {
      const requestResponse: RequestResponse = JSON.parse(message.body) as RequestResponse;
      if (requestResponse.status == "ACCEPTED") {
        this.receiveAgainstPayments$.next(requestResponse.originalRequest);
      } else {
        console.log("Error: " + JSON.stringify(requestResponse));
      }
    })
  }

  getReceiveAgainstPayments() {
    return this.http.get(this.clientGatewayUrl + "/receive-against-payments");
  }

  sendDummyReceiveAgainstPayment() {
    const payload: ReceiveAgainstPayment = {"id": 12312, "senderReference": "REF001","senderPartyId": "CLIENT1","senderAccountId": "ACC001","instrumentId": "US02079K1079","receiverPartyId": "COUNTERPARTY1",  "receiverAccountId": "ACC100","currency": "GBP","amount": 50000};
    this.sendReceiveAgainstPayment(payload);
  }

  sendReceiveAgainstPayment(payload: ReceiveAgainstPayment) {
    payload.status = "NEW";
    this.rxStompService.publish({destination: '/app/settlement-request', body: JSON.stringify(payload) });
  }
  
}
