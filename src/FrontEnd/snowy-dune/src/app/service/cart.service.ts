import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  baseUrl = "http://192.168.1.134:8082/api/cart/";
  baseUrlTwo = "http://192.168.1.134:8082/cart/";
  
  //baseUrl = "http://localhost:8082/snowyduneservice/api/cart/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/cart/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/cart/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/cart/";

  constructor(private httpClient: HttpClient) { }

  getCartByUserId(id: number){
    return this.httpClient.get<any>(this.baseUrl + 'search/getCartByUserId?id=' + id)
  }
}
