import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCarRentalComponent } from './admin-car-rental.component';

describe('AdminCarRentalComponent', () => {
  let component: AdminCarRentalComponent;
  let fixture: ComponentFixture<AdminCarRentalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminCarRentalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminCarRentalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
