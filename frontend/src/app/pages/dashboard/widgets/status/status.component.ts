import { Component } from '@angular/core';

@Component({
  selector: 'app-status',
  standalone: true,
  imports: [],
  template: `
    <div class="content">
      <i class="material-icons">check_circle</i>
      <span class="value">Online</span>
    </div>
  `,
  //TODO: data
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

    .material-icons {
      font-size: 24px;
      color: #4caf50; /* green */
    }

    .value {
      font-size: 16px;
      font-weight: 500;
      color: #333;
    }
  `]
})
export class StatusComponent {}
