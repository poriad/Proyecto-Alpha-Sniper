import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ImgurApiService {

  private readonly IMGUR_UPLOAD_URL = 'https://api.imgur.com/3/image';
  private readonly clientId = 'ef5cea457482285';
  private accessToken: string = "f501d6869ff17485255a869c3406d8d6f4d647d5";

  constructor(
    private http: HttpClient
  ) { }

  upload(b64Image: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        "Authorization": `Client-ID ${this.clientId}`
      }),
    };
    const formData = new FormData();
    formData.append('image', b64Image);
    return this.http.post(`${this.IMGUR_UPLOAD_URL}`, formData, httpOptions);
  }

}
