import { NgModule } from '@angular/core';

import { SharedModule } from '../shared/shared.module';
import { CandidatosFormComponent } from './candidatos-form/candidatos-form.component';
import { CandidatosListComponent } from './candidatos-list/candidatos-list.component';
import { CandidatosRoutingModule } from './candidatos-routing.module';

@NgModule({
  declarations: [
    CandidatosListComponent,
    CandidatosFormComponent,
  ],
  imports: [
    SharedModule,
    CandidatosRoutingModule,
  ]
})
export class CandidatosModule { }