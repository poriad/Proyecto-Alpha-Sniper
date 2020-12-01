import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarRental } from '../models/car-rental';
import { Classes } from '../models/classes';
import { Hotel } from '../models/hotel';
import { SkiMaterial } from '../models/ski-material';
import { Station } from '../models/station';
import { Trip } from '../models/trip';

@Injectable({
  providedIn: 'root'
})
export class TripService {

  baseUrl = "http://192.168.1.134:8082/api/trip/";
  baseUrlTwo = "http://192.168.1.134:8082/trip/";

  constructor(private httpClient: HttpClient) { }

  public getFutureTrips(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsSysdate?userId=' + userId);
  }

  public getPastTrips(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsDoneThisYear?userId=' + userId);
  }

  public getTripsDoneLastYear(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsDoneLastYear?userId=' + userId);
  }

  public getTripsDoneLastMonth(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsDoneLastMonth?userId=' + userId);
  }

  public getStationTrip(id:number): Observable<Station> {
    return this.httpClient.get<Station>(this.baseUrl + id +'/station');
  }

  public getClassesTrip(id:number): Observable<Classes> {
    return this.httpClient.get<Classes>(this.baseUrl + id +'/classes');
  }

  public getHotelTrip(id:number): Observable<Hotel> {
    return this.httpClient.get<Hotel>(this.baseUrl + id +'/hotel');
  }

  public getSkiMaterialTrip(id:number): Observable<SkiMaterial> {
    return this.httpClient.get<SkiMaterial>(this.baseUrl + id +'/skiMaterial');
  }

  public getCarRentalTrip(id:number): Observable<CarRental> {
    return this.httpClient.get<CarRental>(this.baseUrl + id +'/carRental');
  }
}

interface GetResponseTrip {
  _embedded: {
    trip: Trip[];
  }
}