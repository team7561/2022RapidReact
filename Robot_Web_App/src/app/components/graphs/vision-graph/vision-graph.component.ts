import { BaseChartDirective } from 'ng2-charts';
import { Component, OnInit, ViewChild } from '@angular/core';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { ChartConfiguration } from 'chart.js';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-vision-graph',
  templateUrl: './vision-graph.component.html',
  styleUrls: ['./vision-graph.component.scss']
})
export class VisionGraphComponent implements OnInit {
  // It's basically the same as thue ball graph, look over there

  @ViewChild(BaseChartDirective) chart: BaseChartDirective;
  public visionChartLabels: ChartConfiguration["data"]["labels"] = [];
  public visionChartData: ChartConfiguration["data"]["datasets"] = [
    {
      "data": [],
      "label": "Vision target position",
      "pointRadius": 10
    }
  ];
  public visionChartConfig: ChartConfiguration["options"];
  private lastVisionXVal: number;

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.initChart();
    this.globalVars.getSubject().subscribe(()=>{
      if(parseFloat(this.globalVars.getVar("ta")) != this.lastVisionXVal){
        this.movePoint(parseFloat(this.globalVars.getVar("tx")), parseFloat(this.globalVars.getVar("ty")), parseFloat(this.globalVars.getVar("ta")))
      }
    })
  }
  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  initChart():void{
    this.visionChartConfig = {
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
      },
      responsive : true,
    };
  }

  movePoint(tx: number, ty: number, ta: number):void{
    this.visionChartData[0]["data"] = [{x: tx, y: ty, r: ta * 2}];
    this.lastVisionXVal = tx;
    this.chart.update();
  }

}
