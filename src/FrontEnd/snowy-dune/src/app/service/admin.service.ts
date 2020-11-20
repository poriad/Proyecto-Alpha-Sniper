import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NewUser } from '../models/new-user';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  baseUrl = "http://localhost:8082/api/user/";
  //baseUrl = "http://192.168.1.134:8082/api/user/";
  baseUrlUser = "http://192.168.1.134:8082/user/";

  

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
  }

  public putUserDetails(newUser: NewUser, id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlUser + 'update' + id, newUser);
  }

  public deleteUser(id:number): Observable<any>{
    console.log("servicio")
    return this.httpClient.delete<any>(this.baseUrlUser + 'delete/' + id);
  }

  public getUserListPaginate(thePage: number, thePageSize: number): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/findAllUser?`
    + `page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

  public getUserListPaginateSortedByUsername(thePage: number, thePageSize: number): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/findAllUserOderByUsername?`
    + `page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

  public getUserListPaginateSortedByEmail(thePage: number, thePageSize: number): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/findAllUserOderByEmail?`;
    + `page=${thePage}&size=${thePageSize}`

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

  public getUserListPaginateSortedByFirstName(thePage: number, thePageSize: number): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/findAllUserOderByFirstName?`
    + `page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

  public getUserListPaginateSortedByLastName(thePage: number, thePageSize: number): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/findAllUserOderByLastName?`
    + `page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

}

interface GetResponseUsers {
  _embedded: {
    user: NewUser[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}