import { Component, OnInit } from '@angular/core';
import { ReceiveAgainstPayment } from 'src/app/entities';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { CommsService } from 'src/app/comms.service';

@Component({
  selector: 'app-new-settlement-request',
  templateUrl: './new-settlement-request.component.html',
  styleUrls: ['./new-settlement-request.component.scss']
})
export class NewSettlementRequestComponent implements OnInit {

  freeOrAgainstPayment = 'againstPayment';
  receiveOrDeliver = 'receive';

  receiveAgainstPayment: ReceiveAgainstPayment;

  constructor(private formBuilder: FormBuilder, private comms: CommsService) { }

  ngOnInit(): void {
    this.rapForm = this.formBuilder.group({
      id: [null],
      status: [null],
      senderReference: [null, Validators.required],
      senderPartyId: [null, Validators.required],
      senderAccountId: [null, Validators.required],
      receiverPartyId: [null, Validators.required],
      receiverAccountId: [null, Validators.required],
      instrumentId: [null, Validators.required],
      currency: [null, Validators.required],
      amount: [null, Validators.required]
    });
  }

  public rapForm: FormGroup;

  onSubmit(): void {
    console.log(JSON.stringify(this.rapForm.value));
    this.comms.sendReceiveAgainstPayment(this.rapForm.value);
  }

  populateRAPWithDummyData(): void {
    this.rapForm.setValue({id: null, status: null, "senderReference": "REF001","senderPartyId": "CLIENT1","senderAccountId": "ACC001","instrumentId": "US02079K1079","receiverPartyId": "COUNTERPARTY1",  "receiverAccountId": "ACC100","currency": "GBP","amount": 50000});
  }

}
