import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidatosFormComponent } from './candidatos-form.component';

describe('CandidatosFormComponent', () => {
  let component: CandidatosFormComponent;
  let fixture: ComponentFixture<CandidatosFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CandidatosFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidatosFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
