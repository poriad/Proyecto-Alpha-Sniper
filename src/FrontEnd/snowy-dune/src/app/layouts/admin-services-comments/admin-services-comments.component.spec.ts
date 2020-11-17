import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminServicesCommentsComponent } from './admin-services-comments.component';

describe('AdminServicesCommentsComponent', () => {
  let component: AdminServicesCommentsComponent;
  let fixture: ComponentFixture<AdminServicesCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminServicesCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminServicesCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
