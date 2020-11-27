import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HotelDto } from '../components/enterprise-services/enterprise-services.component';
import { Hotel } from '../models/hotel';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  //baseUrl = "http://localhost:8082/api/hotel/";
  //baseUrlTwo = "http://localhost:8082/hotel/";
  baseUrl = "http://192.168.1.134:8082/api/hotel/";
  baseUrlTwo = "http://192.168.1.134:8082/hotel/";
  

  constructor(private httpClient: HttpClient) { }

  public getHotelListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseHotel> {

    const searchUrl = `${this.baseUrl}search/listHotelStatusPageable?status=0&`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseHotel>(searchUrl);
  }

  public getHotelListByUser(userId:number){
    return this.httpClient.get<GetResponseHotel>(this.baseUrl + 'search/getHotelFromUser?id=' + userId);
  }

  public newHotel(hotelDto: HotelDto): Observable<any>{
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', hotelDto);
  }

  public putHotelToActive(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteHotel(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  public desactiveHotel(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=0", null);
  }

  // Actualizar el stationid
  public putHotelStationId(idHotel:number,idStation: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusHotelStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putHotelUserId(idHotel:number, idUser: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdHotel/' + idHotel + "?userid=" + idUser, null);
  }
}

interface GetResponseHotel {
  _embedded: {
    hotel: Hotel[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}
