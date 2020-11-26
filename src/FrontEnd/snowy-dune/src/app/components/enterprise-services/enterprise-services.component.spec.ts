import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnterpriseServicesComponent } from './enterprise-services.component';

describe('EnterpriseServicesComponent', () => {
  let component: EnterpriseServicesComponent;
  let fixture: ComponentFixture<EnterpriseServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnterpriseServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnterpriseServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
