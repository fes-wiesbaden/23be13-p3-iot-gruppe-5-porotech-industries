import { Component } from '@angular/core';

@Component({
  selector: 'app-temprature',
  standalone: true,
  imports: [],
  template: `
    <div class="content">
      <i class="material-icons">device_thermostat</i>
      <span class="value">28 °C</span>
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
      color: #ff5722; /* warm orange */
    }

    .value {
      font-size: 16px;
      font-weight: 500;
      color: #333;
    }
  `]
})
export class TempratureComponent {}
