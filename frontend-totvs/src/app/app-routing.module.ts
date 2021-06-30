import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  {path:'vagas', loadChildren: () => import('./vagas/vagas.module').then(m => m.VagasModule)},
  {path:'candidatos', loadChildren: () => import('./candidatos/candidatos.module').then(m => m.CandidatosModule)},
  {path:'home', component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
