import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Station } from '../models/station';

@Injectable({
  providedIn: 'root'
})
export class StationService {
  
  //baseUrl = "http://localhost:8082/api/stations";
  //stationURL = "http://localhost:8082/station/";
  stationURL = "http://192.168.1.134:8082/station/";
  baseUrl = "http://192.168.1.134:8082/api/stations";

  constructor(private httpClient: HttpClient) { }

  public stationList(): Observable<Station[]> {
    return this.httpClient.get<Station[]>(this.stationURL + 'list');
  }
  
  public getStationListPaginate(thePage: number, thePageSize: number, theStationCountry:string): Observable<GetResponseStations> {

    const searchUrl = `${this.baseUrl}/search/findByCountry?country=${theStationCountry}`
                      + `&page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseStations>(searchUrl);
  }

  public searchStationsPaginate(thePage: number, thePageSize: number, theKeyword:string): Observable<GetResponseStations> {

    const searchUrl = `${this.baseUrl}/search/findByNameContaining?name=${theKeyword}`
    + `&page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<GetResponseStations>(searchUrl);
  }

  public getStationListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseStations> {

    const searchUrl = `${this.baseUrl}/search/listStationStatusPageable?status=0&`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseStations>(searchUrl);
  }

  public putStationToActive(id:number): Observable<any>{
    return this.httpClient.put<any>(this.stationURL + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteStation(id:number): Observable<any>{
    return this.httpClient.put<any>(this.stationURL + 'updateStatus/' + id + "?status=2", null);
  }

  public newStation(station: Station): Observable<any>{
    return this.httpClient.post<any>(this.stationURL + 'create', station);
  }


}

interface GetResponseStations {
  _embedded: {
    station: Station[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}