import { Injectable } from '@angular/core';
import { of, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { keyValPair } from 'src/model';

@Injectable({
  providedIn: 'root'
})
export class DynamicGlobalsService { // Keeps track of variables across components and updates each when new robot data packets arrive
  private globalVars: Array<keyValPair> = [];
  private runInit: boolean = false;
  private static varSubject: Subject<Array<keyValPair>> = new Subject();
  private doLocalBackup: boolean = true;

  constructor(){
    // Read past global variables from localstorage and init default fields
    if(!this.runInit){ // Ensure the variables only gett initialized once
      if(localStorage.getItem("globalVars")){
        try{
          this.globalVars = JSON.parse(localStorage.getItem("globalVars") as string);
        }catch(SyntaxError){
          localStorage.clear();
          this.initGlobalVals();
        }
        if(this.getVar("versionNumber")[2] != environment.versionNumber[2]){
          this.addVar("dataStatus", "unMatchedVersion", true); // Alert user if stored data was created with an older version of the web app
        }else{
          this.addVar("dataStatus", "good", true);
        }
      }else{
        // Load the default values
        this.initGlobalVals();
        console.log("CANNOT FIND LOCAL STORAGE VARIABLES");
        this.addVar("dataStatus", "new", true); // Alert user that the was no stored data in localstorage
      }
      this.addVar("doConnection", "true", false); // Assume user always wants to connect
      this.addVar("doRecording", "false", false);
    }
    this.runInit = true;
    setTimeout(()=>{ // Buffer the first update to allow components to load
      this.saveVar();
      DynamicGlobalsService.varSubject.next(this.globalVars);
    }, 250)
  }

  initGlobalVals():void{
    this.addVar("versionNumber", environment.versionNumber, false);
    this.addVar("connectionURL", environment.defaultURL, false);
    this.addVar("sendURL", environment.sendURL, false)
    this.addVar("pollingRate", environment.defaultPollingRate.toString(), false);
    this.addVar("matchTime", environment.matchTime.toString(), false);
    this.addVar("autoModes", JSON.stringify(environment.autoList), false);
    this.addVar("notificationList", JSON.stringify(environment.notificationList), false);
    this.addVar("driveModes", JSON.stringify(environment.driveModes), false);
    this.addVar("intakeModes", JSON.stringify(environment.intakeModes), false);
    this.addVar("injectorModes", JSON.stringify(environment.injectorModes), false);
    this.addVar("boardList", JSON.stringify([]), false);
    this.addVar("cameraAdresses", JSON.stringify([{"ip": "http://10.75.61.12:1182/stream.mjpg?1651897315379", "name": "Front Facing"}]), false);
  }

  getSubject(): Subject<Array<keyValPair>>{ // Gets the subject so components can subscribe to it 
    return DynamicGlobalsService.varSubject;
  }


  addVar(key: string, val: string, isRobotData: boolean):void{ // Add one val to globals
    // Ensure the var is not already in the dict
    var foundVal = false
    for(var i:number=0; i<this.globalVars.length; i++){
      if(this.globalVars[i]["key"] == key){
        this.globalVars[i]["val"] = val
        foundVal = true;
        break;
      }
    }
    if(!foundVal){
      this.globalVars.push({"key": key, "val": val});
    }
    if(isRobotData){ // Only update all subscribers if the data is from the robot
      DynamicGlobalsService.varSubject.next(this.globalVars);
    }
    this.saveVar();
  }

  addMultipleVars(keyArray: string[], valArray: string[], isRobotData: boolean){ // Add multiple vals altogether to globals
    for(var i=0; i<keyArray.length; i++){
      // If the var is already in the dict, update it, otherwise make a new key 
      var key = keyArray[i];
      var val = valArray[i];
      var foundVal = false
      for(var k:number=0; k<this.globalVars.length; k++){
        if(this.globalVars[k]["key"] == key){
          this.globalVars[k]["val"] = val
          foundVal = true;
          break;
        }
      }
      if(!foundVal){
        this.globalVars.push({"key": key, "val": val});
      }
    }
    if(isRobotData){
      DynamicGlobalsService.varSubject.next(this.globalVars);
    }
  }
  
  getVar(key: string): string{ // Get a specified var from the globals
    var res:string = ""
    for(var i:number=0; i<this.globalVars.length; i++){
      if(this.globalVars[i]["key"] == key){
        res = this.globalVars[i]["val"];
      }
    }
    return res;
  }

  setDoLocalBackup(val: boolean){
    this.doLocalBackup = val;
  }

  getAllVars(): Array<keyValPair>{
    return this.globalVars;
  }

  saveVar(): void{
    // Save the global variables to localstorage
    if(this.doLocalBackup){
      localStorage.setItem("globalVars", JSON.stringify(this.globalVars))
    }
  }
  
}
