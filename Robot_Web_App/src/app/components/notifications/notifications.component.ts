import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { notificationObj } from 'src/model';
import { trigger, style, animate, transition } from '@angular/animations';
@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss'],
  animations: [ // Animations to have the notification fade in and out 
    trigger('inOut', [
      transition('void => *', [ 
        style({ opacity: 0 }),           
        animate('750ms',
          style({ 
            opacity: 1 
          })         
        )
      ]),
      transition('* => void', [
        animate('750ms', 
          style({ opacity: 0 }) 
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
    this.notificationText = title; // Set the text to a val to make it render
    setTimeout(()=>{ 
      this.notificationText = null; // Unrender the container by setting the text to null
    }, 7500)
  }

  ngOnDestroy():void{
    if(this.globalSub){
      this.globalSub.unsubscribe();
    }
  }

}
