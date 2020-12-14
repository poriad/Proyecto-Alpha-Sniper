import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetResponseTrip } from '../interfaces/trip-interface';
import { TripDto } from '../layouts/trip/trip.component';
import { CarRental } from '../models/car-rental';
import { Classes } from '../models/classes';
import { Hotel } from '../models/hotel';
import { SkiMaterial } from '../models/ski-material';
import { Station } from '../models/station';
import { Trip } from '../models/trip';

const cabecera = { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }

@Injectable({
  providedIn: 'root'
})
export class TripService {

  baseUrl = "http://localhost:8082/api/trip/";
  baseUrlTwo = "http://localhost:8082/trip/";

  //baseUrl = "http://192.168.1.134:8082/api/trip/";
  //baseUrlTwo = "http://192.168.1.134:8082/trip/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/trip/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/trip/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/trip/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/trip/";

  constructor(private httpClient: HttpClient) { }

  public getFutureTrips(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsSysdate?userId=' + userId);
  }

  public getList(): Observable<Trip[]> {
    return this.httpClient.get<Trip[]>(this.baseUrlTwo + 'list', cabecera);
  }

  public getTrip(id: number): Observable<Trip> {
    return this.httpClient.get<Trip>(this.baseUrlTwo + 'detail/' + id, cabecera);
  }

  public getPastTrips(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsDoneThisYear?userId=' + userId);
  }

  public getPastTripsComplete(userId: number): Observable<Trip[]> {
    return this.httpClient.get<Trip[]>(this.baseUrlTwo + 'listLastYearTrip?id=' + userId);
  }

  public getThisYearTripsComplete(userId: number): Observable<Trip[]> {
    return this.httpClient.get<Trip[]>(this.baseUrlTwo + 'listThisYearTrips?id=' + userId);
  }

  public getTripsInCart(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsInCart?id=' + userId);
  }

  public getTripsInCartController(userId: number): Observable<Trip[]> {
    return this.httpClient.get<Trip[]>(this.baseUrlTwo + 'listCart?id=' + userId);
  }


  public getTripsInCartControllerCheckout(userId: number): Observable<Trip[]> {
    return this.httpClient.get<Trip[]>(this.baseUrlTwo + 'listCheckout?id=' + userId);
  }

  public getTripsDoneLastYear(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsDoneLastYear?userId=' + userId);
  }

  public getTripsDoneLastMonth(userId: number): Observable<GetResponseTrip> {
    return this.httpClient.get<GetResponseTrip>(this.baseUrl + 'search/getTripsDoneLastMonth?userId=' + userId);
  }

  public getStationTrip(id: number): Observable<Station> {
    return this.httpClient.get<Station>(this.baseUrl + id + '/station');
  }

  public getClassesTrip(id: number): Observable<Classes> {
    return this.httpClient.get<Classes>(this.baseUrl + id + '/classes');
  }

  public getHotelTrip(id: number): Observable<Hotel> {
    return this.httpClient.get<Hotel>(this.baseUrl + id + '/hotel');
  }

  public getSkiMaterialTrip(id: number): Observable<SkiMaterial> {
    return this.httpClient.get<SkiMaterial>(this.baseUrl + id + '/skiMaterial');
  }

  public getCarRentalTrip(id: number): Observable<CarRental> {
    return this.httpClient.get<CarRental>(this.baseUrl + id + '/carRental');
  }

  public putTrip(id: number, userId: number, stationId: number, hotelId: number, classesId: number, skiMaterialId: number, carRentalId: number, cartId: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateTrip?id=' + id + '&userId=' + userId + '&stationId=' + stationId + '&hotelId=' + hotelId + '&classesId=' + classesId + '&skiMaterialId=' + skiMaterialId + '&carRentalId=' + carRentalId + '&cartId=' + cartId, null);
  }

  public putTripToCheckout(idTrip: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateTripToCheckout/' + idTrip, null);

  }

  public putTripWithPayment(paymentId: number, idTrip: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateTripPaymentDone?paymentId=' + paymentId + '&tripId=' + idTrip, null);

  }

  public newTrip(tripDto: TripDto): Observable<any> {
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', tripDto);
  }

  public deleteTrip(idTrip: number): Observable<any> {
    return this.httpClient.delete<any>(this.baseUrlTwo + 'delete/' + idTrip);
  }
}