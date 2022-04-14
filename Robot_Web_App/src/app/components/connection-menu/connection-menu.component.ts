import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-connection-menu',
  templateUrl: './connection-menu.component.html',
  styleUrls: ['./connection-menu.component.scss']
})
export class ConnectionMenuComponent implements OnInit { // Displays basic connection data and basic robot stats
  public connectionURL: string = this.globalVarService.getVar("connectionURL");
  public pollingRate: number = parseInt(this.globalVarService.getVar("pollingRate"));
  private globalSub: Subscription;

  constructor(private globalVarService: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVarService.getSubject().subscribe((res)=>{
      this.reloadStats();
    });
  }

  ngOnDestroy(): void{
    if(this.globalSub){ // Unsubscribe from global vars if elem becomes unloaded
      this.globalSub.unsubscribe();
    }
  }

  reloadStats():void{
    var commsIndictaor: HTMLElement = document.getElementById("commsIndicator") as HTMLElement; 
    commsIndictaor.classList.remove("indicator-unknown");
    commsIndictaor.classList.remove("indicator-warning");
    commsIndictaor.classList.remove("indicator-good");
    commsIndictaor.classList.remove("indicator-error");
    switch(this.globalVarService.getVar("connectionStatus")){
      case "disconnected": {
        commsIndictaor.classList.add("indicator-unknown");
        break;
      } case "connecting" :{
        commsIndictaor.classList.add("indicator-warning");
        break;
      }case "connected":{
        commsIndictaor.classList.add("indicator-good");
        break;
      }case "failed": {
        commsIndictaor.classList.add("indicator-error")
      }
    }

    var batteryIndicator: HTMLElement = document.getElementById("batteryIndicator") as HTMLElement;
    batteryIndicator.classList.remove("indicator-unknown");
    batteryIndicator.classList.remove("indicator-warning");
    batteryIndicator.classList.remove("indicator-good");
    batteryIndicator.classList.remove("indicator-error");
    var batteryVoltage: number = parseFloat(this.globalVarService.getVar("Battery Voltage"));
    if(batteryVoltage < 11){
      batteryIndicator.classList.add("indicator-error")
    }else if(batteryVoltage < 11.5){
      batteryIndicator.classList.add("indicator-warning")
    }else{
      batteryIndicator.classList.add("indicator-good")
    }
  }

  updateConnectionSettings(event: Event):void{
    this.connectionURL = (<HTMLInputElement>document.getElementById("connectionURLInput")).value
    this.pollingRate = parseInt((<HTMLInputElement>document.getElementById("pollingRateInput")).value)

    this.globalVarService.addVar("connectionURL", this.connectionURL, false);
    this.globalVarService.addVar("pollingRate", this.pollingRate.toString(), false);

    setTimeout(()=>{
      location.reload();
    }, this.pollingRate)
  }


}
