import { Component } from '@angular/core';
import { WidgetComponent } from "../../components/widget/widget.component";
import { Widget } from '@/app/models/dashboard';
import { TempratureComponent } from './widgets/temprature/temprature.component';

@Component({
  selector: 'app-dashboard',
  imports: [WidgetComponent],
  template: `
    <h1>Dashboard</h1>

    <app-widget [data]="data"></app-widget>
  `,
  styles: `
  
  `
})
export class DashboardComponent {

  data: Widget = {
    id: 1,
    label: 'Temperatur',
    content: TempratureComponent
  }
}
