import { Component, OnInit } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { CommsService } from 'src/app/comms.service';
import { Message } from '@stomp/stompjs';
import { ReceiveAgainstPayment } from 'src/app/entities';

@Component({
  selector: 'app-settlement-status',
  templateUrl: './settlement-status.component.html',
  styleUrls: ['./settlement-status.component.scss']
})
export class SettlementStatusComponent implements OnInit {

  constructor(private comms: CommsService, private rxStompService: RxStompService) { }

  receiveAgainstPayments: ReceiveAgainstPayment[] = [];

  public receivedMessages: string[] = [];

  ngOnInit(): void {
    this.getReceiveAgainstPayments();
  }

  getReceiveAgainstPayments() {
    this.comms.getReceiveAgainstPayments().subscribe((data) => this.receiveAgainstPayments = data['_embedded']['receive-against-payments']);
    this.comms.receiveAgainstPayments$.subscribe((rap) => {
      console.log("Component received: " + JSON.stringify(rap));
      this.receiveAgainstPayments.push(rap)});
  }

  send() {
    this.comms.sendDummyReceiveAgainstPayment();
  }

}
