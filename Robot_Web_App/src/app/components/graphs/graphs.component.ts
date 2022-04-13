import { Component, ElementRef, NgModule, OnInit, ViewChild } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { Chart, ChartConfiguration, ChartDataset } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.scss'],
})
export class GraphsComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;
  
  private globalSub: Subscription;
  public batteryChartLabels: ChartConfiguration["data"]["labels"] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
  public batteryChartData: ChartConfiguration["data"]["datasets"] = [
    {
      "data": [11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11],
      "label": "Battery Voltage"
    }
  ];
  
  private lastBatteryVal: number;
  public batterChartConfig: ChartConfiguration["options"];
  
  constructor(private globalService: DynamicGlobalsService) {}
  ngOnInit() {
    this.batterChartConfig = {
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
            min: 10,
            max: 12
          }
      }
    };

    this.globalSub = this.globalService.getSubject().subscribe(()=>{
      let data = parseFloat(this.globalService.getVar("Battery Voltage"));
      this.addDataPoint(data);
    });
  }

  addDataPoint(newVal: number):void{
    if(this.batteryChartData[0]["data"].length == 20){
      this.batteryChartData[0]["data"] = this.batteryChartData[0]["data"].slice(1)
      this.batteryChartLabels = this.batteryChartLabels?.slice(1)
    }


    console.log("point added?")
    if(newVal != this.lastBatteryVal){
      let newData = this.batteryChartData[0].data;
      let newLabel = this.batteryChartData[0].label;
      newLabel = new Date().toTimeString();
      this.batteryChartData[0]["data"].push(newVal)
      this.batteryChartLabels?.push(this.batteryChartData[0]["data"].length.toString());
      this.chart.update();
      this.lastBatteryVal = newVal
    }
  }
 

  ngOnDestroy() {
    this.globalSub.unsubscribe();
  }
}
