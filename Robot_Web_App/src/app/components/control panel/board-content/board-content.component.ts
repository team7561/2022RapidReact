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
  public thisBoard: board; // Board referenced by this component

  // Current value stored in global vars
  public currentVal: number | null; 
  public currentString: string;

  public minVal: number;
  public maxVal: number;

  // Allows for control of location of elem
  public marginLeftVal: number = 500;
  

  // The types of display that can be selected if the data type = number
  public selectableDisplayTypes: Array<string> = ["number", "graph",  "numberLine"]

  private globalSub: Subscription;
  constructor(private globalVars: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.initBoard();
    this.updateGlobals();
    
    // Set the same min and max vals as the last time the board was rendered
    if(this.thisBoard["additionalData"]){ 
      for(var i=0; i<this.thisBoard["additionalData"].length; i++){
        if(this.thisBoard["additionalData"][i]["key"] === "minVal"){
          this.minVal = parseFloat(this.thisBoard["additionalData"][i]["val"]);
        }
        if(this.thisBoard["additionalData"][i]["key"] === "maxVal"){
          this.maxVal = parseFloat(this.thisBoard["additionalData"][i]["val"]);
        }
      }
    }

    this.globalSub = this.globalVars.getSubject().subscribe(()=>{
      // Data is only managed in the component if it isn't a preset
      if(!this.thisBoard['isPreset']){
        if(this.thisBoard['displayType'] != 'string'){
          this.currentVal = parseFloat(this.globalVars.getVar(this.thisBoard['presetType']));
        }else{
          this.currentString = this.globalVars.getVar(this.thisBoard['presetType']);
        }
      }
    });
  }

  initBoard():void{
    // Find which value in the global vars is representative of the board 
    let currentLayoutData = JSON.parse(this.globalVars.getVar("boardList"));
    for(var i:number = 0; i<currentLayoutData.length; i++){
      if(currentLayoutData[i]['id'] == this.id){
        this.thisBoard = currentLayoutData[i];
        break;
      }
    }
  }

  updateGlobals():void{
    // Update the global vars with current data about how the board is configured 
    let currentLayoutData = JSON.parse(this.globalVars.getVar("boardList"));
    for(var i:number = 0; i<currentLayoutData.length; i++){
      if(currentLayoutData[i]['id'] == this.id){
        currentLayoutData[i] = this.thisBoard;
        break;
      }
    }
    this.globalVars.addVar("boardList", JSON.stringify(currentLayoutData), false)
  }

  updateDataGraph():void{
    // Update the minimum and maximum vals for the graph
    this.minVal = parseFloat((document.getElementById("minNumInput") as HTMLInputElement).value);
    this.maxVal = parseFloat((document.getElementById("maxNumInput") as HTMLInputElement).value);

    // Add min and max vals to additional data for the board
    var foundMinVal: boolean = false; // Tracks if minVal is already present in additional data
    var foundMaxVal: boolean = false;
    for(var i=0; i<this.thisBoard["additionalData"].length; i++){
      if(this.thisBoard["additionalData"][i]["key"] === "minVal"){
        this.thisBoard["additionalData"][i]["val"] === this.minVal.toString();
        foundMinVal = false;
      }
      if(this.thisBoard["additionalData"][i]["key"] === "maxVal"){
        this.thisBoard["additionalData"][i]["val"] === this.maxVal.toString();
        foundMaxVal = false;
      }
    }
    

    // If min or max vals aren't located in the additional data, add them as new elems to the array
    if(!foundMinVal){
      this.thisBoard["additionalData"].push({"key": "minVal", "val": this.minVal.toString()})
    }
    if(!foundMaxVal){
      this.thisBoard["additionalData"].push({"key": "maxVal", "val": this.maxVal.toString()})
    }

    this.updateGlobals();
  }
}
