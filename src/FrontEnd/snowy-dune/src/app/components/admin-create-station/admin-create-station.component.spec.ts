import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCreateStationComponent } from './admin-create-station.component';

describe('AdminCreateStationComponent', () => {
  let component: AdminCreateStationComponent;
  let fixture: ComponentFixture<AdminCreateStationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminCreateStationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminCreateStationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
