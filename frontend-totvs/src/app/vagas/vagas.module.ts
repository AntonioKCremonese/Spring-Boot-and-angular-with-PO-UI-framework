import { NgModule } from '@angular/core';

import { VagasRoutingModule } from './vagas-routing.module';
import { VagasListComponent } from './vagas-list/vagas-list.component';
import { VagasFormComponent } from './vagas-form/vagas-form.component';

import { SharedModule } from '../shared/shared.module';
import { VagasCandidatosComponent } from './vagas-candidatos/vagas-candidatos.component';

@NgModule({
  declarations: [
    VagasListComponent,
    VagasFormComponent,
    VagasCandidatosComponent,
  ],
  imports: [
    SharedModule,
    VagasRoutingModule,
  ]
})
export class VagasModule { }