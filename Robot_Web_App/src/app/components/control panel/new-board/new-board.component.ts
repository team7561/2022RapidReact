import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { board, keyValPair } from 'src/model';

@Component({
  selector: 'app-new-board',
  templateUrl: './new-board.component.html',
  styleUrls: ['./new-board.component.scss']
})
export class NewBoardComponent implements OnInit {
  public globalVarKeys: Array<string>;

  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      let currentGlobals: keyValPair[] = this.globalVars.getAllVars();
      this.globalVarKeys = [];
      for(var i=0; i<currentGlobals.length; i++){
        this.globalVarKeys.push(currentGlobals[i]["key"]);
      }
    });
  }

  updateBoardList(boardType: string, isPresent: boolean):void{
    let currentBoardsList: board[] = JSON.parse(this.globalVars.getVar("boardList"));
    let mostRecentBoardId: number = -1;
    let mostRecentBoardX: number = -1;
    let displayType: "number" | "string" | "graph" | "img" | "numberLine" = "number"

    if(currentBoardsList.length != 0){
      mostRecentBoardId = (currentBoardsList.slice(-1) as unknown as board[])[0]['id'];
      mostRecentBoardX = (currentBoardsList.slice(-1) as unknown as board[])[0]['x'];
    }

    let thisHeight = 1;    
    let thisWidth = 1;

    if(isPresent){
      switch(boardType){
        case("Offsets"):
          thisHeight = 3
          thisWidth = 3
          break;
        case("Swerve Angle"):
          thisHeight = 2
          thisWidth = 2
          break;
        case("Shooter Control"):
          thisHeight = 3
          thisWidth = 2
          break;
        case("Auto Position"):
          thisHeight = 3
          thisWidth = 6
          break;
        case("Camera Feed"):
          thisHeight = 3
          thisWidth = 6
          break;
      }
    }else{
      if(isNaN(parseFloat(this.globalVars.getVar("boardType")))){
        displayType = "string"
      }
    }
    currentBoardsList.push({"id": mostRecentBoardId + 1, 
                          "x": mostRecentBoardX + 1,
                          "y": 0,
                          "height": thisHeight,
                          "width": thisWidth,
                          "isPreset": isPresent,
                          "presetType": boardType,
                          "displayType": displayType});
    this.globalVars.addVar("boardList", JSON.stringify(currentBoardsList), true);

  }

}
