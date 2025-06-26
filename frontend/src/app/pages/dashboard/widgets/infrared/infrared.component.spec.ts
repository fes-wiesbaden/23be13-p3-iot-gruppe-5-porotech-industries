import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfraredComponent } from './infrared.component';

describe('InfraredComponent', () => {
  let component: InfraredComponent;
  let fixture: ComponentFixture<InfraredComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfraredComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfraredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
