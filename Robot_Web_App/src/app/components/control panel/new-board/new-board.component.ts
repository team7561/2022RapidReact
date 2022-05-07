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
  public displayKeys: Array<string>;


  public searchStr: string = "";

  private globalKeys: Array<string> = [];
  private globalSub: Subscription;

  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      // Get total list of all the global vars present
      let currentGlobals: keyValPair[] = this.globalVars.getAllVars();
      this.globalKeys = [];
      for(var i=0; i<currentGlobals.length; i++){
        this.globalKeys.push(currentGlobals[i]["key"]);
      }
      this.updateDisplayKeys();
    });
  }

  updateDisplayKeys():void{
    setTimeout(() => {
      this.searchStr = (document.getElementById("searchInput") as HTMLInputElement).value;
      this.displayKeys = [];
      for(var i=0; i<this.globalKeys.length; i++){
        if(this.globalKeys[i].toLowerCase().indexOf(this.searchStr.toLowerCase()) >=0){
          this.displayKeys.push(this.globalKeys[i])
        }
      }
    }, 50);
  }

  updateBoardList(boardType: string, isPreset: boolean):void{
    let currentBoardsList: board[] = JSON.parse(this.globalVars.getVar("boardList"));
    let mostRecentBoardId: number = -1;
    // All possible display types
    let displayType: "number" | "string" | "graph" | "img" | "numberLine" = "number"

    if(currentBoardsList.length != 0){
      mostRecentBoardId = (currentBoardsList.slice(-1) as unknown as board[])[0]['id'];
    }

    // Default height and width of a board
    let thisHeight = 2;    
    let thisWidth = 4;

    if(isPreset){ // If the board is a preset Applu custom default sizing to match component
      displayType = "graph"
      switch(boardType){
        case("Offsets"):
          thisHeight = 5;
          thisWidth = 4;
          break;
        case("Swerve Angle"):
          thisHeight = 3;
          thisWidth = 2;
          break;
        case("Shooter Control"):
          thisHeight = 6;
          thisWidth = 3;
          break;
        case("Shooter Status"):
          thisHeight = 3;
          thisWidth = 6;
          break;
        case("Shooter Hood Control"):
          thisHeight = 6;
          thisWidth = 3;
          break;
        case("Injector Control"):
          thisHeight = 4;
          thisWidth = 6;
          break;
        case("Intake Control"):
          thisHeight = 4;
          thisWidth = 6;
          break;
        case("Ball Position"):
          thisHeight = 5;
          thisWidth = 8;
          break;
        case("Vision Target"):
          thisHeight = 5;
          thisWidth = 8;
          break;
        case("Camera View"):
          thisHeight = 8;
          thisWidth = 5;
          break;
        case("Robot Pos"):
          thisHeight = 7;
          thisWidth = 16;
          break;
      }
    }else{
      // Determine if data being stored is a number or string
      if(isNaN(parseFloat(this.globalVars.getVar(boardType)))){
        displayType = "string";
      }
    }
    // Add it to the global vars
    currentBoardsList.push({"id": mostRecentBoardId + 1, 
                          "x": 0,
                          "y": 0,
                          "height": thisHeight,
                          "width": thisWidth,
                          "isPreset": isPreset,
                          "presetType": boardType,
                          "displayType": displayType,
                          "additionalData": []});
    this.globalVars.addVar("boardList", JSON.stringify(currentBoardsList), true);

  }

  loadRecording(event: Event):void{
    // Read recording and set the board layout to uploaded data
    const target = event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];
    let reader = new FileReader();
    var fileData: board[] = [];
    // Whenever the reader has a file loaded 
    reader.onload = function(){
      fileData = JSON.parse(reader.result as string)
    }
    reader.readAsText(file);
    setTimeout(()=>{
      // Allow time for filereader to read file data 
      this.globalVars.addVar("boardList", JSON.stringify(fileData), true);
      setTimeout(()=>{
        // Reload page to force changes to take place
        location.reload();
      }, 100);
    }, 50);
    
  }
  exportLayout():void{
    // Get file download and open download window for user
    var dataStr = "data:text/json;charset=utf-8," + this.globalVars.getVar("boardList");
    var downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href", dataStr);
    downloadAnchorNode.setAttribute("download",  "controlBoardLayout.json");
    document.body.appendChild(downloadAnchorNode); // required for firefox
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
  }
}
