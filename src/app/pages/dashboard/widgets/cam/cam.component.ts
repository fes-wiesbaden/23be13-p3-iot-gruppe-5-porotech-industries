import { Component } from '@angular/core';

@Component({
  selector: 'app-cam',
  standalone: true,
  imports: [],
  template: `
    <div class="container">
      <div class="content">
        <img
          alt="Live camera feed"
          src="501-0667_t.png"
          class="cam-image"
        />
      </div>
      <div class="footer">
        <span>📡 Live</span>
        <span>🟢 Connected</span>
      </div>
    </div>
  `,
  //TODO: data backend
  styles: [`
    .container {
      display: flex;
      flex-direction: column;
      height: 100%;
    }

    .content {
      background: #f5f5f5;
      border-radius: 10px;
      padding: 4px;
      flex-grow: 1;
      display: flex;
      justify-content: center;
      align-items: center;
      overflow: hidden;
    }

    .cam-image {
      width: 100%;
      height: auto;
      max-height: 100%;
      object-fit: cover;
      border-radius: 6px;
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
export class CamComponent {}
