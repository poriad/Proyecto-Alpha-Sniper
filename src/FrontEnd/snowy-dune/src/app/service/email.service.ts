import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ContactCVForm, ContactForm } from '../interfaces/contact-interface';

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  baseUrl = "http://localhost:8082/emailService/";

  //baseUrl = "http://192.168.1.134:8082/emailService/";

  //baseUrl = "http://localhost:8082/snowyduneservice/emailService/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/emailService/";



  constructor(private httpClient: HttpClient) { }

  public postEmailContact(comment: ContactForm) {
    return this.httpClient.post<any>(this.baseUrl + 'emailContacto', comment);
  }

  public postEmailCV(contact: ContactCVForm) {
    return this.httpClient.post<any>(this.baseUrl + 'emailCV', contact);
  }

}
