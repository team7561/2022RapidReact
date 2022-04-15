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

  private tickCount: number = 20; // How many values the graph shows
  private batteryPointsAdded: number = 1; // How many values have been put on graph for battery voltages
  private shooterPointsAdded: number = 1; // How many values have been put on graph for shooter vals

  // Battery Chart
  private globalSub: Subscription;
  public batteryChartLabels: ChartConfiguration["data"]["labels"] = [];
  public batteryChartData: ChartConfiguration["data"]["datasets"] = [
    {
      "data": [],
      "label": "Battery Voltage"
    }
  ];
  
  public batterChartConfig: ChartConfiguration["options"];
  private lastBatteryVal: number;

  // Shooter Chart
  public shooterChartLabels: ChartConfiguration["data"]["labels"] = [];
  public shooterChartData: ChartConfiguration["data"]["datasets"] = [
    {
      "data": [],
      "label": "Shooter A Speed"
    },{
      "data": [],
      "label": "Shooter B Speed"
    }
  ];
  
  public shooterChartConfig: ChartConfiguration["options"];
  private lastShooterAVal: number;

  // Vision tracking Chart
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
  
  constructor(private globalService: DynamicGlobalsService) {}
  ngOnInit() {
    // Init Battery Graph data
    for(var i=0; i<this.tickCount; i++){
      this.batteryChartLabels?.push("");
      this.batteryChartData[0]["data"].push(0)
      
      // Init Shooter chart
      this.shooterChartLabels?.push("");
      this.shooterChartData[0]["data"].push(0)
      this.shooterChartData[1]["data"].push(0)
    }

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
            min: 9,
            max: 12
          }
      }
    };
    this.shooterChartConfig = {
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
            min: -1250,
            max: 1250
          }
      }
    };
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
      }
    };

    this.globalSub = this.globalService.getSubject().subscribe(()=>{
      this.addBatteryDataPoint(parseFloat(this.globalService.getVar("Battery Voltage"))); // Add new point to the graph and remove the last one
      this.addShooterDataPoint(parseFloat(this.globalService.getVar("Shooter A Speed")), parseFloat(this.globalService.getVar("Shooter B Speed")));
      this.moveVisionPoint(parseFloat(this.globalService.getVar("tx")), parseFloat(this.globalService.getVar("ty")), parseFloat(this.globalService.getVar("ta")))
      this.chart.update();
    });
  }

  addBatteryDataPoint(newVal: number):void{
    if(newVal != this.lastBatteryVal){ // Ensure the point is different from the last (not every update subscription ca)
      if(this.batteryChartData[0]["data"].length == 20){
        this.batteryChartData[0]["data"] = this.batteryChartData[0]["data"].slice(1) // Remove the last point and label from the chart
        this.batteryChartLabels = this.batteryChartLabels?.slice(1)
      }
      this.batteryChartData[0]["data"].push(newVal); // Add the new point and label
      this.batteryChartLabels?.push(this.batteryPointsAdded);
      this.batteryPointsAdded += 1;
      this.lastBatteryVal = newVal;
    }
  }
 
  addShooterDataPoint(newAVal: number, newBVal: number){
    if(newAVal != this.lastShooterAVal){
      if(this.shooterChartData[0]["data"].length == 20){
        this.shooterChartData[0]["data"] = this.shooterChartData[0]["data"].slice(1)
        this.shooterChartData[1]["data"] = this.shooterChartData[1]["data"].slice(1)
        this.shooterChartLabels = this.shooterChartLabels?.slice(1)
      }
      this.shooterChartData[0]["data"].push(newAVal);
      this.shooterChartData[1]["data"].push(newBVal);
      this.shooterChartLabels?.push(this.shooterPointsAdded);
      this.shooterPointsAdded += 1;
      this.lastShooterAVal = newAVal;
    }
  }

  moveVisionPoint(xVal: number, yVal: number, aVal: number){
    if(xVal != this.lastVisionXVal){
      this.visionChartData[0]["data"] = [{x: xVal, y: yVal, r: aVal}];
      this.lastVisionXVal = xVal;
      this.chart.update();

    }
  }

  ngOnDestroy() {
    this.globalSub.unsubscribe();
  }
}
