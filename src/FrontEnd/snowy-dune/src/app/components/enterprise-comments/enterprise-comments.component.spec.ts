import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnterpriseCommentsComponent } from './enterprise-comments.component';

describe('EnterpriseCommentsComponent', () => {
  let component: EnterpriseCommentsComponent;
  let fixture: ComponentFixture<EnterpriseCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnterpriseCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnterpriseCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
