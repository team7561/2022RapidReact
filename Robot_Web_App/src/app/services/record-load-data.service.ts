import { Injectable } from '@angular/core';
import { keyValPair } from 'src/model';
import { DynamicGlobalsService } from './dynamic-globals.service';

@Injectable({
  providedIn: 'root'
})
export class RecordLoadDataService {
  public recordingTimeVal: number = 0;
  
  private recordingStartTime: Date
  private recordedData: Array<string> = [];
  private writeInterval: any;
  
  constructor(private globalVars: DynamicGlobalsService) { }

  startRecording():void{
    this.recordingStartTime = new Date();
    this.recordedData = [];
    this.writeInterval = setInterval(()=>{
      this.recordedData.push(JSON.stringify(this.globalVars.getAllVars()));
      // Calculate how long the recording has been happening
      var secondsRecorded = (new Date().getTime() - this.recordingStartTime.getTime()) / 1000;
      this.recordingTimeVal = Math.floor(secondsRecorded);
    }, parseInt(this.globalVars.getVar("pollingRate")));
  }

  getTimeRecorded(): number{
    return this.recordingTimeVal;
  }

  getTotalData(): string{
    clearInterval(this.writeInterval);
    return JSON.stringify(this.recordedData);
  }
}
