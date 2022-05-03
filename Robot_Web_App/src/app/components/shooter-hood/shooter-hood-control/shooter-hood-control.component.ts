import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/robot-data/get-robot-data.service';

@Component({
  selector: 'app-shooter-hood-control',
  templateUrl: './shooter-hood-control.component.html',
  styleUrls: ['./shooter-hood-control.component.scss']
})
export class ShooterHoodControlComponent implements OnInit {

  // Similar code to the shooter control

  public shooterHoodVal: number;
  public shooterHoodSetpoint: number | null = 0;

  private globalSub: Subscription;

  constructor(private globalVar: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      this.shooterHoodVal = parseFloat(this.globalVar.getVar("ShooterHood"));
    });
  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  updateShooterHoodVal():void{
    setTimeout(()=>{
      this.robotData.sendRobotData("ShooterHood", this.shooterHoodVal?.toString() as string); // Send the robot the new setpoints when they are updated
    }, 25)
  }

  setShooterHoodVal(val: number):void{
    this.shooterHoodSetpoint = val;
    this.updateShooterHoodVal();
  }
}
