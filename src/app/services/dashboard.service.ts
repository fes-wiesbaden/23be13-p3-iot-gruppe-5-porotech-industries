import { computed, Injectable, signal } from '@angular/core';
import { TempratureComponent } from '../pages/dashboard/widgets/temprature/temprature.component';
import { Widget } from '../models/dashboard';
import { AngleComponent } from '../pages/dashboard/widgets/angle/angle.component';
import { CamComponent } from '../pages/dashboard/widgets/cam/cam.component';
import { BatteryComponent } from '../pages/dashboard/widgets/battery/battery.component';

@Injectable()
export class DashboardService {
  widgets = signal<Widget[]>([
    {
      id: 1,
      label: 'Cam',
      content: TempratureComponent,
      rows: 2,
      columns: 2
    },
    {
      id: 2,
      label: 'Temperatur',
      content: TempratureComponent,
      rows: 1,
      columns: 1,
    },
    {
      id: 3,
      label: 'Angle',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 4,
      label: 'Infra Red (3)',
      content: AngleComponent,
      rows: 2,
      columns: 1
    },
    {
      id: 5,
      label: 'Direction (North)',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 6,
      label: 'Speed',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 7,
      label: 'Battery',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 8,
      label: 'Map (surroundings)',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 9,
      label: 'Status',
      content: AngleComponent,
      rows: 1,
      columns: 1
    }
  ]);

  addedWidgets = signal<Widget[]>([
    {
      id: 1,
      label: 'Cam',
      content: CamComponent,
      rows: 2,
      columns: 2
    },
    {
      id: 2,
      label: 'Temperatur',
      content: TempratureComponent,
      rows: 1,
      columns: 1,
    },
    {
      id: 3,
      label: 'Angle',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 4,
      label: 'Infra Red (3)',
      content: AngleComponent,
      rows: 2,
      columns: 1
    },
    {
      id: 5,
      label: 'Direction (North)',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 6,
      label: 'Speed',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 7,
      label: 'Battery',
      content: BatteryComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 8,
      label: 'Map (surroundings)',
      content: AngleComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 9,
      label: 'Status',
      content: AngleComponent,
      rows: 1,
      columns: 1
    }
  ]);

  widgetsToAdd = computed(() => {
    const addedIds = this.addedWidgets().map(w => w.id);
    return this.widgets().filter(w => !addedIds.includes(w.id));
  })

  addWidget(w: Widget) {
    this.addedWidgets.set([...this.addedWidgets(), { ...w }]);
  }

  constructor() { }
}
