import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { GetRobotDataService } from 'src/app/services/robot-data/get-robot-data.service';

@Component({
  selector: 'app-intake-injector-popup',
  templateUrl: './intake-popup.component.html',
  styleUrls: ['./intake-popup.component.scss']
})
export class IntakePopupComponent implements OnInit {

  // Crickets

  constructor() { }

  ngOnInit(): void {
    
  }


}
