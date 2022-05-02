import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';

@Component({
  selector: 'app-general-stats',
  templateUrl: './general-stats.component.html',
  styleUrls: ['./general-stats.component.scss']
})
export class GeneralStatsComponent implements OnInit {
  public climberStatus: string;
  public intakeStatus: string;
  public injectorMode: string;
  public selectedAuto: string;
  public selectedDrivetrain: string;
  public gyroAngle: string;
  public visionMode: string;
  public shooterASpeed: number;
  public shooterATarget: number;
  public shooterBSpeed: number;
  public shooterBTarget: number;
  public batteryVoltage: number;

  public popuptype: string = "shooter";
  // TODO  Remove default popup string

  private globalSub: Subscription;


  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      // Get robot data 
      this.intakeStatus = this.globalVars.getVar("Intake Speed");
      this.injectorMode = this.globalVars.getVar("Injector Mode");
      this.selectedAuto = this.globalVars.getVar("Auto");
      this.selectedDrivetrain = this.globalVars.getVar("Drivetrain Mode");
      this.gyroAngle = this.globalVars.getVar("Gyro Angle");
      this.visionMode = this.globalVars.getVar("visionMode");
      this.batteryVoltage = parseFloat(this.globalVars.getVar("Battery Voltage"));
      this.shooterASpeed = parseInt(this.globalVars.getVar("Shooter A Speed"));
      this.shooterATarget = parseInt(this.globalVars.getVar("Shooter A Setpoint"));
      this.shooterBSpeed = parseInt(this.globalVars.getVar("Shooter B Speed"));
      this.shooterBTarget = parseInt(this.globalVars.getVar("Shooter B Setpoint"));
    })
  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }
  


}
