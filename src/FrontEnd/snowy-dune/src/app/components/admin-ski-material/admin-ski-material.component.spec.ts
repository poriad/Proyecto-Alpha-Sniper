import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSkiMaterialComponent } from './admin-ski-material.component';

describe('AdminSkiMaterialComponent', () => {
  let component: AdminSkiMaterialComponent;
  let fixture: ComponentFixture<AdminSkiMaterialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminSkiMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminSkiMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
