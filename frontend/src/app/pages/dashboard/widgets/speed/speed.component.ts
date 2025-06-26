import { Component } from '@angular/core';
import { MatIcon } from '@angular/material/icon';


@Component({
  selector: 'app-speed',
  standalone: true,
  imports: [MatIcon],
  template: `
    <div class="content">
      <mat-icon class="speed-icon">speed</mat-icon>
      <span class="value">Current: {{ speed }} km/h</span>
    </div>
  `,
  styles: [`
    .content {
      background: #f5f5f5;
      border-radius: 10px;
      padding: 10px;
      display: flex;
      align-items: center;
      gap: 10px;
      font-family: 'Roboto', sans-serif;
    }

    .speed-icon {
      font-size: 24px;
      color: #3f51b5;
    }

    .value {
      font-size: 16px;
      font-weight: 500;
      color: #333;
    }
  `]
})
export class SpeedComponent {
  speed = 24; //TODO: backend connection
}
