import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VagasCandidatosComponent } from './vagas-candidatos/vagas-candidatos.component';
import { VagasFormComponent } from './vagas-form/vagas-form.component';
import { VagasListComponent } from './vagas-list/vagas-list.component';


const routes: Routes = [
  { path: '', component:  VagasListComponent},
  { path: 'new', component: VagasFormComponent },
  { path: 'edit/:id', component: VagasFormComponent },
  { path: 'candidatos', component: VagasCandidatosComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VagasRoutingModule { }
