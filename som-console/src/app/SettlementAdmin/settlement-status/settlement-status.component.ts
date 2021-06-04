import { Component, OnInit } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { CommsService } from 'src/app/comms.service';
import { Message } from '@stomp/stompjs';

@Component({
  selector: 'app-settlement-status',
  templateUrl: './settlement-status.component.html',
  styleUrls: ['./settlement-status.component.scss']
})
export class SettlementStatusComponent implements OnInit {

  constructor(private comms: CommsService, private rxStompService: RxStompService) { }

  receiveAgainstPayments;

  public receivedMessages: string[] = [];

  ngOnInit(): void {
    this.getReceiveAgainstPayments();
    this.rxStompService.watch('/topic/settlement-request-response').subscribe((message: Message) => {
      this.receivedMessages.push(message.body);
      console.log(message.body);
    })
  }

  getReceiveAgainstPayments() {
    this.comms.getReceiveAgainstPayments().subscribe((data) => this.receiveAgainstPayments = data['_embedded']['receive-against-payments'])
  }

  send() {
    this.comms.sendDummyReceiveAgainstPayment();
  }

}
