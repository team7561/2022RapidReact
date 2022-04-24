import { Component, OnInit } from '@angular/core';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/get-robot-data.service';
import { RecordLoadDataService } from 'src/app/services/record-load-data.service';
import { keyValPair } from 'src/model';

@Component({
  selector: 'app-recording-menu',
  templateUrl: './recording-menu.component.html',
  styleUrls: ['./recording-menu.component.scss']
})
export class RecordingMenuComponent implements OnInit {
  public recordingTimeString: string = "00:00";
  public playBackTimeString: string = "00:00";
  public totalPlayBackTimeString: string = "00:00";
  public playBackTimeVal: number | null= 0;
  public totalplayBackTimeVal: number = 0;

  constructor(
      private recorder: RecordLoadDataService, 
      private globalVars: DynamicGlobalsService,
      private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.recordingTimeString = this.formatTime(this.recorder.getTimeRecorded());
    setInterval(()=>{
      this.recordingTimeString = this.formatTime(this.recorder.getTimeRecorded());
    }, parseFloat(this.globalVars.getVar("pollingRate")));
  }
  
  startRecording():void{
    this.recorder.startRecording();
  }

  saveRecording():void{
    var dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(this.recorder.getTotalData());
    var downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href", dataStr);
    downloadAnchorNode.setAttribute("download",  "robotData.json");
    document.body.appendChild(downloadAnchorNode); // required for firefox
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
  }

  loadRecording(event: Event): void{
    const dataReader = this.robotData;
    const target = event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];
    var playBackTime: number;
    let reader = new FileReader();
    var hasReadText = false;

    reader.onload = function(){
      var preData: Array<string> = JSON.parse(reader.result as string)
      var newData: Array<Array<keyValPair>> = [];
      for(var i=0; i<preData.length; i++){
        newData.push(JSON.parse(preData[i]));
      }

      // Calculate length ofplayback file
      for(var i=0; i<newData[0].length; i++){
        if(newData[0][i]["key"] == "pollingRate"){
          playBackTime = (parseInt(newData[0][i]["val"]) * newData.length) / 1000;
          break
        }
      }
      
      dataReader.readFromLocalData(newData);
      hasReadText = true
    }
    reader.readAsText(file);

    setTimeout(()=>{
      this.setPlayBackStrings(playBackTime);
    }, 500)
  }

  setPlayBackStrings(totalTime: number):void{
    this.totalPlayBackTimeString = this.formatTime(totalTime);
    this.totalplayBackTimeVal = totalTime;

    setInterval(()=>{
      this.playBackTimeVal = this.robotData.getPlaybackTime();
      this.playBackTimeString = this.formatTime(this.robotData.getPlaybackTime());

      if(this.playBackTimeVal == this.totalplayBackTimeVal){
        location.reload();
      }
    }, 500)
  }

  formatTime(seconds: number): string{
    var timeString = "";
    var minutes = Math.floor(seconds / 60);
    if(minutes != 0){
      if(minutes >= 10){
        timeString += minutes.toString();
      }else{
        timeString += "0" + minutes.toString();
      }
    }else{
      timeString += "00";
    }
    timeString += ":";
    seconds = seconds - (60 * Math.floor(seconds / 60));
    if(seconds < 10){
      timeString += "0" + seconds.toString();
    }else{
      timeString += seconds.toString();
    }

    return timeString;
  }

  pausePlayBack():void{
    this.robotData.pausePlayBack();
  }

  resumePlayBack():void{
    this.robotData.resumePlayBack();
  }


}
