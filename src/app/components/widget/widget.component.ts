import { Widget } from '@/app/models/dashboard';
import { NgComponentOutlet } from '@angular/common';
import { Component, input } from '@angular/core';

@Component({
  selector: 'app-widget',
  imports: [NgComponentOutlet],
  template: `
    <div class="container mat-elevation-z3">
      <h3 class="m-0">{{ data().label }}</h3>
      <ng-container [ngComponentOutlet]="data().content" />
    </div>
  `,
  styles: `
  
    :host {
      display: block;
      border-radius: 16px;
    }

    .container {
      position: relative;
      height: 100%;
      width: 100%;
      padding: 32px;
      box-sizing: border-box;
      border-radius: inherit;
      overflow: hidden;
    }
  `
})
export class WidgetComponent {
  data = input.required<Widget>();
}
