import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-line-graph',
  templateUrl: './line-graph.component.html',
  styleUrls: ['./line-graph.component.scss']
})
export class LineGraphComponent implements OnInit {
  @Input() trackLabels: Array<string>;
  @Input() trackKeys: Array<string>; // The key of the var tracked by the graph
  @Input() minVal: number;
  @Input() maxVal: number;
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  private globalSub: Subscription;

  private lastUpdateDate: Date = new Date();
  private tickCount: number = 20; // How many values the graph shows
  private totalTicksAdded: number = 1; // How many values have been put on graph for battery voltages

  public chartLabels: ChartConfiguration["data"]["labels"] = [];
  public chartData: ChartConfiguration["data"]["datasets"] = [];
  
  public chartConfig: ChartConfiguration["options"];

  constructor(private globalVar: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.initChart();
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      if(new Date().getTime() - this.lastUpdateDate.getTime() > parseInt(this.globalVar.getVar("pollingRate")) - 150){
        for(var i=0; i<this.trackKeys.length; i++){
          this.addPoint(parseFloat(this.globalVar.getVar(this.trackKeys[i])), i);
        }
        this.chartLabels = this.chartLabels?.slice(1);
        this.chartLabels?.push(this.totalTicksAdded);
        this.totalTicksAdded += 1;
      }
    })
  }

  initChart():void{
    // Get Blank Data
    for(var i=0; i<this.trackLabels.length; i++){
      this.chartData.push({
        "label": this.trackLabels[i],
        "data": []
      });
      for(var k=0; k<this.tickCount; k++){
        this.chartData[i]["data"].push(0);
      }
    }
    for(var k=0; k<this.tickCount; k++){
      this.chartLabels?.push("")
    }

    this.chartConfig = {
      animation: {
        duration: 0
      },
      elements: {
        line: {
          tension: 0
        }
      },
      scales: {
        x: {},
        'y-axis-0':
          {
            position: 'left',
            min: this.minVal,
            max: this.maxVal
          }
      }
    };
  }

  addPoint(val: number, index: number):void{
    if(this.chartData[index]["data"].length == 20){
      this.chartData[index]["data"] = this.chartData[index]["data"].slice(1); // Remove the last point and label from the chart
    } 
    this.chartData[index]["data"].push(val); // Add the new point and label
    
    this.lastUpdateDate = new Date();
  }

}
