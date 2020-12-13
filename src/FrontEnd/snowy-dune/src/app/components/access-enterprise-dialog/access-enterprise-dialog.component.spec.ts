import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessEnterpriseDialogComponent } from './access-enterprise-dialog.component';

describe('AccessEnterpriseDialogComponent', () => {
  let component: AccessEnterpriseDialogComponent;
  let fixture: ComponentFixture<AccessEnterpriseDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccessEnterpriseDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccessEnterpriseDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
