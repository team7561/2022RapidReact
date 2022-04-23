import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { keyValPair, notificationObj } from 'src/model';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  public routineList: Array<string>;
  public gameLength: number | null;
  public notifList: Array<notificationObj>;
  public driveModeList: Array<string>;
  public intakeModeList: Array<string>;
  public injectorModeList: Array<string>;

  public globalVarData: Array<keyValPair> = [];

  private globalSub: Subscription;

  constructor(private globalVar: DynamicGlobalsService) { }

  ngOnInit(): void {
    // Get default settings values
    this.gameLength = parseInt(this.globalVar.getVar("matchTime"));
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      if(this.notifList != JSON.parse(this.globalVar.getVar("notificationList"))){ // Only update list when changed
        this.notifList = JSON.parse(this.globalVar.getVar("notificationList"));
      }
      if(this.routineList != JSON.parse(this.globalVar.getVar("autoModes"))){
        this.routineList = JSON.parse(this.globalVar.getVar("autoModes")); // Auto update routine list when changed
      }
      if(this.driveModeList != JSON.parse(this.globalVar.getVar("driveModes"))){
        this.driveModeList = JSON.parse(this.globalVar.getVar("driveModes")); // Auto update routine list when changed
      }
      if(this.intakeModeList != JSON.parse(this.globalVar.getVar("intakeModes"))){
        this.intakeModeList = JSON.parse(this.globalVar.getVar("intakeModes")); // Auto update routine list when changed
      }
      if(this.injectorModeList != JSON.parse(this.globalVar.getVar("injectorModes"))){
        this.injectorModeList = JSON.parse(this.globalVar.getVar("injectorModes")); // Auto update routine list when changed
      }
      this.globalVarData = this.globalVar.getAllVars();
    })
  }

  addDataPoint(event: Event, key: string):void{
    event.preventDefault();
    var currentDataList = JSON.parse(this.globalVar.getVar(key));
    currentDataList.push((document.getElementById(key + "Input") as HTMLInputElement).value);
    (document.getElementById(key + "Input") as HTMLInputElement).value = "";
    this.globalVar.addVar(key, JSON.stringify(currentDataList), false);
  }

  deleteDataPoint(event: Event, key: string): void{
    var valToDelete = (event.target as HTMLElement).id;
    var dataList: Array<string> = JSON.parse(this.globalVar.getVar(key));

    for(var i=0; i<dataList.length; i++){
      if(dataList[i] == valToDelete){
        dataList.splice(i, 1);
        break;
      }
    }
    this.globalVar.addVar(key, JSON.stringify(dataList), true);

  }

  changeMatchTime():void{
    this.gameLength = parseInt((document.getElementById("gameLengthInput") as HTMLInputElement).value);
    this.globalVar.addVar("matchTime", this.gameLength.toString(), false);
  }

  addNotification():void{
    var newNotifName: string = (document.getElementById("newNotifNameInput") as HTMLInputElement).value;
    var newNotifTime: number = parseInt((document.getElementById("newNotifTimeInput") as HTMLInputElement).value);
    this.notifList.push({"title": newNotifName, "timeVal": newNotifTime});
    this.globalVar.addVar("notificationList", JSON.stringify(this.notifList), true);
    setTimeout(()=>{
      location.reload(); // Reload the page to ensure changes take effect 
    }, 250);
  }

  deleteNotification(event: Event){
    var valToDelete = (event.target as HTMLElement).id
    console.log(valToDelete)
    for(var i=0; i<this.notifList.length; i++){
      if(this.notifList[i]["title"] == valToDelete){
        this.notifList.splice(i, 1);
        break;
      }
    }
    this.globalVar.addVar("notificationList", JSON.stringify(this.notifList), false);
    setTimeout(()=>{
      location.reload();
    }, 250)
  }

  wipeLocalStorage():void{
    if(confirm("Are you sure you want to delete ALL locally stored variables?")){ // Only wipe data if user confirms
      this.globalVar.setDoLocalBackup(false); // Stop the global vars from backing up between clearing local storage and reloading
      setTimeout(()=>{
        localStorage.clear();
        console.log("Local storage cleared");
        location.reload();
      }, 100);

    }
  }


}
