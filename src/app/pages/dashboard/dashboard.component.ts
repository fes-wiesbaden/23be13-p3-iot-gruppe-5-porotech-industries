import { Component, inject } from '@angular/core';
import { WidgetComponent } from "../../components/widget/widget.component";
import { Widget } from '@/app/models/dashboard';
import { DashboardService } from '@/app/services/dashboard.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

@Component({
  selector: 'app-dashboard',
  imports: [WidgetComponent, MatButtonModule, MatIcon, MatMenuModule],
  providers: [DashboardService],
  template: `
    <div class="header">
      <h2>Dashboard</h2>
      <button mat-raised-button [mat-menu-trigger-for]="widgetMenu">
        <mat-icon>add</mat-icon>
        Add Widget
      </button>
      <mat-menu #widgetMenu="matMenu">
        @for (widget of store.widgetsToAdd(); track widget.id) {
          <button mat-menu-item (click)="store.addWidget(widget)">
            {{ widget.label }}
            <mat-icon>add</mat-icon>
          </button>

        }
      </mat-menu>
    </div>

    <div class="dashboard-widgets">
      @for (w of store.addedWidgets(); track w.id) {
      <app-widget [data]="w" />
      }
    </div>

  `,
  styles: `
  
  .dashboard-widgets {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 16px;
  }

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  `
})
export class DashboardComponent {
  store = inject(DashboardService);
}
