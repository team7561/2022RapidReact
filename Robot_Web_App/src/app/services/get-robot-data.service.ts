import { Injectable } from '@angular/core';
import { DynamicGlobalsService } from './dynamic-globals.service';
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http"
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GetRobotDataService {

  constructor(private globalVars: DynamicGlobalsService,
              private httpClient: HttpClient) { }

  startDataStream(): void{ // Begins the data stream to the robot
    this.globalVars.addVar("connectionStatus", "connecting", true);
    const dataInterval = setInterval(()=>{
      if(this.globalVars.getVar("doConnection") == "true"){ // Only connect to robot if user enables it 
        this.getRobotData().subscribe((res: Object)=>{
          if(this.globalVars.getVar("connectionStatus") != "connected"){
            this.globalVars.addVar("connectionStatus", "connected", true)
          }
          var keyArray: string[] = []; // List of keys returned by the robot
          var valArray: string[] = []; // List of values returned
          for(var i:number=0; i<Object.keys(res).length; i++){
            keyArray.push(Object.keys(res)[i]);
            valArray.push((res as any)[Object.keys(res)[i]].toString());
          }
          this.globalVars.addMultipleVars(keyArray, valArray, true);
        })
      }
    }, parseInt(this.globalVars.getVar("pollingRate")));
  }

  getRobotData(): Observable<any>{ // Connect to and recive data from the robot
    return this.httpClient.get(this.globalVars.getVar("connectionURL")).pipe(catchError(()=>{
      if(this.globalVars.getVar("connectionStatus") != "failed"){
        this.globalVars.addVar("connectionStatus", "failed", true);
      }
      return throwError("CANNOT CONNECT TO ROBOT")
    })); 
  }

  sendRobotData(key: string, val: string){
    if(this.globalVars.getVar("doConnection") == "true"){ // Only Send data to the robot if user enables it 
      var url: string = this.globalVars.getVar("sendURL") + "/?key=" + key.replace(/ /g, "%20") + "&val=" + val.replace(/ /g, "%20")
      this.httpClient.get(url).subscribe((res)=>{});
    }
  }
}
