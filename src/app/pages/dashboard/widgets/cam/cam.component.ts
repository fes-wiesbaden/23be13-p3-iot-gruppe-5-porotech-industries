import { Component } from '@angular/core';

@Component({
  selector: 'app-cam',
  imports: [],
  template: `
    <img
      alt="test cam not loading"
      src="501-0667_t.png"
      class="cam-image"
      sizes="100vw"
    />
  `,
styles: `
  :host {
    display: block;
    width: 100%;
    height: 100%;
  }

  .cam-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
  }`
})
export class CamComponent {

}
