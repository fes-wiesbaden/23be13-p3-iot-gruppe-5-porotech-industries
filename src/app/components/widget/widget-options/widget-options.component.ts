import { Component, model } from '@angular/core';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatButtonToggle } from '@angular/material/button-toggle';

import { MatButtonToggleGroup } from '@angular/material/button-toggle';
@Component({
  selector: 'app-widget-options',
  imports: [MatButtonModule, MatIcon, MatButtonToggle, MatButtonToggleGroup],
  template: `
    <button mat-icon-button 
    class="close-button" 
    (click)="showOptions.set(false)"
    >
      <mat-icon>close</mat-icon>
    </button>

    <div>
      Width
      <mat-button-toggle-group hideSingleSelectionIndicator="true" [value]="data().rows ?? 1">
        <mat-button-toggle [value]="1">1</mat-button-toggle>
        <mat-button-toggle [value]="2">2</mat-button-toggle>
        <mat-button-toggle [value]="3">3</mat-button-toggle>
        <mat-button-toggle [value]="4">4</mat-button-toggle>
      </mat-button-toggle-group>
    </div>

    
    <div>
      Height
      <mat-button-toggle-group hideSingleSelectionIndicator="true">
        <mat-button-toggle [value]="1">1</mat-button-toggle>
        <mat-button-toggle [value]="2">2</mat-button-toggle>
        <mat-button-toggle [value]="3">3</mat-button-toggle>
        <mat-button-toggle [value]="4">4</mat-button-toggle>
      </mat-button-toggle-group>
    </div>
  `,
  styles: `
  
  :host {
    position: absolute;
    z-index: 2;
    background: whitesmoke;
    color: black;
    top: 0;
    left: 0;
    border-radius: inherit;
    width: 100%;
    height: 100%;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
     --mat-standard-button-toggle-height: 16px;
  
    > div {
      display: flex;
      gap: 8px;
      align-items: center;
      margin-bottom: 8px;
    }

  }
  
  .close-button {
    position: absolute;
    top: 0;
    right: 0;
  }


  `
})
export class WidgetOptionsComponent {

  showOptions = model<boolean>(false);

}
