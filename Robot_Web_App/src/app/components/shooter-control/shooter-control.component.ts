import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/get-robot-data.service';

@Component({
  selector: 'app-shooter-control',
  templateUrl: './shooter-control.component.html',
  styleUrls: ['./shooter-control.component.scss']
})
export class ShooterControlComponent implements OnInit {
  public shooterAVal: number;
  public shooterBVal: number;
  public shooterASetPoint: number |null = 0;
  public shooterBSetPoint: number |null = 0;
  public shooterADisplaySetPoint: number |null = 0;
  public shooterBDisplaySetPoint: number |null = 0;

  private gloabalSub: Subscription;

  constructor(private globalVar: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.gloabalSub = this.globalVar.getSubject().subscribe(()=>{
      this.shooterADisplaySetPoint =  parseInt(this.globalVar.getVar("Shooter A Setpoint"));// Get the default shooter set point
      this.shooterBDisplaySetPoint = parseInt(this.globalVar.getVar("Shooter B Setpoint"));
      this.shooterAVal = parseInt(this.globalVar.getVar("Shooter A Speed"));
      this.shooterBVal = parseInt(this.globalVar.getVar("Shooter B Speed"));
    });
  }

  ngOnDestroy():void{
    this.gloabalSub.unsubscribe();
  }

  updateShooterVal():void{
    setTimeout(()=>{
      this.robotData.sendRobotData("Shooter A Setpoint", this.shooterASetPoint?.toString() as string); // Send the robot the new setpoints when they are updated
      this.robotData.sendRobotData("Shooter B Setpoint", this.shooterBSetPoint?.toString() as string);
    }, 25)
  }

  setShooterVal(shooterType: string, val: number){
    this.updateShooterVal();
    if(shooterType == "A"){
      this.shooterADisplaySetPoint = val;
      this.shooterASetPoint = val;
    }else{
      this.shooterBDisplaySetPoint = val;
      this.shooterBSetPoint = val;
    }
  }

}
