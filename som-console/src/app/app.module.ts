import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NewSettlementRequestComponent } from './SettlementClient/new-settlement-request/new-settlement-request.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { SettlementStatusComponent } from './SettlementAdmin/settlement-status/settlement-status.component';

@NgModule({
  declarations: [
    AppComponent,
    NewSettlementRequestComponent,
    WelcomeComponent,
    SettlementStatusComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
