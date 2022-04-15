import { Component } from '@angular/core';
import { DynamicGlobalsService } from './services/dynamic-globals.service';
import { GetRobotDataService } from './services/get-robot-data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Robot_Web_App';

  constructor(private robotDataService: GetRobotDataService){}

  ngOnInit():void{
    this.robotDataService.startDataStream(); // Initialize the data stream between robot and client
  }
}

