import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MotorpowerComponent } from './motorpower.component';

describe('MotorpowerComponent', () => {
  let component: MotorpowerComponent;
  let fixture: ComponentFixture<MotorpowerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MotorpowerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MotorpowerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
