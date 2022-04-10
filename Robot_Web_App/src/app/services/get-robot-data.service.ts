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
    this.globalVars.addVar("connectionStatus", "connecting");
    const dataInterval = setInterval(()=>{
      this.getRobotData().subscribe((res)=>{
        if(this.globalVars.getVar("connectionStatus") != "connected"){
          this.globalVars.addVar("connectionStatus", "connected")
        }
        this.globalVars.addVar("robotResponse", JSON.stringify(res));
      })
    }, parseInt(this.globalVars.getVar("pollingRate")));
  }

  getRobotData(): Observable<any>{
    return this.httpClient.get(this.globalVars.getVar("connectionURL")).pipe(catchError(()=>{
      if(this.globalVars.getVar("connectionStatus") != "failed"){
        this.globalVars.addVar("connectionStatus", "failed");
      }
      return throwError("CANNOT CONNECT TO ROBOT")
    }));
  }
}
