import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClassesDto } from '../components/enterprise-services/enterprise-services.component';
import { Classes } from '../models/classes';

@Injectable({
  providedIn: 'root'
})
export class ClassesService {

  baseUrl = "http://localhost:8082/api/classes/";
  baseUrlTwo = "http://localhost:8082/classes/"
  //baseUrl = "http://192.168.1.134:8082/api/classes/";
  //baseUrlTwo = "http://192.168.1.134:8082/classes/";
  

  constructor(private httpClient: HttpClient) { }

  public getClassesListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseClasses> {

    const searchUrl = `${this.baseUrl}search/listClassesStatusPageable?status=0&`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseClasses>(searchUrl);
  }

  public getClassesListByUser(userId:number){
    return this.httpClient.get<GetResponseClasses>(this.baseUrl + 'search/getClassesFromUser?id=' + userId);
  }

  public newClasses(classesDto: ClassesDto): Observable<any>{
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', classesDto);
  }

  public putClassesToActive(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteClass(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  public desactiveClass(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=0", null);
  }

  // Actualizar el stationid
  public putClassesStationId(idHotel:number,idStation: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusClassesStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putClassesUserId(idHotel:number, idUser: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdClasses/' + idHotel + "?userid=" + idUser, null);
  }
}

interface GetResponseClasses {
  _embedded: {
    classes: Classes[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}
