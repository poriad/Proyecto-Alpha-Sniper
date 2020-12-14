import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetResponseHotel, HotelDto } from '../interfaces/hotel-interface';
import { Hotel } from '../models/hotel';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  baseUrl = "http://localhost:8082/api/hotel/";
  baseUrlTwo = "http://localhost:8082/hotel/";

  //baseUrl = "http://192.168.1.134:8082/api/hotel/";
  //baseUrlTwo = "http://192.168.1.134:8082/hotel/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/hotel/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/hotel/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/hotel/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/hotel/";



  constructor(private httpClient: HttpClient) { }

  public getHotelById(hotelId: number) {
    return this.httpClient.get<Hotel>(this.baseUrlTwo + 'hotelId/' + hotelId)
  }

  public getHotelListPaginatedSorted(thePage: number, thePageSize: number, columnName: string, order: string): Observable<GetResponseHotel> {

    const searchUrl = `${this.baseUrl}search/listHotelStatusPageable?status=0&`
      + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseHotel>(searchUrl);
  }

  public getHotelByCountryListPaginate(thePage: number, thePageSize: number, theHotelCountry: string): Observable<GetResponseHotel> {

    const searchUrl = `${this.baseUrl}search/listHotelByLocationStatusPageable?status=1&location=${theHotelCountry}`
      + `&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseHotel>(searchUrl);
  }

  public getHotelByStarsListPaginate(thePage: number, thePageSize: number, theHotelCountry: string, stars: number): Observable<GetResponseHotel> {

    const searchUrl = `${this.baseUrl}search/listHotelByStarsStatusPageable?status=1&location=${theHotelCountry}`
      + `&stars=${stars}&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseHotel>(searchUrl);
  }

  public getHotelByStarsPriceListPaginate(thePage: number, thePageSize: number, theHotelCountry: string, minPrice: number, maxPrice: number, stars: number): Observable<GetResponseHotel> {

    const searchUrl = `${this.baseUrl}search/listHotelByLocationAndPriceStatusPageable?status=1&location=${theHotelCountry}`
      + `&stars=${stars}&minPrice=${minPrice}&maxPrice=${maxPrice}&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseHotel>(searchUrl);
  }


  public getHotelListByUser(userId: number) {
    return this.httpClient.get<GetResponseHotel>(this.baseUrl + 'search/getHotelFromUser?id=' + userId);
  }

  public newHotel(hotelDto: HotelDto): Observable<any> {
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', hotelDto);
  }

  public putHotelToActive(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteHotel(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  public desactiveHotel(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=0", null);
  }

  // Actualizar el stationid
  public putHotelStationId(idHotel: number, idStation: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusHotelStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putHotelUserId(idHotel: number, idUser: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdHotel/' + idHotel + "?userid=" + idUser, null);
  }
}

