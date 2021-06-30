import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PoNotificationService, PoPageAction, PoTableAction, PoTableColumn } from '@po-ui/ng-components';
import { Candidato } from 'src/app/interfaces/Candidato';
import { Vaga, VagaStatusEnum } from '../../interfaces/Vaga';
import { VagasService } from '../../services/vaga.service';

// import { environment } from 'src/environments/environment';
const environment = {
  production: false,
  apiUrl: 'http://localhost:5000'
};

@Component({
  selector: 'app-vagas-list',
  templateUrl: './vagas-list.component.html',
  styleUrls: ['./vagas-list.component.css']
})
export class VagasListComponent implements OnInit {

  public readonly actions: Array<PoPageAction> = [
    { action: this.onNewVaga.bind(this), label: 'Cadastrar', icon: 'po-icon-stock' },
  ];

  public readonly tableActions: Array<PoTableAction> = [
    { action: this.onViewCandidatos.bind(this), disabled: false, label: 'Candidatos' },
    { action: this.onEditVaga.bind(this), disabled: false, label: 'Editar' },
    { action: this.onRemoveVaga.bind(this), label: 'Remover', type: 'danger', separator: true }
  ];

  public items: Array<Vaga> = [];
  public candidatos: Array<Candidato> = [];
  public columnsCandidatosTable:Array<PoTableColumn> = [
    {label: 'id'},
    {label: 'name'},
    {label: 'mail'},
    {label: 'phone'},
  ]

  constructor(private vagasService: VagasService, private router: Router, private poNotification: PoNotificationService) { }

  ngOnInit(): void {
    this.vagasService.getAllVagas().subscribe(res => {
      if(res.length > 0){
        this.items = res;
      }
    });
  }

  onNewVaga() {
    this.router.navigateByUrl('/vagas/new');
  }

  private onEditVaga(vaga:Vaga){
    this.router.navigateByUrl(`/vagas/edit/${vaga.id}`); 
  }
  private onRemoveVaga(vaga: Vaga){
    this.vagasService.delete(vaga.id).subscribe(() => this.poNotification.warning('Vaga deletada com sucesso.'));
    this.items.splice(this.items.indexOf(vaga), 1);
  }

  private onViewCandidatos(vaga:Vaga){
    this.router.navigate(['/vagas/candidatos'],{
      state:{
        'candidatos': vaga['candidatos'],
      }
    });
    // this.candidatos = vaga.candidatos;
  }

}
