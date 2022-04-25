import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';

@Component({
  selector: 'app-shooter-status',
  templateUrl: './shooter-status.component.html',
  styleUrls: ['./shooter-status.component.scss']
})
export class ShooterStatusComponent implements OnInit {
  public shooterAAction: string;
  public shooterBAction: string;
  public shooterADeficit: number;
  public shooterBDeficit: number;

  public shooterAColor: string = "#6096BA";
  public shooterBColor: string = "#6096BA";

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      this.shooterADeficit = parseInt(this.globalVars.getVar("Shooter A Setpoint")) -  parseInt(this.globalVars.getVar("Shooter A Speed"));
      this.shooterBDeficit = parseInt(this.globalVars.getVar("Shooter B Setpoint")) - parseInt(this.globalVars.getVar("Shooter B Speed"));

      if(parseInt(this.globalVars.getVar("Shooter A Speed")) == 0){
        this.shooterAAction = "OFF";
        this.shooterAColor = "#8B8C89";
      }else{
        if(Math.abs(this.shooterADeficit) < 50){
          this.shooterAAction = "STABLE";
          this.shooterAColor = "#20bf55";
        }else if(this.shooterADeficit > 0){
          this.shooterAAction = "SPEEDING UP";
          this.shooterAColor = "#eec643";
        }else{
          this.shooterAAction = "SLOWING DOWN";
          this.shooterAColor = "#eec643";
        }
      }
      if(parseInt(this.globalVars.getVar("Shooter B Speed")) == 0){
        this.shooterBAction = "OFF";
        this.shooterBColor = "#8B8C89";
      }else{
        if(Math.abs(this.shooterBDeficit) < 50){
          this.shooterBAction = "STABLE";
          this.shooterBColor = "#20bf55";
        }else if(this.shooterBDeficit > 0){
          this.shooterBAction = "SPEEDING UP";
          this.shooterBColor = "#eec643";
        }else{
          this.shooterBAction = "SLOWING DOWN";
          this.shooterBColor = "#eec643";
        }
      }

    });
  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

}
