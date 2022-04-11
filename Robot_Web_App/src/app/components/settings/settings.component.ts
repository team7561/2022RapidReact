import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  public routineList: Array<string>;

  private globalSub: Subscription;

  constructor(private globalVar: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      if(this.routineList != JSON.parse(this.globalVar.getVar("autoModes"))){
        this.routineList = JSON.parse(this.globalVar.getVar("autoModes"));
      }
    })
  }

  addAutoRoutine(event: Event):void{
    event.preventDefault();
    var currentAutoList = JSON.parse(this.globalVar.getVar("autoModes"));
    currentAutoList.push((document.getElementById("newRoutineInput") as HTMLInputElement).value);
    (document.getElementById("newRoutineInput") as HTMLInputElement).value = ""
    this.globalVar.addVar("autoModes", JSON.stringify(currentAutoList));
  }

  deleteRoutine(event: Event):void{
    var valToDelete = (event.target as HTMLElement).id
    for(var i=0; i<this.routineList.length; i++){
      if(this.routineList[i] == valToDelete){
        this.routineList.splice(i, 1);
        break;
      }
    }
    this.globalVar.addVar("autoModes", JSON.stringify(this.routineList));
    
  }


}
