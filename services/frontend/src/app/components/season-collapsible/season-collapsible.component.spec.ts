import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeasonCollapsibleComponent } from './season-collapsible.component';

describe('SeasonCollapsibleComponent', () => {
  let component: SeasonCollapsibleComponent;
  let fixture: ComponentFixture<SeasonCollapsibleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SeasonCollapsibleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SeasonCollapsibleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
