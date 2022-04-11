import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';

@Component({
  selector: 'app-swerve-direction',
  templateUrl: './swerve-direction.component.html',
  styleUrls: ['./swerve-direction.component.scss']
})
export class SwerveDirectionComponent implements OnInit {
  private globalSub: Subscription;
  
  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {      
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      (document.getElementById("moduleAIcon") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("A_Angle") + "deg)";
      (document.getElementById("moduleBIcon") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("B_Angle") + "deg)";
      (document.getElementById("moduleCIcon") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("C_Angle") + "deg)";
      (document.getElementById("moduleDIcon") as HTMLElement).style.transform = "rotate(" + this.globalVars.getVar("D_Angle") + "deg)"; 
    });
  }

  ngOnDestory():void{
    this.globalSub.unsubscribe();
  }

}
