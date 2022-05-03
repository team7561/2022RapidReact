import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';

@Component({
  selector: 'app-timer',
  templateUrl: './timer.component.html',
  styleUrls: ['./timer.component.scss']
})
export class TimerComponent implements OnInit {
  public doLocalTimer: boolean = false;
  public timerStarted: boolean = false;
  private timerVal: number; 
  public timerString: string;
  private startTime: Date; 
  private globalSub: Subscription;
  
  constructor(private globalVar: DynamicGlobalsService) { }
  
  ngOnInit(): void {
    this.timerVal = parseInt(this.globalVar.getVar("matchTime")); // Get the number of seconds a match lasts for 
    this.globalVar.addVar("timeRemaining", this.timerVal.toString(), false);
    this.timerString =  this.formatTime(this.timerVal);
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      if(this.globalVar.getVar("connectionStatus") == "connected"){
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
    this.doLocalTimer = event.checked // Only begin the timer if a connection is established AND the user has indicated to start
  }

  countDownTimer():void{
    setInterval(()=>{ // Update the 4 times per second
      var timeElapsed = Math.round((new Date().getTime()- this.startTime.getTime()) / 1000);
      if(timeElapsed >= this.timerVal){
        timeElapsed = this.timerVal;
      }
      var timeRemaining = this.timerVal - timeElapsed;

      this.globalVar.addVar("timeRemaining", timeRemaining.toString(), false);
      if(this.doLocalTimer){
        this.timerString = this.formatTime(timeRemaining);
      }else{
        this.timerString = this.formatTime(parseInt(this.globalVar.getVar("Game Time")));
      }
    }, 250)
  }


  formatTime(seconds: number): string{
    // Returns time in format MM:SS for any second counr
    var timeString = "";
    var minutes = Math.floor(seconds / 60);
    if(minutes != 0){
      if(minutes >= 10){
        timeString += minutes.toString();
      }else{
        timeString += "0" + minutes.toString();
      }
    }else{
      timeString += "00";
    }
    timeString += ":";
    seconds = seconds - (60 * Math.floor(seconds / 60));
    if(seconds < 10){
      timeString += "0" + seconds.toString();
    }else{
      timeString += seconds.toString();
    }

    return timeString;
  }

}
