import { Component } from '@angular/core';

@Component({
  selector: 'app-battery',
  standalone: true,
  imports: [],
  template: `
    <div>
      <div class="content">
        <i class="material-icons">battery_5_bar</i>
        <span class="value">64%</span>
      </div>
    </div>
  `,
  //TODO: data backend
  styles: [`
    .title {
      font-size: 14px;
      color: #333;
      margin-bottom: 8px;
      display: block;
    }

    .content {
      background: #f5f5f5;
      border-radius: 10px;
      padding: 12px;
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .material-icons {
      color: #4caf50;
      font-size: 28px;
    }

    .value {
      font-size: 20px;
      color: #333;
    }
  `]
})
export class BatteryComponent {}
