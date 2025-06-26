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
    <div class="speed-display">
      <mat-icon class="speed-icon">speed</mat-icon>
      <span>{{ value }} % Power</span>
    </div>

    <div class="slider">
      <mat-slider
        
        class="example-margin"
        [disabled]="disabled"
        [max]="max"
        [min]="min"
        [step]="step"
      >
        <input matSliderThumb [(ngModel)]="value" />
      </mat-slider>
    </div>
  `,
  styles: [`
    .speed-display {
      display: flex;
      align-items: center;
      gap: 3px;
      font-size: 20px;
      margin-bottom: 12px;
    }

    .speed-icon {
      font-size: 24px;
    }

    .example-margin {
      width: 100%;
    }

    .slider {
      align: center;
    }
  `],
})
export class SpeedComponent {
  value = 50;
  min = 0;
  max = 100;
  step = 1;
  disabled = false;
center: any;
}
