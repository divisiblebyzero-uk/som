import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SettlementStatusComponent } from './SettlementAdmin/settlement-status/settlement-status.component';
import { NewSettlementRequestComponent } from './SettlementClient/new-settlement-request/new-settlement-request.component';
import { WelcomeComponent } from './welcome/welcome.component';

const routes: Routes = [
  {path: '', component: WelcomeComponent },
  {path: 'settlement-client/new-settlement-request', component: NewSettlementRequestComponent},
  {path: 'settlement-admin/settlement-status', component: SettlementStatusComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
