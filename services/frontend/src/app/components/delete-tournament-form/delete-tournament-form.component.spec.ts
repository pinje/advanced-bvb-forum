import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteTournamentFormComponent } from './delete-tournament-form.component';

describe('DeleteTournamentFormComponent', () => {
  let component: DeleteTournamentFormComponent;
  let fixture: ComponentFixture<DeleteTournamentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteTournamentFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteTournamentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
