import { computed, Injectable, signal } from '@angular/core';
import { TempratureComponent } from '../pages/dashboard/widgets/temprature/temprature.component';
import { Widget } from '../models/dashboard';
import { AngleComponent } from '../pages/dashboard/widgets/angle/angle.component';
import { CamComponent } from '../pages/dashboard/widgets/cam/cam.component';
import { BatteryComponent } from '../pages/dashboard/widgets/battery/battery.component';
import { InfraredComponent } from '../pages/dashboard/widgets/infrared/infrared.component';
import { DirectionComponent } from '../pages/dashboard/widgets/direction/direction.component';
import { SpeedComponent } from '../pages/dashboard/widgets/speed/speed.component';
import { MapComponent } from '../pages/dashboard/widgets/map/map.component';
import { StatusComponent } from '../pages/dashboard/widgets/status/status.component';

@Injectable()
export class DashboardService {
  widgets = signal<Widget[]>([
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
      content: InfraredComponent,
      rows: 2,
      columns: 1
    },
    {
      id: 5,
      label: 'Direction (North)',
      content: DirectionComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 6,
      label: 'Speed',
      content: SpeedComponent,
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
      content: MapComponent,
      rows: 2,
      columns: 2
    },
    {
      id: 9,
      label: 'Status',
      content: StatusComponent,
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
      content: InfraredComponent,
      rows: 2,
      columns: 1
    },
    {
      id: 5,
      label: 'Direction (North)',
      content: DirectionComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 6,
      label: 'Speed',
      content: SpeedComponent,
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
      content: MapComponent,
      rows: 2,
      columns: 2
    },
    {
      id: 9,
      label: 'Status',
      content: StatusComponent,
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
