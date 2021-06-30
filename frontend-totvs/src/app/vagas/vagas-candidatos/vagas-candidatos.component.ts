import { Component, OnInit } from "@angular/core";
import { Candidato } from "src/app/interfaces/Candidato";

@Component({
  selector: 'app-vagas-candidatos',
  templateUrl: './vagas-candidatos.component.html',
})
export class VagasCandidatosComponent implements OnInit {

  public candidatos: Array<Candidato> = [];

  constructor(){}

  ngOnInit(){
    this.candidatos = history.state.candidatos;
  }
}