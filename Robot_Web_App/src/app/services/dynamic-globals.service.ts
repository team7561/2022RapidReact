import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { keyValPair } from 'src/model';

@Injectable({
  providedIn: 'root'
})
export class DynamicGlobalsService {
  private globalVars: Array<keyValPair> = [];
  private runInit: boolean = false;
  private static varSubject: Subject<Array<keyValPair>> = new Subject();

  constructor(){
    // Read past global variables from localstorage and init default fields
    if(!this.runInit){ // Ensure the variables only gett initialized once`
      if(localStorage.getItem("globalVars")){
        this.globalVars = JSON.parse(localStorage.getItem("globalVars") as string);
      }else{
        this.addVar("connectionURL", environment.defaultURL);
        this.addVar("pollingRate", environment.defaultPollingRate.toString());
        this.addVar("matchTime", environment.matchTime.toString());
        console.log("CANNOT FIND LOCAL STORAGE VARIABLES")
      }
    }
    this.runInit = true;
    setTimeout(()=>{ // Buffer the first update to allow components to load
      this.varsChanged();
    }, 250)
  }

  getSubject(): Subject<Array<keyValPair>>{
    return DynamicGlobalsService.varSubject;
  }

  varsChanged():void{
    DynamicGlobalsService.varSubject.next(this.globalVars);
    this.saveVar();
  }

  addVar(key: string, val: string):void{
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
    this.varsChanged();
  }
  
  getVar(key: string): string{
    var res:string = ""
    for(var i:number=0; i<this.globalVars.length; i++){
      if(this.globalVars[i]["key"] == key){
        res = this.globalVars[i]["val"];
      }
    }
    return res;
  }

  saveVar(): void{
    
    // Save the global variables to localstorage
    localStorage.setItem("globalVars", JSON.stringify(this.globalVars))
  }
  
}
