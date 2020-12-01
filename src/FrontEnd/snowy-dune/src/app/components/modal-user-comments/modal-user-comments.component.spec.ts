import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalUserCommentsComponent } from './modal-user-comments.component';

describe('ModalUserCommentsComponent', () => {
  let component: ModalUserCommentsComponent;
  let fixture: ComponentFixture<ModalUserCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalUserCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalUserCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
