import { Component } from '@angular/core';

@Component({
  selector: 'app-infrared',
  standalone: true,
  imports: [],
  template: `
    <div class="content">
      <div class="sensor">
        <span class="label">Front Left</span>
        <span class="status">📍 32 cm</span>
      </div>
      <div class="sensor">
        <span class="label">Front Right</span>
        <span class="status">📍 30 cm</span>
      </div>
      <div class="sensor">
        <span class="label">Back Center</span>
        <span class="status">📍 55 cm</span>
      </div>
    </div>
  `,
  //TODO: 3 data from backend
  styles: [`
    .content {
      background: #f5f5f5;
      border-radius: 10px;
      padding: 8px 10px;
      display: flex;
      flex-direction: column;
      gap: 6px;
      font-family: 'Roboto', sans-serif;
    }

    .sensor {
      display: flex;
      justify-content: space-between;
      font-size: 13px;
      color: #444;
    }

    .label {
      font-weight: 400;
    }

    .status {
      font-weight: 600;
    }
  `]
})
export class InfraredComponent {}
