import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private url: string = 'http://localhost:8080/file';

  constructor(private httpClient: HttpClient) { }

  public getFileById(fileId: string): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/${fileId}`)
  }

  public create(formData: FormData): Observable<String> {
    console.log('formdata', formData);
    return this.httpClient.post<String>(this.url, formData);
  }

}
