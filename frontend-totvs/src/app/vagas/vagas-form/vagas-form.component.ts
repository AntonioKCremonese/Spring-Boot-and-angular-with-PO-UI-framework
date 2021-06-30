import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PoNotificationService, PoSelectOption } from '@po-ui/ng-components';
import { Subscription } from 'rxjs';
import { Vaga, VagaPost, VagaStatusEnum } from 'src/app/interfaces/Vaga';
import { VagasService } from 'src/app/services/vaga.service';

@Component({
  selector: 'app-vagas-form',
  templateUrl: './vagas-form.component.html',
  styleUrls: ['./vagas-form.component.css']
})
export class VagasFormComponent implements OnInit, OnDestroy {
  public readonly statusOptions: Array<PoSelectOption> = [
    { label: 'Aberto', value: 'OPEN' },
    { label: 'Fechado', value: 'CLOSED' },
  ];

  public vaga: any = { name: '', status: ''};
  public action: string = 'new';
  private paramsSub!: Subscription;

  constructor(private vagasService: VagasService, private router: Router, private route: ActivatedRoute, private poNotification: PoNotificationService) { }

  ngOnDestroy() {
    this.paramsSub.unsubscribe();
  }
  ngOnInit(): void {
    this.paramsSub = this.route.params.subscribe(params => {
      if (params['id']) {
        this.vagasService.getVagaById(params['id']).subscribe(res => this.vaga = res);
        this.action = 'edit';
      }
    });
  }

  save(){
    if(this.vaga.id) {
      console.log(this.vaga)
      this.vagasService.update(this.vaga).subscribe(() => this.navigateToList("Vaga editada com sucesso")); 
    } else {
      this.vagasService.create(this.vaga).subscribe(() => this.navigateToList("Vaga salva com sucesso"));
    }
  }

  cancel(){
    this.router.navigateByUrl('/vagas');
  }

  get title() {
    return this.action === 'new' ? 'Nova Vaga': 'Editar Vaga';
  }

  private navigateToList(msg: string) {
    this.poNotification.success(msg);

    this.router.navigateByUrl('/vagas');
  }

}
