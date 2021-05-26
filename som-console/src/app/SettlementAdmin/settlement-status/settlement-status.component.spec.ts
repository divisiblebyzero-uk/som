import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SettlementStatusComponent } from './settlement-status.component';

describe('SettlementStatusComponent', () => {
  let component: SettlementStatusComponent;
  let fixture: ComponentFixture<SettlementStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SettlementStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SettlementStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
