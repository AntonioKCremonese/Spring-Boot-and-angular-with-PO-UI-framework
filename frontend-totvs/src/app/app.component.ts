import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { PoMenuItem } from '@po-ui/ng-components';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private router: Router){}

  readonly menus: Array<PoMenuItem> = [
    { label: 'Home', action: this.navigateToHomePage.bind(this) },
    { label: 'Vagas', subItems: [
      { label: 'Lista de vagas', link: '/vagas' },
      { label: 'Adicionar nova vaga', link: '/vagas/new' },
    ]},
    { label: 'Candidatos', subItems: [
      { label: 'Lista de Candidatos', link: '/candidatos' },
      { label: 'Novo Candidato', link: '/candidatos/new' },
    ]}
  ];

  private navigateToHomePage(){
    this.router.navigate(["/home"]);
  }

}
