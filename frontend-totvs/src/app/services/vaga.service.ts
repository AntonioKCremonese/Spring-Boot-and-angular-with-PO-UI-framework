import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vaga, VagaPost, VagaPut } from '../interfaces/Vaga';

@Injectable({
  providedIn: 'root'
})
export class VagasService {

  private url: string = 'http://localhost:8080/vagas';

  constructor(private httpClient: HttpClient) { }

  public getAllVagas(): Observable<Vaga[]> {
    return this.httpClient.get<Vaga[]>(this.url);
  }

  public getVagaById(vagaId: string): Observable<Vaga> {
    return this.httpClient.get<Vaga>(`${this.url}/${vagaId}`)
  }

  public create(vaga: VagaPost): Observable<String> {
    return this.httpClient.post<String>(this.url, vaga);
  }

  public update(vaga: VagaPut): Observable<String> {
    return this.httpClient.put<String>(this.url, vaga);
  }

  public delete(vagaId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${vagaId}`)
  }
}
