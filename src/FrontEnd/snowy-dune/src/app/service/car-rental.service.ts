import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarRentalDto, GetResponseCarRental } from '../interfaces/car-rental-interface';
import { CarRental } from '../models/car-rental';

@Injectable({
  providedIn: 'root'
})
export class CarRentalService {

  baseUrl = "http://localhost:8082/api/car-rental/";
  baseUrlTwo = "http://localhost:8082/carRental/";

  //baseUrl = "http://192.168.1.134:8082/api/car-rental/";
  //baseUrlTwo = "http://192.168.1.134:8082/carRental/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/car-rental/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/carRental/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/car-rental/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/carRental/";

  constructor(private httpClient: HttpClient) { }

  public getCarRentalById(carRentalId: number) {
    return this.httpClient.get<CarRental>(this.baseUrlTwo + 'carRentalId/' + carRentalId)
  }

  public getCarRentalListPaginatedSorted(thePage: number, thePageSize: number, columnName: string, order: string): Observable<GetResponseCarRental> {

    const searchUrl = `${this.baseUrl}search/listCarRentalStatusPageable?status=0&`
      + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseCarRental>(searchUrl);
  }

  public getCarRentalByCountryListPaginate(thePage: number, thePageSize: number, theCarRentalCountry: string): Observable<GetResponseCarRental> {

    const searchUrl = `${this.baseUrl}search/listCarRentalByLocationStatusPageable?status=1&location=${theCarRentalCountry}`
      + `&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseCarRental>(searchUrl);
  }

  public getCarRentalByLocationPriceListPaginate(thePage: number, thePageSize: number, theCarRentalCountry: string, minPrice: number, maxPrice: number): Observable<GetResponseCarRental> {

    const searchUrl = `${this.baseUrl}search/listCarRentalByLocationAndPriceStatusPageable?status=1&location=${theCarRentalCountry}`
      + `&minPrice=${minPrice}&maxPrice=${maxPrice}&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseCarRental>(searchUrl);
  }

  public getCarRentalListByUser(userId: number) {
    return this.httpClient.get<GetResponseCarRental>(this.baseUrl + 'search/getCarRentalFromUser?id=' + userId);
  }

  public newCarRental(carRentalDto: CarRentalDto): Observable<any> {
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', carRentalDto);
  }

  public putCarRentalToActive(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteCarRental(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  public desactiveCarRental(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=0", null);
  }

  // Actualizar el stationid
  public putCarRentalStationId(idHotel: number, idStation: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusCarRentalStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putCarRentalUserId(idHotel: number, idUser: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdCarRental/' + idHotel + "?userid=" + idUser, null);
  }

}

