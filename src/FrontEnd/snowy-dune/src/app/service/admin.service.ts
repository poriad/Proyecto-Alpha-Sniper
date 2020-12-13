import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetResponseUsers, GetResponseUsersEnterprise } from '../interfaces/users';
import { UserSnowy } from '../layouts/user-management/user-management.component';
import { EnterpriseUser } from '../models/enterprise-user';
import { NewUser } from '../models/new-user';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  //baseUrl = "http://localhost:8082/api/user/";
  //baseUrlUser = "http://localhost:8082/user/";
  
  baseUrl = "http://192.168.1.134:8082/api/user/";
  baseUrlUser = "http://192.168.1.134:8082/user/";
  
  //baseUrl = "http://localhost:8082/snowyduneservice/api/user/"
  //baseUrlUser = "http://localhost:8082/snowyduneservice/user/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/user/"
  //baseUrlUser = "http://iesalixar.ddns.net:9095/snowyduneservice/user/";

  

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
  }


  public putUserDetails(newUser: NewUser, id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'update/' + id, newUser);
  }

  public putUserPassword(id:number,password:string): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'updatePassword/' + id + "?password="+ password, null);
  }

  public putUserManagement(newUser: UserSnowy, id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'updateUserSnowy/' + id, newUser);
  }

  public putEnterpriseStatus(id:number, status:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'updateActiveStatus/' + id +'?status=' + status, null);
  }

  public deleteUser(id:number,status:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'updateUserStatus/' + id + "?status=" + status,null);
  }

  public getUserListPaginate(thePage: number, thePageSize: number): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/findAllUser?`
    + `page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

  public getUserListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/findAllUser?`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

  public getUserEnterpriseListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseUsersEnterprise> {

    const searchUrl = `${this.baseUrl}search/findByIsEnterprise?`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseUsersEnterprise>(searchUrl);
  }

}

