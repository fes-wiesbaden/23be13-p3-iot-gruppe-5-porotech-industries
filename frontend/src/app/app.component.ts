import { Component, computed, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { CustomSidenavComponent } from "./components/custom-sidenav/custom-sidenav.component";


@Component({
  selector: 'app-root',
  imports: [
    MatSidenavModule,
    MatToolbarModule,
    RouterOutlet,
    MatIconModule,
    CustomSidenavComponent
],
  template: `
  <mat-toolbar class="mat-elevation-z3">
    <button mat-icon-button (click)="collapsed.set(!collapsed())">
      <mat-icon>menu</mat-icon>
    </button>
  </mat-toolbar>
  <mat-sidenav-container>
    <mat-sidenav opened mode="side" [style.width]="sidenavWidth()">
     <app-custom-sidenav [collapsed]="collapsed()"/>
    </mat-sidenav>
    <mat-sidenav-content class="content" [style.margin-left]="sidenavWidth()">
      <router-outlet>
      </router-outlet>
    </mat-sidenav-content>
  </mat-sidenav-container>
  
  `,
  styles: [`

    mat-toolbar {
      position: relative;
      z-index: 5;
    }

    .content {
      padding: 24px,
    }

    mat-sidenav-container {
      height: calc(100vh - 64px);
    }

    mat-sidenav,
    mat-sidenav-content {
      transition: all 150ms ease-in-out;
    }

   `],
})
export class AppComponent {
  title = 'porotech';

  collapsed = signal(false);

  sidenavWidth = computed(() => this.collapsed() ? '64px' : '250px');
}
