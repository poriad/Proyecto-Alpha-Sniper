import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUsersEnterprisesComponent } from './admin-users-enterprises.component';

describe('AdminUsersEnterprisesComponent', () => {
  let component: AdminUsersEnterprisesComponent;
  let fixture: ComponentFixture<AdminUsersEnterprisesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminUsersEnterprisesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminUsersEnterprisesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
