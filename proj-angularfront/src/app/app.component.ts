import { Component } from '@angular/core';
import {sample} from 'proj-common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})


export class AppComponent {
  sample = new sample.Sample();
  platform = sample.Platform;
  title = 'angularfront';
  tester = new sample.Tester();

  constructor() {
    sample.delayedAsync(this.tester);
  }
}
