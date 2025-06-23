import { Injectable, signal } from '@angular/core';
import { TempratureComponent } from '../pages/dashboard/widgets/temprature/temprature.component';
import { Widget } from '../models/dashboard';
import { AngleComponent } from '../pages/dashboard/widgets/angle/angle.component';

@Injectable()
export class DashboardService {
  widgets = signal<Widget[]>([
    {
      id: 1,
      label: 'Temperatur',
      content: TempratureComponent,
    },
    {
      id: 2,
      label: 'Angle',
      content: AngleComponent,
    },
  ]);

  constructor() { }
}
