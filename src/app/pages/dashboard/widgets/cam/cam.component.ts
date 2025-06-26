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
      [border]="1"
    />
  `,
styles: `

  .cam-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    margin: 0 auto;
  }`
})
export class CamComponent {

}
