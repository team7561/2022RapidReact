import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { cameraData, keyValPair, notificationObj } from 'src/model';

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
  public cameraList: cameraData[];

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
      }if(this.cameraList != JSON.parse(this.globalVar.getVar("cameraAdresses"))){{
        this.cameraList = JSON.parse(this.globalVar.getVar("cameraAdresses"))
      }}
      this.globalVarData = this.globalVar.getAllVars();
    })
  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  addDataPoint(event: Event, key: string):void{
    // Add new elem to specified list stored in global vars
    event.preventDefault();
    var currentDataList = JSON.parse(this.globalVar.getVar(key));
    currentDataList.push((document.getElementById(key + "Input") as HTMLInputElement).value);
    (document.getElementById(key + "Input") as HTMLInputElement).value = "";
    this.globalVar.addVar(key, JSON.stringify(currentDataList), false);
  }

  deleteDataPoint(event: Event, key: string): void{
    // Remove specified elem from list in global vars
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

  deleteCamData(ip: string){
  // Remove specified elem from list in global vars
  var dataList: cameraData[] = JSON.parse(this.globalVar.getVar("cameraAdresses"));
  for(var i=0; i<dataList.length; i++){
    if(dataList[i]['ip'] == ip){
      dataList.splice(i, 1);
      break;
    }
  }
  this.globalVar.addVar("cameraAdresses", JSON.stringify(dataList), true);
  }

  changeMatchTime():void{
    // Adjust how long a match lasts
    this.gameLength = parseInt((document.getElementById("gameLengthInput") as HTMLInputElement).value);
    this.globalVar.addVar("matchTime", this.gameLength.toString(), false);
  }

  addNotification():void{
    // Add new notificaton
    var newNotifName: string = (document.getElementById("newNotifNameInput") as HTMLInputElement).value;
    var newNotifTime: number = parseInt((document.getElementById("newNotifTimeInput") as HTMLInputElement).value);
    this.notifList.push({"title": newNotifName, "timeVal": newNotifTime});
    this.globalVar.addVar("notificationList", JSON.stringify(this.notifList), true);
    setTimeout(()=>{
      location.reload(); // Reload the page to ensure changes take effect 
    }, 250);
  }

  deleteNotification(event: Event){
    // Remove notification
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
    // Remove all variables stored in local storage (Nuke it)
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
