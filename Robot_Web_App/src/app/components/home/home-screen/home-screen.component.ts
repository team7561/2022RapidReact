import { Component, OnInit } from '@angular/core';
import { GetRobotDataService } from 'src/app/services/robot-data/get-robot-data.service';
import { GraphDataService } from 'src/app/services/graph-data/graph-data.service';

@Component({
  selector: 'app-home-screen',
  templateUrl: './home-screen.component.html',
  styleUrls: ['./home-screen.component.scss']
})
export class HomeScreenComponent implements OnInit {
  public inputType: string = "connection";
  public innerWidth: number;

  constructor(private robotDataService: GetRobotDataService, private graphData: GraphDataService){}

  ngOnInit():void{
    this.innerWidth = window.innerWidth;
  }

}
