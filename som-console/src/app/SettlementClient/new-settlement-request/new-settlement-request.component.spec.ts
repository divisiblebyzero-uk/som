import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSettlementRequestComponent } from './new-settlement-request.component';

describe('NewSettlementRequestComponent', () => {
  let component: NewSettlementRequestComponent;
  let fixture: ComponentFixture<NewSettlementRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewSettlementRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSettlementRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
