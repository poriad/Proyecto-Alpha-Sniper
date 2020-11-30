import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkiMaterialComponent } from './ski-material.component';

describe('SkiMaterialComponent', () => {
  let component: SkiMaterialComponent;
  let fixture: ComponentFixture<SkiMaterialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkiMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkiMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
