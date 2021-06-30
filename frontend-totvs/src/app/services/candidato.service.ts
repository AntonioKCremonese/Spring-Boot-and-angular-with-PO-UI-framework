import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Candidato, CandidatoPost, CandidatoPut } from '../interfaces/Candidato';
import { Vaga, VagaPost, VagaPut } from '../interfaces/Vaga';

@Injectable({
  providedIn: 'root'
})
export class CandidatoService {

  private url: string = 'http://localhost:8080/candidatos';

  constructor(private httpClient: HttpClient) { }

  public getAllCandidatos(): Observable<Candidato[]> {
    return this.httpClient.get<Candidato[]>(this.url);
  }

  public getCandidatoById(candidatoId: string): Observable<Candidato> {
    return this.httpClient.get<Candidato>(`${this.url}/${candidatoId}`)
  }

  public create(candidato: CandidatoPost): Observable<String> {
    return this.httpClient.post<String>(this.url, candidato);
  }

  public update(candidato: CandidatoPut): Observable<String> {
    return this.httpClient.put<String>(this.url, candidato);
  }

  public delete(candidatoId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${candidatoId}`)
  }
}
