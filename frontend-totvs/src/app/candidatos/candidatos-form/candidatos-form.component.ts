import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PoNotificationService, PoSelectOption, PoUploadComponent } from '@po-ui/ng-components';
import { PoUploadFile } from '@po-ui/ng-components/lib/components/po-field/po-upload/po-upload-file';
import { Subscription } from 'rxjs';
import { CandidatoService } from 'src/app/services/candidato.service';
import { FileService } from 'src/app/services/file.service';
import { VagasService } from 'src/app/services/vaga.service';

@Component({
  selector: 'app-candidatos-form',
  templateUrl: './candidatos-form.component.html',
  styleUrls: ['./candidatos-form.component.css']
})
export class CandidatosFormComponent implements OnInit, OnDestroy {
  private paramsSub!: Subscription;
  private action: string = 'new';
  
  public vagaOptions: Array<PoSelectOption> = [];
  public candidato: any = { name: '', mail: '', phone: '', vaga: {id: ''}};

  @ViewChild(PoUploadComponent, { static: true }) upload!: PoUploadComponent;
  
  constructor(private vagasService: VagasService, 
    private candidatoService: CandidatoService, 
    private fileService: FileService, 
    private router: Router, 
    private route: ActivatedRoute, 
    private poNotification: PoNotificationService) {}
  
  ngOnDestroy() {
    this.paramsSub.unsubscribe();
  }

  ngOnInit(): void {
    this.vagasService.getAllVagas().subscribe(res => {
      if(res.length > 0){
        this.vagaOptions = res.map(vaga => {
          return {
            label: vaga.name,
            value: vaga.id,
          }
        })
      }
    });

    this.paramsSub = this.route.params.subscribe(params => {
      if (params['id']) {
        this.candidatoService.getCandidatoById(params['id']).subscribe(res => {
          this.candidato = {
            id: res.id,
            name: res.name,
            mail: res.mail,
            phone: res.phone,
            vaga: res.vaga,
            file: res.file,
          }
        });
        this.action = 'edit';
      }
    });
  }

  get title(){
    return this.action === 'new' ? 'Novo Candidato': 'Editar Candidato';
  }

  sendFile(){
    this.upload.sendFiles();
  }

  save(){
    if(this.candidato.id){
      this.candidatoService.update(this.candidato).subscribe(() => this.navigateToList("Candidato atualizado com sucesso"));
    } else {
      this.candidatoService.create(this.candidato).subscribe(() => this.navigateToList("Candidato salvo com sucesso"));
    }
  }

  cancel(){
    this.router.navigateByUrl('/candidatos');
  }

  uploadFile(event:any){
    event.data = {'candidato_id': this.candidato.id};
  }

  successUpload(file:PoUploadFile){
    this.poNotification.success("Curriculo cadastrado com sucesso");
    this.router.navigateByUrl('/candidatos');
  }

  errorUpload(event:any){
    this.poNotification.error("Deu ruim");
  }

  private navigateToList(msg: string) {
    this.poNotification.success(msg);

    this.router.navigateByUrl('/candidatos');
  }

}
