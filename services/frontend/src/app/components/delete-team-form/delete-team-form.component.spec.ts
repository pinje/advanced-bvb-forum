import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteTeamFormComponent } from './delete-team-form.component';

describe('DeleteTeamFormComponent', () => {
  let component: DeleteTeamFormComponent;
  let fixture: ComponentFixture<DeleteTeamFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteTeamFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteTeamFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
