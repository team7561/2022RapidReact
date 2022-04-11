import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-connection-menu',
  templateUrl: './connection-menu.component.html',
  styleUrls: ['./connection-menu.component.scss']
})
export class ConnectionMenuComponent implements OnInit {
  public connectionURL: string = this.globalVarService.getVar("connectionURL");
  public pollingRate: number = parseInt(this.globalVarService.getVar("pollingRate"));
  private globalSub: Subscription;

  constructor(private globalVarService: DynamicGlobalsService) { }

  ngOnInit(): void {
    console.log(this.pollingRate)
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
    (document.getElementById("connectionURLInput") as HTMLInputElement).value = this.globalVarService.getVar("connectionURL");
    var commsIndictaor: HTMLElement = document.getElementById("commsIndicator") as HTMLElement; 
    commsIndictaor.classList.remove("indicator-unknown");
    commsIndictaor.classList.remove("indicator-connecting");
    commsIndictaor.classList.remove("indicator-connected");
    commsIndictaor.classList.remove("indicator-failed");
    switch(this.globalVarService.getVar("connectionStatus")){
      case "disconnected": {
        commsIndictaor.classList.add("indicator-unknown");
        break;
      } case "connecting" :{
        commsIndictaor.classList.add("indicator-connecting");
        break;
      }case "connected":{
        commsIndictaor.classList.add("indicator-connected");
        break;
      }case "failed": {
        commsIndictaor.classList.add("indicator-failed")
      }
    }
  }

  updateConnectionSettings(event: Event):void{
    this.connectionURL = (<HTMLInputElement>document.getElementById("connectionURLInput")).value
    this.pollingRate = parseInt((<HTMLInputElement>document.getElementById("pollingRateInput")).value)

    this.globalVarService.addVar("connectionURL", this.connectionURL);
    this.globalVarService.addVar("pollingRate", this.pollingRate.toString());

    setTimeout(()=>{
      location.reload();
    }, this.pollingRate)
  }


}
