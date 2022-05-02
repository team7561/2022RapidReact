import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { DynamicGlobalsService } from '../globals/dynamic-globals.service';

@Injectable({
  providedIn: 'root'
})
export class GraphDataService {
  // The keys used to get necessary data from the globalvars
  private graphKeys: Array<string> = ["Battery Voltage", "Shooter A Speed", "Shooter B Speed", "Injector Speed", "Intake Speed", "ShooterHood"];
  private graphData: Map<string, Array<number>> = new Map();
  private graphTimeVals: Array<number> = [];
  private static subject: Subject<void> = new Subject();

  constructor(private globalData: DynamicGlobalsService) { }

  getSubject():Subject<void>{
    return GraphDataService.subject;
  }

  beginGraphStream():void{
    for(var i=0; i<this.graphKeys.length; i++){
      this.graphData.set(this.graphKeys[i], []);
    }

    setInterval(()=>{
      for(var i=0; i<this.graphKeys.length; i++){
        var currentDataList: Array<number> = (this.graphData.get(this.graphKeys[i]) as number[]);
        currentDataList.push(parseFloat(this.globalData.getVar(this.graphKeys[i])));
        this.graphData.set(this.graphKeys[i], currentDataList)
      }
      this.graphTimeVals.push(this.graphTimeVals.length + 1);
      GraphDataService.subject.next();
    }, parseInt(this.globalData.getVar("pollingRate")));
  }

  getGraphData(key: string): Array<number>{
    return (this.graphData.get(key) as number[])
  }
  
  getGraphTimeVals(): Array<number>{
    return this.graphTimeVals
  }

  deleteSection(key: string): void{
    this.graphData.delete(key);
    this.graphData.set(key, []);
  }


}
