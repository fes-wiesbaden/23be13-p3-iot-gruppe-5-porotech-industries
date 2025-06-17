import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';


@Component({
  selector: 'app-root',
  imports: [
    CommonModule, 
    MatSidenavModule, 
    MatToolbarModule, 
    RouterOutlet, 
    MatIconModule],
  template: `
  <mat-toolbar class="mat-elevation-z3">
    <button mat-icon-button>
      <mat-icon>menu</mat-icon>
    </button>
  </mat-toolbar>
  <mat-sidenav-container>
    <mat-sidenav>

    </mat-sidenav>
    <mat-sidenav-content class="content">
      <router-outlet></router-outlet>
    </mat-sidenav-content>
  </mat-sidenav-container>
  
  `,
  styles: [`
    .content {
      padding: 24px,
    }
   `],
})
export class AppComponent {
  title = 'porotech';
}
