import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/robot-data/get-robot-data.service';

@Component({
  selector: 'app-intake-injector-popup',
  templateUrl: './intake-popup.component.html',
  styleUrls: ['./intake-popup.component.scss']
})
export class IntakePopupComponent implements OnInit {
  public intakeSpeed: number;
  public intakeMode: string;

  public intakeModes: Array<string> = JSON.parse(this.globalVars.getVar("intakeModes"));;
  public selectedIntakeMode: string = this.globalVars.getVar("Intake Mode");
  public selectedIntakeSpeed: number | null = 0;

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      this.intakeSpeed = parseInt(this.globalVars.getVar("Intake Speed"));
      this.intakeMode = this.globalVars.getVar("Intake Mode");
    });
  }
  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  updateIntakeMode():void{
    this.robotData.sendRobotData("Intake Mode", this.selectedIntakeMode);
  }
  
  updateIntakeSpeed():void{
    setTimeout(()=>{
      this.robotData.sendRobotData("Intake Speed", (this.selectedIntakeSpeed as number).toString());
    }, 20)
  }

}
