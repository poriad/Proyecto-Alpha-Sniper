import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SkiMaterialDto } from '../components/enterprise-services/enterprise-services.component';
import { SkiMaterial } from '../models/ski-material';

@Injectable({
  providedIn: 'root'
})
export class SkiMaterialService {

  //baseUrl = "http://localhost:8082/api/ski-material/";
  //baseUrlTwo = "http://localhost:8082/skiMaterial/";
  baseUrl = "http://192.168.1.134:8082/api/ski-material/";
  baseUrlTwo = "http://192.168.1.134:8082/skiMaterial/";

  constructor(private httpClient: HttpClient) { }

  public getSkiMaterialListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseSkiMaterial> {

    const searchUrl = `${this.baseUrl}search/listSkiMaterialStatusPageable?status=0&`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseSkiMaterial>(searchUrl);
  }

  public getSkiMaterialListByUser(userId:number){
    return this.httpClient.get<GetResponseSkiMaterial>(this.baseUrl + 'search/getSkiMaterialFromUser?id=' + userId);
  }

  public newSkiMaterial(skiMaterialDto: SkiMaterialDto): Observable<any>{
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', skiMaterialDto);
  }

  public putSkiMaterialToActive(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteSkiMaterial(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  public desactiveSkiMaterial(id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=0", null);
  }
  // Actualizar el stationid
  public putSkiMaterialStationId(idHotel:number,idStation: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusSkiMaterialStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putSkiMaterialUserId(idHotel:number, idUser: number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdSkiMaterial/' + idHotel + "?userid=" + idUser, null);
  }
  
}

interface GetResponseSkiMaterial {
  _embedded: {
    skiMaterial: SkiMaterial[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }
}