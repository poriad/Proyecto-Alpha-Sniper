import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StripePaymentModalComponent } from './stripe-payment-modal.component';

describe('StripePaymentModalComponent', () => {
  let component: StripePaymentModalComponent;
  let fixture: ComponentFixture<StripePaymentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StripePaymentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StripePaymentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
