import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/robot-data/get-robot-data.service';

@Component({
  selector: 'app-injector-control',
  templateUrl: './injector-control.component.html',
  styleUrls: ['./injector-control.component.scss']
})
export class InjectorControlComponent implements OnInit {
  
  // Values reported by the robot
  public injectorSpeed: number;
  public injectorMode: string;

  // Assume selected Injector mode is the 1st on the array
  public injectorModes: Array<string> = JSON.parse(this.globalVars.getVar("injectorModes"));

  // Values inputted by user
  public selectedInjectorMode: string = this.globalVars.getVar("Injector Mode"); 
  public selectedInjectorSpeed: number | null = 0;

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService, private robotData: GetRobotDataService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      this.injectorSpeed = parseInt(this.globalVars.getVar("Injector Speed"));
      this.injectorMode = this.globalVars.getVar("Injector Mode");
    });
  }
  
  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  updateInjectorMode():void{
    this.robotData.sendRobotData("Injector Mode", this.selectedInjectorMode); 
  }
  
  updateInjectorSpeed():void{
    // Sends the new Injector speed to the robot 
    // NB, for some reason only works if reading data from component is delayed?
    setTimeout(()=>{
      this.robotData.sendRobotData("Injector Speed", (this.selectedInjectorSpeed as number).toString());
    }, 20)
  }
}
