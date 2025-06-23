import { computed, Injectable, signal } from '@angular/core';
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
      rows: 20,
      colums: 2,
    },
    {
      id: 2,
      label: 'Angle',
      content: AngleComponent,
    },
  ]);

  addedWidgets = signal<Widget[]>([]);

  widgetsToAdd = computed(() => {
    const addedIds = this.addedWidgets().map(w => w.id);
    return this.widgets().filter(w => !addedIds.includes(w.id));
  })

  addWidget(w: Widget) {
    this.addedWidgets.set([...this.addedWidgets(), { ...w }]);
  }

  constructor() { }
}
