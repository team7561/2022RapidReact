import { Component, Input, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { board } from 'src/model';

@Component({
  selector: 'app-board-content',
  templateUrl: './board-content.component.html',
  styleUrls: ['./board-content.component.scss']
})
export class BoardContentComponent implements OnInit {
  @Input() id: string;
  public thisBoard: board;
  public currentVal: number;
  public currentString: string;


  private globalSub: Subscription;
  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    let currentLayoutData = JSON.parse(this.globalVars.getVar("boardList"));
    for(var i:number = 0; i<currentLayoutData.length; i++){
      if(currentLayoutData[i]['id'] == this.id){
        this.thisBoard = currentLayoutData[i];
        break;
      }
    }

    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      if(!this.thisBoard['isPreset']){
        switch(this.thisBoard['displayType']){
          case("number"):
            this.currentVal = parseFloat(this.globalVars.getVar(this.thisBoard['presetType']));
            break;
          case("string"):
            this.currentString = this.globalVars.getVar(this.thisBoard['presetType'])
            break;
        }
      }
    });
  }

}
