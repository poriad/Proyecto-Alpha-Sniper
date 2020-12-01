import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
}

interface GetResponseTrip {
  _embedded: {
    trip: Trip[];
  }
}