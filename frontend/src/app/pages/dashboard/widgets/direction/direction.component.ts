import { Component } from '@angular/core';

@Component({
  selector: 'app-direction',
  standalone: true,
  imports: [],
  template: `
    <div>
      <div class="content">
        <i class="material-icons">explore</i>
        <span class="value">North</span>
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
      color: #3f51b5;
      font-size: 28px;
    }

    .value {
      font-size: 20px;
      color: #333;
    }
  `]
})
export class DirectionComponent {}
