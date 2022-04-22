import { Component, OnInit } from '@angular/core';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/get-robot-data.service';

@Component({
  selector: 'app-drivetrain-popup',
  templateUrl: './drivetrain-popup.component.html',
  styleUrls: ['./drivetrain-popup.component.scss']
})
export class DrivetrainPopupComponent implements OnInit {
  public driveModes: Array<string> = JSON.parse(this.globalVars.getVar("driveModes"));
  public driveMode: string = this.globalVars.getVar("Drivetrain Mode");

  constructor(private globalVars: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
  }

  updateDriveMode(): void{
    this.globalVars.addVar("Drivetrain Mode", this.driveMode, true)
    this.robotData.sendRobotData("Drivetrain Mode", this.driveMode);
  }

}
