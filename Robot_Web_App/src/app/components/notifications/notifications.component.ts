import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/dynamic-globals.service';
import { notificationObj } from 'src/model';
import { trigger, state, style, animate, transition } from '@angular/animations';
@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss'],
  animations: [
    trigger('inOut', [
      transition('void => *', [ 
        style({ opacity: 0 }),           // initial styles
        animate('750ms',
          style({ 
            opacity: 1 
          })          // final style after the transition has finished
        )
      ]),
      transition('* => void', [
        animate('750ms', 
          style({ opacity: 0 })          // we asume the initial style will be always opacity: 1
        ) 
      ])
    ]) 
  ]
})
export class NotificationsComponent implements OnInit {
  public notificationText: string | null;
  public showNotification: boolean = true;

  private notificationList: Array<notificationObj> = JSON.parse(this.globalVar.getVar("notificationList"));
  private globalSub: Subscription;
  constructor(private globalVar: DynamicGlobalsService) { }

  ngOnInit(): void {
    setTimeout(()=>{ // Allow time for global vars to init 
      this.globalSub = this.globalVar.getSubject().subscribe(()=>{
        var timeRemaining: number = parseInt(this.globalVar.getVar("timeRemaining"));
        if(timeRemaining.toString() != "NaN"){ // Ensure the number is valid
          for(var i=0; i<this.notificationList.length; i++){ // Check all the notifications in the list
            if(timeRemaining <= this.notificationList[i]["timeVal"]){
              this.displayNotification(this.notificationList[i]["title"]);
              this.notificationList.splice(i, 1); // Remove the notification from the local list to aviod displaying it multiple time
            }
          }
        }
      });
    }, 250)
  }

  displayNotification(title: string):void{
    this.notificationText = title;
    setTimeout(()=>{
      this.notificationText = null;
    }, 5000)
  }

}
