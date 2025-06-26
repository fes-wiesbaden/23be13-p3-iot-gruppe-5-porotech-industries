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
import { ControlsComponent } from '../pages/dashboard/widgets/controlpanel/controlpanel.component';
import { MotorpowerComponent } from '../pages/dashboard/widgets/motorpower/motorpower.component';


@Injectable()
export class DashboardService {
  widgets = signal<Widget[]>([
    {
      id: 1,
      label: 'Cam',
      content: CamComponent,
      rows: 3,
      columns: 3
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
      rows: 1,
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
      label: 'Status',
      content: StatusComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 7,
      label: 'Motorpower',
      content: MotorpowerComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 8,
      label: 'Speed',
      content: SpeedComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 9,
      label: 'Battery',
      content: BatteryComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 10,
      label: 'Map (surroundings)',
      content: MapComponent,
      rows: 2,
      columns: 2
    },
    { 
      id: 11,
      label: 'ControlPanel',
      content: ControlsComponent,
      rows: 2,
      columns: 2
    }
  ]);

  addedWidgets = signal<Widget[]>([
    {
      id: 1,
      label: 'Cam',
      content: CamComponent,
      rows: 3,
      columns: 3
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
      rows: 1,
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
      label: 'Status',
      content: StatusComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 7,
      label: 'Motorpower',
      content: MotorpowerComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 8,
      label: 'Speed',
      content: SpeedComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 9,
      label: 'Battery',
      content: BatteryComponent,
      rows: 1,
      columns: 1
    },
    {
      id: 10,
      label: 'Map (surroundings)',
      content: MapComponent,
      rows: 2,
      columns: 2
    },
    { 
      id: 11,
      label: 'ControlPanel',
      content: ControlsComponent,
      rows: 2,
      columns: 2
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
