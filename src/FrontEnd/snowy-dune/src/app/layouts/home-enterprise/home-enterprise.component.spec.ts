import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeEnterpriseComponent } from './home-enterprise.component';

describe('HomeEnterpriseComponent', () => {
  let component: HomeEnterpriseComponent;
  let fixture: ComponentFixture<HomeEnterpriseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeEnterpriseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeEnterpriseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
