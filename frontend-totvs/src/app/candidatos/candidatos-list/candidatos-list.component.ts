import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PoNotificationService, PoPageAction, PoTableAction } from '@po-ui/ng-components';
import { Candidato } from 'src/app/interfaces/Candidato';
import { CandidatoService } from 'src/app/services/candidato.service';

@Component({
  selector: 'app-candidatos-list',
  templateUrl: './candidatos-list.component.html',
  styleUrls: ['./candidatos-list.component.css']
})
export class CandidatosListComponent implements OnInit {
  public readonly actions: Array<PoPageAction> = [
    { action: this.onNewCandidato.bind(this), label: 'Cadastrar', icon: 'po-icon-stock' },
  ];

  public readonly tableActions: Array<PoTableAction> = [
    { action: this.onRegisterCv.bind(this), disabled: false, label: 'Cadastrar Curriculo' },
    { action: this.onEditCandidato.bind(this), disabled: false, label: 'Editar' },
    { action: this.onRemoveCandidato.bind(this), label: 'Remover', type: 'danger', separator: true }
  ];

  public candidatos: Array<Candidato> = [];

  constructor(
    private candidatoService: CandidatoService, 
    private router: Router, 
    private poNotification: PoNotificationService) { }

  ngOnInit(): void {
    this.candidatoService.getAllCandidatos().subscribe(res => this.candidatos = res);
  }

  onNewCandidato(){
    this.router.navigateByUrl('/candidatos/new');  
  }

  onEditCandidato(candidato: Candidato){
    this.router.navigateByUrl(`/candidatos/edit/${candidato.id}`);
  }

  onRemoveCandidato(candidato: Candidato){
    this.candidatoService.delete(candidato.id).subscribe(() => this.poNotification.warning('Candidato deletado com sucesso.'))
    this.candidatos.splice(this.candidatos.indexOf(candidato), 1);
  }

  onRegisterOnVaga(candidato: Candidato){
    this.router.navigateByUrl(`/candidatos/edit/${candidato.id}`);
  }

  onRegisterCv(candidato: Candidato) {
    this.router.navigateByUrl(`/candidatos/edit/${candidato.id}`);  
  }

}
