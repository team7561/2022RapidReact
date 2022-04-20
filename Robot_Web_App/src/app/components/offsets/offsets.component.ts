import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/get-robot-data.service';

@Component({
  selector: 'app-offsets',
  templateUrl: './offsets.component.html',
  styleUrls: ['./offsets.component.scss']
})
export class OffsetsComponent implements OnInit {
  public offsetValA: number | null;
  public offsetValB: number | null;
  public offsetValC: number | null;
  public offsetValD: number | null;

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.offsetValA = parseFloat(parseFloat(this.globalVars.getVar("A_Offset_Angle")).toFixed(2));
    this.offsetValB = parseFloat(parseFloat(this.globalVars.getVar("B_Offset_Angle")).toFixed(2));
    this.offsetValC = parseFloat(parseFloat(this.globalVars.getVar("C_Offset_Angle")).toFixed(2));
    this.offsetValD = parseFloat(parseFloat(this.globalVars.getVar("D_Offset_Angle")).toFixed(2));
    
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
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

  updateOffsets():void{
    this.robotData.sendRobotData("A_Offset_Angle", this.offsetValA?.toString() as string); // Send the new offsets to the robot
    this.robotData.sendRobotData("B_Offset_Angle", this.offsetValB?.toString() as string);
    this.robotData.sendRobotData("C_Offset_Angle", this.offsetValC?.toString() as string);
    this.robotData.sendRobotData("D_Offset_Angle", this.offsetValD?.toString() as string);
  }

  

}
