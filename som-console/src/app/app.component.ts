import { Component, OnInit } from '@angular/core';
import { CommsService } from './comms.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  connectionStatus$

  constructor(private comms: CommsService) {}

  ngOnInit() {
    this.connectionStatus$ = this.comms.connectionStatus$;
  }

  title = 'Settlement o Matic';
  public isMenuCollapsed = true;
}
