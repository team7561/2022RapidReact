import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/robot-data/get-robot-data.service';

@Component({
  selector: 'app-offsets',
  templateUrl: './offsets.component.html',
  styleUrls: ['./offsets.component.scss']
})
export class OffsetsComponent implements OnInit {
  public offsetValA: number;
  public offsetValB: number;
  public offsetValC: number;
  public offsetValD: number;

  public offsetSetpointA: number | null;
  public offsetSetpointB: number | null;
  public offsetSetpointC: number | null;
  public offsetSetpointD: number | null;

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.setOffsets();
    this.initOffsets();
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      this.setOffsets();

      if(document.getElementById("iconA")){ // Ensure the element is rendered before transforming them all
        (document.getElementById("iconA") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("A_Angle") + "deg)";
        (document.getElementById("iconB") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("B_Angle") + "deg)";
        (document.getElementById("iconC") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("C_Angle") + "deg)";
        (document.getElementById("iconD") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("D_Angle") + "deg)";
      }
    });
  }

  ngOnDestory():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  initOffsets():void{
    this.offsetSetpointA = this.offsetValA
    this.offsetSetpointB = this.offsetValB
    this.offsetSetpointC = this.offsetValC
    this.offsetSetpointD = this.offsetValD
  }

  setOffsets(): void{
    this.offsetValA = parseFloat(parseFloat(this.globalVars.getVar("A_Offset_Angle")).toFixed(2));
    this.offsetValB = parseFloat(parseFloat(this.globalVars.getVar("B_Offset_Angle")).toFixed(2));
    this.offsetValC = parseFloat(parseFloat(this.globalVars.getVar("C_Offset_Angle")).toFixed(2));
    this.offsetValD = parseFloat(parseFloat(this.globalVars.getVar("D_Offset_Angle")).toFixed(2));
  }

  updateOffsets():void{
    this.robotData.sendRobotData("A_Offset_Angle", this.offsetSetpointA?.toString() as string); // Send the new offsets to the robot
    this.robotData.sendRobotData("B_Offset_Angle", this.offsetSetpointB?.toString() as string);
    this.robotData.sendRobotData("C_Offset_Angle", this.offsetSetpointC?.toString() as string);
    this.robotData.sendRobotData("D_Offset_Angle", this.offsetSetpointD?.toString() as string);
  }



  

}
