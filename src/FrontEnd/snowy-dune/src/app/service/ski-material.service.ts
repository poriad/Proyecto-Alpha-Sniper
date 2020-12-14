import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetResponseSkiMaterial, SkiMaterialDto } from '../interfaces/ski-material-interface';
import { SkiMaterial } from '../models/ski-material';

@Injectable({
  providedIn: 'root'
})
export class SkiMaterialService {
  
  baseUrl = "http://localhost:8082/api/ski-material/";
  baseUrlTwo = "http://localhost:8082/skiMaterial/";

  //baseUrl = "http://192.168.1.134:8082/api/ski-material/";
  //baseUrlTwo = "http://192.168.1.134:8082/skiMaterial/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/ski-material/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/skiMaterial/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/ski-material/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/skiMaterial/";


  constructor(private httpClient: HttpClient) { }

  public getSkiMaterialById(skiMaterialId: number) {
    return this.httpClient.get<SkiMaterial>(this.baseUrlTwo + 'skiMaterialId/' + skiMaterialId)
  }

  public getSkiMaterialListPaginatedSorted(thePage: number, thePageSize: number, columnName: string, order: string): Observable<GetResponseSkiMaterial> {

    const searchUrl = `${this.baseUrl}search/listSkiMaterialStatusPageable?status=0&`
      + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseSkiMaterial>(searchUrl);
  }

  public getSkiMaterialByCountryListPaginate(thePage: number, thePageSize: number, theSkiMaterialCountry: string): Observable<GetResponseSkiMaterial> {

    const searchUrl = `${this.baseUrl}search/listSkiMaterialByLocationStatusPageable?status=1&location=${theSkiMaterialCountry}`
      + `&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseSkiMaterial>(searchUrl);
  }

  public getSkiMaterialByLocationPriceListPaginate(thePage: number, thePageSize: number, theSkiMaterialCountry: string, minPrice: number, maxPrice: number): Observable<GetResponseSkiMaterial> {

    const searchUrl = `${this.baseUrl}search/listSkiMaterialByLocationAndPriceStatusPageable?status=1&location=${theSkiMaterialCountry}`
      + `&minPrice=${minPrice}&maxPrice=${maxPrice}&page=${thePage}&size=${thePageSize}&sort=name,asc`;

    return this.httpClient.get<GetResponseSkiMaterial>(searchUrl);
  }

  public getSkiMaterialListByUser(userId: number) {
    return this.httpClient.get<GetResponseSkiMaterial>(this.baseUrl + 'search/getSkiMaterialFromUser?id=' + userId);
  }

  public newSkiMaterial(skiMaterialDto: SkiMaterialDto): Observable<any> {
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', skiMaterialDto);
  }

  public putSkiMaterialToActive(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=1", null);
  }

  public deleteSkiMaterial(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=2", null);
  }

  public desactiveSkiMaterial(id: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatus/' + id + "?status=0", null);
  }
  // Actualizar el stationid
  public putSkiMaterialStationId(idHotel: number, idStation: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateStatusSkiMaterialStation/' + idHotel + "?station=" + idStation, null);
  }

  // Actualizar el userid
  public putSkiMaterialUserId(idHotel: number, idUser: number): Observable<any> {
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateUserIdSkiMaterial/' + idHotel + "?userid=" + idUser, null);
  }

}

