import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CommsService {

  clientGatewayUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getReceiveAgainstPayments() {
    return this.http.get(this.clientGatewayUrl + "/receive-against-payments");
  }
  
}
