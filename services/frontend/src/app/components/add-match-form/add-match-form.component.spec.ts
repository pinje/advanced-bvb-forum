import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMatchFormComponent } from './add-match-form.component';

describe('AddMatchFormComponent', () => {
  let component: AddMatchFormComponent;
  let fixture: ComponentFixture<AddMatchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddMatchFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddMatchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
