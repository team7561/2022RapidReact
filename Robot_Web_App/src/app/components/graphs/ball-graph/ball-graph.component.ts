import { Component, OnInit, ViewChild } from '@angular/core';
import { ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';

@Component({
  selector: 'app-ball-graph',
  templateUrl: './ball-graph.component.html',
  styleUrls: ['./ball-graph.component.scss']
})
export class BallGraphComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;
  public ballChartLabels: ChartConfiguration["data"]["labels"] = [];
  public ballChartData: ChartConfiguration["data"]["datasets"] = [
    {
      "data": [],
      "label": "Ball Position",
      "pointRadius": 10
    }
  ];
  public ballChartConfig: ChartConfiguration["options"];
  private lastVisionXVal: number;

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.initChart();
    this.globalVars.getSubject().subscribe(()=>{
      if(parseFloat(this.globalVars.getVar("ball_x_coord")) != this.lastVisionXVal){
        this.movePoint(parseFloat(this.globalVars.getVar("ball_x_coord")))
      }
    })
  }
  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  initChart():void{
    this.ballChartConfig = {
      animation: {
        duration: 250
      },
      elements: {
        point: {
          radius: 10
        }
      },
      scales: {
        "x-axis-0": {
          min: -30,
          max: 30
        },
        'y-axis-0':
          {
            position: 'left',
            
            min: -30,
            max: 30
        }
      }
    };
  }

  movePoint(ballX: number):void{
    this.ballChartData[0]["data"] = [{x: ballX, y: 0, r: 20}];
    this.lastVisionXVal = ballX;
    this.chart.update();
  }


}
