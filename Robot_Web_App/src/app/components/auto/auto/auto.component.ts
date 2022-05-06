import { Component, OnInit } from '@angular/core';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/robot-data/get-robot-data.service';
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-auto',
  templateUrl: './auto.component.html',
  styleUrls: ['./auto.component.scss']
})
export class AutoComponent implements OnInit {
  public autoOptions: string[];
  public selectedAuto : string;

  
  private globalSub: Subscription;
  constructor(private globalVars: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      if(this.autoOptions != JSON.parse(this.globalVars.getVar("autoModes"))){
        // Gather auto options
        this.autoOptions = JSON.parse(this.globalVars.getVar("autoModes"));
      }
    });

    this.selectedAuto = this.globalVars.getVar("Auto")
  }
  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  updateAuto():void{
    this.globalVars.addVar("Auto", this.selectedAuto, false);
    this.robotData.sendRobotData("Auto", this.selectedAuto);
  }



}
