import { JwtDTO } from './../models/jwt-dto';
import { LoginUser } from './../models/login-user';
import { Observable } from 'rxjs';
import { NewUser } from './../models/new-user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  //authURL = 'http://localhost:8082/auth/';
  authURL = 'http://192.168.1.134:8082/auth/'

  constructor(private httpClient: HttpClient) { }

  public new(newUser: NewUser): Observable<any>{
    return this.httpClient.post<any>(this.authURL + 'nuevo', newUser);
  }

  public login(loginUser: LoginUser): Observable<JwtDTO>{
    return this.httpClient.post<JwtDTO>(this.authURL + 'login', loginUser);
  }
}
