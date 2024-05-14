import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteSeasonFormComponent } from './delete-season-form.component';

describe('DeleteSeasonFormComponent', () => {
  let component: DeleteSeasonFormComponent;
  let fixture: ComponentFixture<DeleteSeasonFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteSeasonFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteSeasonFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
