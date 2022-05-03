import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { ChartConfiguration } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Subscription } from 'rxjs';
import { GraphDataService } from 'src/app/services/graph-data/graph-data.service';

@Component({
  selector: 'app-line-graph',
  templateUrl: './line-graph.component.html',
  styleUrls: ['./line-graph.component.scss']
})
export class LineGraphComponent implements OnInit {
  // Is standard graph refers to wether or not the vals
  // are stored in 'graph service', or whether they need to
  // be gotten by this module
  @Input() isStandardGraph: boolean; 
  @Input() trackLabels: Array<string>;
  @Input() trackKeys: Array<string>; // The key of the var tracked by the graph
  @Input() minVal: number; // Adjusted the y axis scale
  @Input() maxVal: number;
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  private globalSub: Subscription;

  private lastUpdateDate: Date = new Date(); // When the graph was last updated
  private tickCount: number = 20; // How many values the graph shows
  private totalTicksAdded: number = 1; // How many values have been put on graph 

  public chartLabels: ChartConfiguration["data"]["labels"] = [];
  public chartData: ChartConfiguration["data"]["datasets"] = [];
  
  public chartConfig: ChartConfiguration["options"];

  constructor(private globalVar: DynamicGlobalsService, private graphData: GraphDataService) { }

  ngOnInit(): void {
    this.initChart();
    this.configChart();
    if(this.isStandardGraph){
      this.setChart();
    }
    
    this.graphData.getSubject().subscribe(()=>{
      if(this.isStandardGraph){
        if(new Date().getTime() - this.lastUpdateDate.getTime() > parseInt(this.globalVar.getVar("pollingRate")) - 150){
          // If its been within 150ms of the polling rate before updating
          this.setChart();
        }
      }
      else{
        if(new Date().getTime() - this.lastUpdateDate.getTime() > parseInt(this.globalVar.getVar("pollingRate")) - 150){
          for(var i=0; i<this.trackKeys.length; i++){
            this.addPoint(parseFloat(this.globalVar.getVar(this.trackKeys[i])), i);
          }
          this.configChart(); // Readjsusts the min and max vals in case they have changed 
          this.chartLabels = this.chartLabels?.slice(1);
          this.chartLabels?.push(this.totalTicksAdded);
          this.totalTicksAdded += 1;
        }
      }
      this.chart.update();
    })

  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }


  configChart():void{
    // Sets up basic config for graph as well 
    // as defining the scale for the y axis
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

  initChart():void{
    // Add basic filler data to the graph
    // so it starts on the right
    var sampleLabelList = [];
    for(var i=0; i<this.trackLabels.length; i++){
      let sampleDataList = [];
      sampleLabelList = [];
      for(var j:number=0; j<this.tickCount; j++){
        sampleDataList.push(0);
        sampleLabelList.push("");
      }
      this.chartData.push({
        "label": this.trackLabels[i],
        "data": sampleDataList
      });
    }
    this.chartLabels = sampleLabelList
  }


  setChart():void{
    // Set chart data to that stored in the 'graph data' service
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
    this.lastUpdateDate = new Date();
  }

  addPoint(val: number, index: number):void{
    // Add a new point the graph
    if(this.chartData[index]["data"].length == 20){
      this.chartData[index]["data"] = this.chartData[index]["data"].slice(1); // Remove the last point and label from the chart
    } 
    this.chartData[index]["data"].push(val); // Add the new point and label
    
    this.lastUpdateDate = new Date();
  }

  wipeData():void{
    // Remove any data stored on the graph
    for(var i=0; i<this.trackKeys.length; i++){
      this.graphData.deleteSection(this.trackKeys[i]);
    }
  }

}
