import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import {FormControl} from '@angular/forms';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/get-robot-data.service';
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
        this.autoOptions = JSON.parse(this.globalVars.getVar("autoModes"));
      }
    });

    this.selectedAuto = this.globalVars.getVar("Auto")
  }

  updateAuto():void{
    this.globalVars.addVar("Auto", this.selectedAuto);
    this.robotData.sendRobotData("Auto", this.selectedAuto);
  }

}
