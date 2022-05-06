import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';

@Component({
  selector: 'app-robot-pos',
  templateUrl: './robot-pos.component.html',
  styleUrls: ['./robot-pos.component.scss']
})
export class RobotPosComponent implements OnInit {
  private robotX: number;
  private robotY: number;
  private robotHeading: number;

  private globalSub: Subscription;
  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      if(this.robotX != parseFloat(this.globalVars.getVar("robotX"))){
        // Dather data about robot position and heading
        this.robotX = parseFloat(this.globalVars.getVar("robotX"));
        this.robotY = parseFloat(this.globalVars.getVar("robotY"));
        this.robotHeading = parseFloat(this.globalVars.getVar("Gyro Angle"));
        this.moveRobot();
      }
    });
  }

  moveRobot():void{ // Move the robot iconm to a given point and adjust the heading
    var robotElem = document.getElementById("robot") as HTMLElement;
    if(robotElem){
      robotElem.style.top = "calc(" + (this.robotY * 100).toString() + "%" + " - 60px)"
      robotElem.style.left = "calc(" + (this.robotX * 100).toString() + "%" + " - 60px)"
      robotElem.style.transform = "rotate(" + this.robotHeading.toString() + "deg)"
    }
  }
}
