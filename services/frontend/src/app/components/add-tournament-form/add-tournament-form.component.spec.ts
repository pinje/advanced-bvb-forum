import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTournamentFormComponent } from './add-tournament-form.component';

describe('AddTournamentFormComponent', () => {
  let component: AddTournamentFormComponent;
  let fixture: ComponentFixture<AddTournamentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddTournamentFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddTournamentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
