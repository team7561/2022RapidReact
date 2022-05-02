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

  private robotX: number;
  private robotY: number;
  private robotHeading: number;
  private globalSub: Subscription;
  constructor(private globalVars: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      if(this.autoOptions != JSON.parse(this.globalVars.getVar("autoModes"))){
        this.autoOptions = JSON.parse(this.globalVars.getVar("autoModes"));
      }
      if(this.robotX != parseFloat(this.globalVars.getVar("robotX"))){
        this.robotX = parseFloat(this.globalVars.getVar("robotX"));
        this.robotY = parseFloat(this.globalVars.getVar("robotY"));
        this.robotHeading = parseFloat(this.globalVars.getVar("Gyro Angle"));
        this.moveRobot();
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

  moveRobot():void{ // Move the robot iconm to a given point and adjust the heading
    var robotElem = document.getElementById("robot") as HTMLElement;
    if(robotElem){
      robotElem.style.top = (this.robotY * 440).toString() + "px"
      robotElem.style.left = (this.robotX * window.innerWidth * 0.71).toString() + "px"
      robotElem.style.transform = "rotate(" + this.robotHeading.toString() + "deg)"
    }
  }

}
