import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ComentarioDto } from '../components/modal-user-comments/modal-user-comments.component';
import { GetResponseComment } from '../interfaces/comment-interface';
import { GetUser } from '../interfaces/users';
import { Comentario } from '../models/comment';
import { NewUser } from '../models/new-user';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  //baseUrl = "http://localhost:8082/api/comment/";
  //baseUrlTwo = "http://localhost:8082/comment/";
  
  baseUrl = "http://192.168.1.134:8082/api/comment/";
  baseUrlTwo = "http://192.168.1.134:8082/comment/";

  //baseUrl = "http://localhost:8082/snowyduneservice/api/comment/";
  //baseUrlTwo = "http://localhost:8082/snowyduneservice/comment/";

  //baseUrl = "http://iesalixar.ddns.net:9095/snowyduneservice/api/comment/";
  //baseUrlTwo = "http://iesalixar.ddns.net:9095/snowyduneservice/comment/";

  constructor(private httpClient: HttpClient) { }

  public getCommentListPaginatedSorted(thePage: number, thePageSize: number, columnName:string, order:string): Observable<GetResponseComment> {

    const searchUrl = `${this.baseUrl}search/listComments?`
    + `page=${thePage}&size=${thePageSize}&sort=${columnName},${order}`;

    return this.httpClient.get<GetResponseComment>(searchUrl);
  }

  public getCommentByClassId(classId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsClasses?id=' + classId);
  }

  public getCommentByClassIdPaginated(thePage: number,classId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsClasses?id=' + classId +
    `&page=${thePage}&size=3&sort=date,asc`);
  }

  public getCommentByCarRentalId(carRentalId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsCarRental?id=' + carRentalId);
  }

  public getCommentByCarRentalIdPaginated(thePage: number,carRentalId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsCarRental?id=' + carRentalId +
    `&page=${thePage}&size=3&sort=date,asc`);
  }

  public getCommentBySkiMaterialId(skiMaterialId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsSkiMaterial?id=' + skiMaterialId);
  }

  public getCommentBySkiMaterialIdPaginated(thePage: number,skiMaterialId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsSkiMaterial?id=' + skiMaterialId +
    `&page=${thePage}&size=3&sort=date,asc`);
  }

  public getCommentByHotelId(hotelId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsHotel?id=' + hotelId);
  }

  public getCommentByHotelIdPaginated(thePage: number,hotelId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsHotel?id=' + hotelId +
    `&page=${thePage}&size=3&sort=date,asc`);
  }

  public getCommentByStationIdPaginated(thePage: number,stationId: number){
    return this.httpClient.get<GetResponseComment>(this.baseUrl + 'search/listCommentsStation?id=' + stationId +
    `&page=${thePage}&size=3&sort=date,asc`);
  }

  public getCommentByUserId(userId: number){
    return this.httpClient.get<GetUser>(this.baseUrl + userId + '/user');
  }

  public getCommentByCommentId(commentId: number){
    return this.httpClient.get<GetUser>(this.baseUrl + commentId + '/user');
  }

  public putComment(comment: Comentario, id:number): Observable<any>{
    return this.httpClient.put<any>(this.baseUrlTwo + 'update/' + id, comment);
  }

  public putCommentByStationUserId(stationId: number, userId: number, commentId: number){
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateCommentStation?userId=' + userId + '&stationId=' + stationId + '&commentId=' + commentId, null);
  }

  public putCommentByHotelUserId(hotelId: number, userId: number, commentId: number){
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateCommentHotel?userId=' + userId + '&hotelId=' + hotelId + '&commentId=' + commentId, null);
  }

  public putCommentByClassesUserId(classesId: number, userId: number, commentId: number){
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateCommentClasses?userId=' + userId + '&classesId=' + classesId + '&commentId=' + commentId, null);
  }

  public putCommentBySkiMaterialUserId(skiMaterialId: number, userId: number, commentId: number){
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateCommentSkiMaterial?userId=' + userId + '&skiMaterialId=' + skiMaterialId + '&commentId=' + commentId, null);
  }

  public putCommentByCarRentalUserId(carRentalId: number, userId: number, commentId: number){
    return this.httpClient.put<any>(this.baseUrlTwo + 'updateCommentCarRental?userId=' + userId + '&carRentalId=' + carRentalId + '&commentId=' + commentId, null);
  }

  public deleteComment(id:number): Observable<any>{
    return this.httpClient.delete<any>(this.baseUrlTwo + 'delete/' + id);
  }

  public newComment(comment: ComentarioDto): Observable<any>{
    return this.httpClient.post<any>(this.baseUrlTwo + 'create', comment);
  }
}


