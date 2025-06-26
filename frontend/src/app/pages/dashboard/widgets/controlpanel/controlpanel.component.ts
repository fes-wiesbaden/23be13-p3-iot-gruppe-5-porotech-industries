import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-controls',
  standalone: true,
  imports: [MatIconModule, MatButtonModule],
  template: `
    <div class="content">
      <div class="row center">
        <button mat-icon-button color="primary" (click)="onControl('forward')">
          <mat-icon>arrow_upward</mat-icon>
        </button>
      </div>

      <div class="row space-between">
        <button mat-icon-button color="primary" (click)="onControl('left')">
          <mat-icon>arrow_back</mat-icon>
        </button>
        <button mat-icon-button color="warn" (click)="onControl('stop')">
          <mat-icon>stop</mat-icon>
        </button>
        <button mat-icon-button color="primary" (click)="onControl('right')">
          <mat-icon>arrow_forward</mat-icon>
        </button>
      </div>

      <div class="row center">
        <button mat-icon-button color="primary" (click)="onControl('backward')">
          <mat-icon>arrow_downward</mat-icon>
        </button>
      </div>
    </div>
  `,
  styles: [`
    .content {
      background: #f5f5f5;
      border-radius: 10px;
      padding: 10px;
      display: flex;
      flex-direction: column;
      gap: 10px;
      font-family: 'Roboto', sans-serif;
    }

    .row {
      display: flex;
      gap: 12px;
    }

    .center {
      justify-content: center;
    }

    .space-between {
      justify-content: space-between;
    }

    mat-icon {
      font-size: 24px;
    }
  `]
})
export class ControlsComponent {
  onControl(direction: string) {
    console.log('Manual control:', direction);
    // TODO: Send control command to backend
  }
}
