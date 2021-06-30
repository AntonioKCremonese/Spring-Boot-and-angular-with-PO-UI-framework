import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CandidatosFormComponent } from './candidatos-form/candidatos-form.component';
import { CandidatosListComponent } from './candidatos-list/candidatos-list.component';


const routes: Routes = [
  { path: '', component:  CandidatosListComponent},
  { path: 'new', component: CandidatosFormComponent },
  { path: 'edit/:id', component: CandidatosFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidatosRoutingModule { }
