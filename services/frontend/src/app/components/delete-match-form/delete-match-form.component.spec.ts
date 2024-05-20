import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteMatchFormComponent } from './delete-match-form.component';

describe('DeleteMatchFormComponent', () => {
  let component: DeleteMatchFormComponent;
  let fixture: ComponentFixture<DeleteMatchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteMatchFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteMatchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
