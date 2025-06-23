import { Component, inject } from '@angular/core';
import { WidgetComponent } from "../../components/widget/widget.component";
import { Widget } from '@/app/models/dashboard';
import { DashboardService } from '@/app/services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  imports: [WidgetComponent],
  providers: [DashboardService],
  template: `
    <h1>Dashboard</h1>

    @for (w of store.widgets(); track w.id) {
    <app-widget [data]="w" />
  }
  `,
  styles: `
  
  `
})
export class DashboardComponent {
  store = inject(DashboardService);
}
