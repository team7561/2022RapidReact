import { Component } from '@angular/core';
import { DynamicGlobalsService } from './services/dynamic-globals.service';
import { GetRobotDataService } from './services/get-robot-data.service';
import { GraphDataService } from './services/graph-data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Robot Web App';
  public inputType: string = "connection";
  public innerWidth: number;

  constructor(private robotDataService: GetRobotDataService, private graphData: GraphDataService){}

  ngOnInit():void{
    this.robotDataService.startDataStream(); // Initialize the data stream between robot and client
    this.graphData.beginGraphStream(); // Starts recording the graph data
    this.innerWidth = window.innerWidth
  }
}

