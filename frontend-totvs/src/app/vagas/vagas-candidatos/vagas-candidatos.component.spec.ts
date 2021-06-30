import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VagasCandidatosComponent } from './vagas-candidatos.component';

describe('VagasComponent', () => {
  let component: VagasCandidatosComponent;
  let fixture: ComponentFixture<VagasCandidatosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VagasCandidatosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VagasCandidatosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
