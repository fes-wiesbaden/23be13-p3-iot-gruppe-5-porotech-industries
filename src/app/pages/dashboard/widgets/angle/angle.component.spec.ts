import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AngleComponent } from './angle.component';

describe('AngleComponent', () => {
  let component: AngleComponent;
  let fixture: ComponentFixture<AngleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AngleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AngleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
