import { Injectable } from '@angular/core';
import { DynamicGlobalsService } from './dynamic-globals.service';
import { HttpClient, HttpErrorResponse } from "@angular/common/http"
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GetRobotDataService {

  constructor(private globalVars: DynamicGlobalsService,
              private httpClient: HttpClient) { }

  startDataStream(): void{
    this.globalVars.addVar("connectionStatus", "connecting", true);
    const dataInterval = setInterval(()=>{
      this.getRobotData().subscribe((res: Object)=>{
        if(this.globalVars.getVar("connectionStatus") != "connected"){
          this.globalVars.addVar("connectionStatus", "connected", true)
        }
        var keyArray: string[] = [];
        var valArray: string[] = [];
        for(var i:number=0; i<Object.keys(res).length; i++){
          keyArray.push(Object.keys(res)[i]);
          valArray.push((res as any)[Object.keys(res)[i]].toString())
        }
        this.globalVars.addMultipleVars(keyArray, valArray, true);
      })
    }, parseInt(this.globalVars.getVar("pollingRate")));
  }

  getRobotData(): Observable<any>{
    return this.httpClient.get(this.globalVars.getVar("connectionURL")).pipe(catchError(()=>{
      if(this.globalVars.getVar("connectionStatus") != "failed"){
        this.globalVars.addVar("connectionStatus", "failed", true);
      }
      return throwError("CANNOT CONNECT TO ROBOT")
    }));
  }

  // TODO send data to robot
  sendRobotData(key: string, val: any){
    console.log("SENDING DATA " + key)
  }
}
