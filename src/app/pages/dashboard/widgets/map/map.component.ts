import { Component } from '@angular/core';

@Component({
  selector: 'app-map',
  standalone: true,
  imports: [],
  template: `
    <div class="container">
      <div class="map-area">
        <span class="map-placeholder">📡 LiDAR Data Incoming...</span>
      </div>
      <div class="footer">
        <span>🗺️ 2D Map</span>
        <span>🔄 Updating</span>
      </div>
    </div>
  `,
  //TODO: wenn data geht noch machen
  styles: [`
    .container {
      display: flex;
      flex-direction: column;
      height: 100%;
    }

    .map-area {
      flex-grow: 1;
      background: #f5f5f5;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
      padding: 12px;
    }

    .map-placeholder {
      color: #777;
      font-size: 16px;
      font-style: italic;
    }

    .footer {
      display: flex;
      justify-content: space-between;
      padding: 6px 8px 0 8px;
      font-size: 13px;
      color: #444;
    }
  `]
})
export class MapComponent {}
