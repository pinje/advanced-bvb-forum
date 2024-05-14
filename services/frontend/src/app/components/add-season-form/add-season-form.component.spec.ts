import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSeasonFormComponent } from './add-season-form.component';

describe('AddSeasonFormComponent', () => {
  let component: AddSeasonFormComponent;
  let fixture: ComponentFixture<AddSeasonFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddSeasonFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddSeasonFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
