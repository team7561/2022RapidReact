import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';

@Component({
  selector: 'app-connection-menu',
  templateUrl: './connection-menu.component.html',
  styleUrls: ['./connection-menu.component.scss']
})
export class ConnectionMenuComponent implements OnInit { // Displays basic connection data and basic robot stats
  public connectionURL: string = this.globalVarService.getVar("connectionURL");
  public sendURL: string = this.globalVarService.getVar("sendURL");
  public pollingRate: number = parseInt(this.globalVarService.getVar("pollingRate"));
  public doReadOnlyPollingRate: string = "false";
  public isChecked: boolean = true;

  private globalSub: Subscription;

  constructor(private globalVarService: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.reloadStats();
    this.globalSub = this.globalVarService.getSubject().subscribe((res)=>{
      this.reloadStats();
      if(this.globalVarService.getVar("doRecording") == "true"){
        this.doReadOnlyPollingRate = "true";
      }else{
        this.doReadOnlyPollingRate = "false";
      }
    });
  }

  ngOnDestroy(): void{
    if(this.globalSub){ // Unsubscribe from global vars if elem becomes unloaded
      this.globalSub.unsubscribe();
    }
  }

  reloadStats():void{
    // Set the comms indicator to the correct status
    var commsIndictaor: HTMLElement = document.getElementById("commsIndicator") as HTMLElement; 
    commsIndictaor.classList.remove("indicator-unknown");
    commsIndictaor.classList.remove("indicator-warning");
    commsIndictaor.classList.remove("indicator-good");
    commsIndictaor.classList.remove("indicator-error");
    if(this.globalVarService.getVar("doConnection") == "true"){
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
    }else{
      commsIndictaor.classList.add("indicator-unknown")
    }

    // Set the battery indicator to the correct status
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
    event.preventDefault();
    this.connectionURL = (<HTMLInputElement>document.getElementById("connectionURLInput")).value
    this.sendURL = (<HTMLInputElement>document.getElementById("sendURLInput")).value
    this.pollingRate = parseInt((<HTMLInputElement>document.getElementById("pollingRateInput")).value)

    console.log(this.connectionURL);
    console.log(this.sendURL);

    this.globalVarService.addVar("connectionURL", this.connectionURL, false);
    this.globalVarService.addVar("sendURL", this.sendURL, false);
    this.globalVarService.addVar("pollingRate", this.pollingRate.toString(), false);

    setTimeout(()=>{
      location.reload();
    }, 250)
  }

  updateDoConnection(event: MatCheckboxChange): void{
    if(event.checked){
      this.isChecked = true;
      this.globalVarService.addVar("doConnection", "true", true)
    }else{
      this.isChecked = false;
      this.globalVarService.addVar("doConnection", "false", true)
    }
  }


}
