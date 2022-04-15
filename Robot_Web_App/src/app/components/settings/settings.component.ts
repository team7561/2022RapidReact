import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { notificationObj } from 'src/model';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  public routineList: Array<string>;
  public gameLength: number | null;
  public notifList: Array<notificationObj>;

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
    })
  }

  addAutoRoutine(event: Event):void{ 
    event.preventDefault();
    var currentAutoList = JSON.parse(this.globalVar.getVar("autoModes"));
    currentAutoList.push((document.getElementById("newRoutineInput") as HTMLInputElement).value);
    (document.getElementById("newRoutineInput") as HTMLInputElement).value = ""
    this.globalVar.addVar("autoModes", JSON.stringify(currentAutoList), false);
  }

  deleteRoutine(event: Event):void{
    var valToDelete = (event.target as HTMLElement).id
    for(var i=0; i<this.routineList.length; i++){
      if(this.routineList[i] == valToDelete){
        this.routineList.splice(i, 1);
        break;
      }
    }
    this.globalVar.addVar("autoModes", JSON.stringify(this.routineList), false);
    
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
