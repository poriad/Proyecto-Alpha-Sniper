import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comentario } from '../models/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  baseUrl = "http://localhost:8082/api/comment/";
  baseUrlTwo = "http://localhost:8082/comment/";
  //baseUrl = "http://192.168.1.134:8082/api/comment/";
  //baseUrlTwo = "http://192.168.1.134:8082/comment/";

  constructor(private httpClient: HttpClient) { }

  public getCommentListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseUsers> {

    const searchUrl = `${this.baseUrl}search/listComments?`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseUsers>(searchUrl);
  }

  public getCommentByClasssId(classId: number){
    return this.httpClient.get<GetResponseUsers>(this.baseUrl + 'search/listCommentsClasses?id=' + classId);

  }

  public getCommentByCarRentalId(carRentalId: number){
    return this.httpClient.get<GetResponseUsers>(this.baseUrl + 'search/listCommentsCarRental?id=' + carRentalId);
  }

  public getCommentBySkiMaterialId(skiMaterialId: number){
    return this.httpClient.get<GetResponseUsers>(this.baseUrl + 'search/listCommentsSkiMaterial?id=' + skiMaterialId);
  }

  public getCommentByHotelId(hotelId: number){
    return this.httpClient.get<GetResponseUsers>(this.baseUrl + 'search/listCommentsHotel?id=' + hotelId);

  }

  public putComment(comment: Comentario, id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'update/' + id, comment);
  }

  public deleteComment(id:number): Observable<any>{
    return this.httpClient.delete<any>(this.baseUrlTwo + 'delete/' + id);
  }
}

interface GetResponseUsers {
  _embedded: {
    comment: Comentario[];
  },
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number
  }

}

