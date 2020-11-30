import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDialogStationComponent } from './confirm-dialog-station.component';

describe('ConfirmDialogStationComponent', () => {
  let component: ConfirmDialogStationComponent;
  let fixture: ComponentFixture<ConfirmDialogStationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmDialogStationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmDialogStationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
