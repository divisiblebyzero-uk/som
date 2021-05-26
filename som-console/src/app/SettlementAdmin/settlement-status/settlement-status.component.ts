import { Component, OnInit } from '@angular/core';
import { CommsService } from 'src/app/comms.service';

@Component({
  selector: 'app-settlement-status',
  templateUrl: './settlement-status.component.html',
  styleUrls: ['./settlement-status.component.scss']
})
export class SettlementStatusComponent implements OnInit {

  constructor(private comms: CommsService) { }

  receiveAgainstPayments;

  ngOnInit(): void {
    this.getReceiveAgainstPayments();
  }

  getReceiveAgainstPayments() {
    this.comms.getReceiveAgainstPayments().subscribe((data) => this.receiveAgainstPayments = data['_embedded']['receive-against-payments'])
  }

}
