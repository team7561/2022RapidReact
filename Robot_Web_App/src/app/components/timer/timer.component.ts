import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { interval, Subscription } from 'rxjs';
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
    this.timerVal = parseInt(this.globalVar.getVar("matchTime"));
    this.timerString =  Math.floor(this.timerVal / 60).toString() + ":" + (this.timerVal % 60).toString();
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      if(this.globalVar.getVar("connectionStatus") == "connected" && this.doTimer){
        if(!this.timerStarted){ // Ensure the timer hasn't already begun
          this.startTime = new Date();
          this.countDownTimer() 
        }
        this.timerStarted = true;

      }
    })
  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

  timerChange(event: MatCheckboxChange):void{
    this.doTimer = event.checked
  }

  countDownTimer():void{
    setInterval(()=>{
      var timeElapsed = Math.round((new Date().getTime()- this.startTime.getTime()) / 1000);
      if(timeElapsed >= this.timerVal){
        timeElapsed = this.timerVal
      }
      var timeRemaining = this.timerVal - timeElapsed;

      var blankSpace = ""
      if((timeRemaining % 60).toString().length == 1 ){ // Add an extra 0 to seconds if necessary
        blankSpace = "0"
      }
      this.timerString =  Math.floor(timeRemaining / 60).toString() + ":" + blankSpace + (timeRemaining % 60).toString();
    }, 1000)
  }

}
