import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSliderModule } from '@angular/material/slider';

@Component({
  selector: 'app-speed',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatSliderModule,
  ],
  template: `
    <div class="container">
      <div class="content">
        <mat-icon class="speed-icon">speed</mat-icon>
        <span class="value">{{ value }} % Power</span>
      </div>
      <mat-slider
        class="slider"
        [disabled]="disabled"
        [max]="max"
        [min]="min"
        [step]="step"
      >
        <input matSliderThumb [(ngModel)]="value" />
      </mat-slider>
    </div>
  `,
  //TODO: data backend
  styles: [`
    .container {
      font-family: 'Roboto', sans-serif;
    }

    .content {
      background: #f5f5f5;
      border-radius: 10px;
      padding: 6px 10px;
      display: flex;
      align-items: center;
      gap: 8px;
      margin-bottom: 6px;
    }

    .speed-icon {
      font-size: 20px;
      color: #3f51b5;
      line-height: 1;
    }

    .value {
      font-size: 15px;
      font-weight: 500;
      color: #333;
      line-height: 1;
    }

    .slider {
      width: 100%;
    }
  `],
})
export class MotorpowerComponent {
  value = 50;
  min = 0;
  max = 100;
  step = 1;
  disabled = false;
}
