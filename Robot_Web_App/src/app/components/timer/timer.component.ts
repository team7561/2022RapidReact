import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.scss']
})
export class TimerComponent implements OnInit {
  private doTimer: boolean = true;
  public timerStarted: boolean = false;
  private timerVal: number; 
  public timerString: string;
  private startTime: Date; 
  private globalSub: Subscription;
  
  constructor(private globalVar: DynamicGlobalsService) { }
  
  ngOnInit(): void {
    this.timerVal = parseInt(this.globalVar.getVar("matchTime")); // Get the number of seconds a match lasts for 
    this.globalVar.addVar("timeRemaining", this.timerVal.toString(), false);
    var blankSpace = ""
    if((this.timerVal % 60).toString().length <= 1){ // Add an extra 0 to seconds if necessary
      blankSpace = "0"
    }
    this.timerString =  Math.floor(this.timerVal / 60).toString() + ":" + blankSpace + (this.timerVal % 60).toString();
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      if(this.globalVar.getVar("connectionStatus") == "connected" && this.doTimer){
        if(!this.timerStarted){ // Ensure the timer hasn't already begun
          this.startTime = new Date(); // Time from which the timer is measured
          this.countDownTimer() 
        }
        this.timerStarted = true;

      }
    })
  }

  ngOnDestroy():void{ // Prevents overuse of system resources
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  timerChange(event: MatCheckboxChange):void{
    this.doTimer = event.checked // Only begin the timer if a connection is established AND the user has indicated to start
  }

  countDownTimer():void{
    setInterval(()=>{ // Update the timer once per second
      var timeElapsed = Math.round((new Date().getTime()- this.startTime.getTime()) / 1000);
      if(timeElapsed >= this.timerVal){
        timeElapsed = this.timerVal;
      }
      var timeRemaining = this.timerVal - timeElapsed;

      var blankSpace = "";
      if((timeRemaining % 60).toString().length <= 1){ // Add an extra 0 to seconds if necessary
        blankSpace = "0";
      }
      this.globalVar.addVar("timeRemaining", timeRemaining.toString(), false);
      this.timerString =  Math.floor(timeRemaining / 60).toString() + ":" + blankSpace + (timeRemaining % 60).toString();
    }, 1000)
  }

}
