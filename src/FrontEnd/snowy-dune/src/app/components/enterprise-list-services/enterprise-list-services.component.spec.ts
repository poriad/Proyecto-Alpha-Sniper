import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnterpriseListServicesComponent } from './enterprise-list-services.component';

describe('EnterpriseListServicesComponent', () => {
  let component: EnterpriseListServicesComponent;
  let fixture: ComponentFixture<EnterpriseListServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnterpriseListServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnterpriseListServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
