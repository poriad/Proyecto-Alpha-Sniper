import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClassesDto, GetResponseClasses } from '../interfaces/classes-interface';
import { Classes } from '../models/classes';

@Injectable({
  providedIn: 'root'
})
export class ClassesService {

  baseUrl = "http://localhost:8082/api/classes/";
  baseUrlTwo = "http://localhost:8082/classes/"

  //baseUrl = "http://192.168.1.134:8082/api/classes/";
  //baseUrlTwo = "http://192.168.1.134:8082/classes/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/classes/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/classes/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/classes/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/classes/";

  constructor(private httpClient: HttpClient) { }

  public getClassesById(classesId: number) {
    return this.httpClient.get<Classes>(this.baseUrlTwo + 'classesId/' + classesId)
  }


  public getClassesListPaginatedSorted(thePage: number, thePageSize: number, columnName: string, order: string): Observable<GetResponseClasses> {

    const searchUrl = `${this.baseUrl}search/listClassesStatusPageable?status=0&`
      + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseClasses>(searchUrl);
  }

  public getClassesByCountryListPaginate(thePage: number, thePageSize: number, theClassesCountry: string): Observable<GetResponseClasses> {

    const searchUrl = `${this.baseUrl}search/listClassesByLocationStatusPageable?status=1&location=${theClassesCountry}`
      + `&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseClasses>(searchUrl);
  }

  public getClassesByLocationPriceListPaginate(thePage: number, thePageSize: number, theClassesCountry: string, minPrice: number, maxPrice: number): Observable<GetResponseClasses> {

    const searchUrl = `${this.baseUrl}search/listClassesByLocationAndPriceStatusPageable?status=1&location=${theClassesCountry}`
      + `&minPrice=${minPrice}&maxPrice=${maxPrice}&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseClasses>(searchUrl);
  }

  public getClassesListByUser(userId: number) {
    return this.httpClient.get<GetResponseClasses>(this.baseUrl + 'search/getClassesFromUser?id=' + userId);
  }

  public newClasses(classesDto: ClassesDto): Observable<any> {
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', classesDto);
  }

  public putClassesToActive(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteClass(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  public desactiveClass(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=0", null);
  }

  // Actualizar el stationid
  public putClassesStationId(idHotel: number, idStation: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusClassesStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putClassesUserId(idHotel: number, idUser: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdClasses/' + idHotel + "?userid=" + idUser, null);
  }
}

