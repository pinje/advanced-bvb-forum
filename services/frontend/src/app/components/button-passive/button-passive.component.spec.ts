import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonPassiveComponent } from './button-passive.component';

describe('ButtonPassiveComponent', () => {
  let component: ButtonPassiveComponent;
  let fixture: ComponentFixture<ButtonPassiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonPassiveComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ButtonPassiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
