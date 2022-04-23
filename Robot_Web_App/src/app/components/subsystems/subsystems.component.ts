import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';

@Component({
  selector: 'app-subsystems',
  templateUrl: './subsystems.component.html',
  styleUrls: ['./subsystems.component.scss']
})
export class SubsystemsComponent implements OnInit {
  public popuptype: string = "";
  public shooterAStatusColor: string = "#20bf55"
  public shooterBStatusColor: string = "#20bf55"

  // TODO remove default option
  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      if(parseInt(this.globalVars.getVar("Shooter A Speed")) == 0){
        this.shooterAStatusColor = "#8B8C89";
      }else if(Math.abs(parseInt(this.globalVars.getVar("Shooter A Speed")) - parseInt(this.globalVars.getVar("Shooter A Setpoint"))) > 50){
         // If the shooter is more then 50 off the setpoint
        this.shooterAStatusColor = "#eec643";
      }else{
        this.shooterAStatusColor = "#20bf55";
      }

      if(parseInt(this.globalVars.getVar("Shooter B Speed")) == 0){
        this.shooterBStatusColor = "#8B8C89";
      }else if(Math.abs(parseInt(this.globalVars.getVar("Shooter B Speed")) - parseInt(this.globalVars.getVar("Shooter B Setpoint"))) > 50){
        // If the shooter is more then 50 off the setpoint
        this.shooterBStatusColor = "#eec643";
      }else{
        this.shooterBStatusColor = "#20bf55";
      }
    });
  }

  onParentPopupClick(event: Event):void{ // Hides the popup IF the click originates from parent div
    if((event.target as HTMLElement).id == "popUpParent"){
      document.getElementById("popUpParent")?.classList.toggle("hidden");
    }
  }

}
