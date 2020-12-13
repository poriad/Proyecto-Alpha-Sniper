import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentDto, PaymentIntentDto } from '../interfaces/payment-interface';
import { Payment } from '../models/payment';
import { Trip } from '../models/trip';

const cabecera = {headers: new HttpHeaders({'Content-Type': 'application/json'})}

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  baseUrl = "http://192.168.1.134:8082/api/payment/";
  baseUrlTwo = "http://192.168.1.134:8082/payment/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/payment/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/payment/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/payment/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/payment/";

  constructor(private httpClient: HttpClient) { }

  public putPaymentUserId(userId: number, paymentId: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updatePayment?userId=' + userId + '&paymentId=' + paymentId, null);
  }

  public newPayment(payment: PaymentDto): Observable<any>{
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', payment);
  }

  public newPaymentStripe(): Observable<any>{
    return this.httpClient.post<any>(this.baseUrlTwo + 'createPayment', {});
  }

  public pay(paymentIntentDto: PaymentIntentDto): Observable<string> {
    return this.httpClient.post<string>(this.baseUrlTwo + 'paymentIntent', paymentIntentDto, cabecera)
  }
  
  public confirm(id: String): Observable<string>{
    return this.httpClient.post<string>(this.baseUrlTwo + 'confirm/' + id, {},cabecera);
  }

  public delete(id: String): Observable<string>{
    return this.httpClient.post<string>(this.baseUrlTwo + 'cancel/' + id, {},cabecera);
  }

  
}
