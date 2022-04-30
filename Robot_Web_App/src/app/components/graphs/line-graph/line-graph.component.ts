import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Subscription } from 'rxjs';
import { GraphDataService } from 'src/app/services/graph-data.service';

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

  constructor(private globalVar: DynamicGlobalsService, private graphData: GraphDataService) { }

  ngOnInit(): void {
    this.initChart();
    this.setChart();

    this.graphData.getSubject().subscribe(()=>{
      this.setChart();
      this.chart.update();
    })

  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  initChart():void{
    for(var i=0; i<this.trackLabels.length; i++){
      this.chartData.push({
        "label": this.trackLabels[i],
        "data": []
      });
      this.chartData[i]["data"] = [];
      
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


  setChart():void{
    // Get Blank Data
    for(var i=0; i<this.trackLabels.length; i++){
      var currentGraphData = this.graphData.getGraphData(this.trackKeys[i]);
      if(currentGraphData.length > this.tickCount){
        currentGraphData = currentGraphData.slice(-(this.tickCount));
      }else{
        for(var k=0; k<this.tickCount - currentGraphData.length; k++){
          currentGraphData.unshift(0)
        }
      }
      this.chartData[i]["data"] = currentGraphData;
    }
    var currentGraphTimes = this.graphData.getGraphTimeVals();
    if(currentGraphTimes.length > this.tickCount){
      currentGraphTimes = currentGraphTimes.slice(-(this.tickCount));
    }else{
      for(var k=0; k<this.tickCount - currentGraphTimes.length; k++){
        currentGraphTimes.unshift(0);
      }
    }
    this.chartLabels = currentGraphTimes;
  }

  wipeData():void{
    for(var i=0; i<this.trackKeys.length; i++){
      this.graphData.deleteSection(this.trackKeys[i]);
    }
  }

}
