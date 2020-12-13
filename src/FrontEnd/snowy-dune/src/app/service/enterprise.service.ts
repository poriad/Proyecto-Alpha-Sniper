import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Enterprise } from '../models/enterprise';
import { NewUser } from '../models/new-user';

@Injectable({
  providedIn: 'root'
})
export class EnterpriseService {

  //baseUrl = "http://localhost:8082/api/user/";
  //baseUrlUser = "http://localhost:8082/user/";
  
  baseUrl = "http://192.168.1.134:8082/api/user/";
  baseUrlUser = "http://192.168.1.134:8082/user/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/user/";
  //baseUrlUser = "http://localhost:8082/snowyduneservice/user/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/user/";
  //baseUrlUser = "http://iesalixar.ddns.net:9095/snowyduneservice/user/";

  constructor(private httpClient: HttpClient) { }

  public putUserDetailsToEnterprise(enterprise: Enterprise, id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'updateToEnterprise/' + id, enterprise);
  }

  public putUserToEnterprise(userId:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'updateStatusEnterprise/' + userId, null);
  }

  public getIdUsername(username: string): Observable<NewUser> {

    const searchUrl = `${this.baseUrlUser}getByUsername?username=` + username;

    return this.httpClient.get<NewUser>(searchUrl);
  }

}
