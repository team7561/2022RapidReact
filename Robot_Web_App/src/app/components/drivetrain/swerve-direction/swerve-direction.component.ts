import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';

@Component({
  selector: 'app-swerve-direction',
  templateUrl: './swerve-direction.component.html',
  styleUrls: ['./swerve-direction.component.scss']
})
export class SwerveDirectionComponent implements OnInit {
  private globalSub: Subscription;
  
  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void { // Shows the angle if each swerve module
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      (document.getElementById("moduleAIcon") as HTMLElement).style.transform = "rotate(" + this.calculateAbsoluteAngle("A") + "deg)";
      (document.getElementById("moduleBIcon") as HTMLElement).style.transform = "rotate(" + this.calculateAbsoluteAngle("B") + "deg)";
      (document.getElementById("moduleCIcon") as HTMLElement).style.transform = "rotate(" + (this.calculateAbsoluteAngle("C") - 90) + "deg)";
      (document.getElementById("moduleDIcon") as HTMLElement).style.transform = "rotate(" + this.calculateAbsoluteAngle("D") + "deg)"; 
    });
  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  calculateAbsoluteAngle(motorChar: string): number{
    var angle: number = parseInt(this.globalVars.getVar(motorChar + "_Angle"));
    var offsetAngle: number = parseFloat(this.globalVars.getVar(motorChar + "_Offset_Angle")) * 360 
    
    var absoluteAngle: number = Math.abs(angle - offsetAngle) % 360;

    return absoluteAngle; 
  }

}
