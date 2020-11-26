import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarRentalDto } from '../components/enterprise-services/enterprise-services.component';
import { CarRental } from '../models/car-rental';

@Injectable({
  providedIn: 'root'
})
export class CarRentalService {

  baseUrl = "http://192.168.1.134:8082/api/car-rental/";
  baseUrlTwo = "http://192.168.1.134:8082/carRental/";

  constructor(private httpClient: HttpClient) { }

  public getCarRentalListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseCarRental> {

    const searchUrl = `${this.baseUrl}search/listCarRentalStatusPageable?status=0&`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseCarRental>(searchUrl);
  }

  public getCarRentalListByUser(userId:number){
    return this.httpClient.get<GetResponseCarRental>(this.baseUrl + 'search/getCarRentalFromUser?id=' + userId);
  }

  public newCarRental(carRentalDto: CarRentalDto): Observable<any>{
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', carRentalDto);
  }

  public putCarRentalToActive(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteCarRental(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  // Actualizar el stationid
  public putCarRentalStationId(idHotel:number,idStation: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusCarRentalStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putCarRentalUserId(idHotel:number, idUser: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdCarRental/' + idHotel + "?userid=" + idUser, null);
  }
  
}

interface GetResponseCarRental {
  _embedded: {
    carRental: CarRental[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}