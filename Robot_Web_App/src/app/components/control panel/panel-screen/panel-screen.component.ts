import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { KtdGridComponent, KtdGridLayout } from '@katoid/angular-grid-layout';
import { Subscription } from 'rxjs';
import { DOCUMENT } from '@angular/common';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { board } from 'src/model';

@Component({
  selector: 'app-panel-screen',
  templateUrl: './panel-screen.component.html',
  styleUrls: ['./panel-screen.component.scss']
})
export class PanelScreenComponent implements OnInit {
  @ViewChild(KtdGridComponent, {static: true}) grid: KtdGridComponent;

  public columnCount: number = Math.floor(window.innerWidth / 100);
  public rowheight: number = 100;
  public compactType: 'vertical' | 'horizontal' | null = null;
  public layout: KtdGridLayout = [];

  private globalSub: Subscription;

  constructor(@Inject(DOCUMENT) public document: Document, private globalVar: DynamicGlobalsService) { }

  ngOnInit(): void {
    this.renderBoards();
    this.globalSub = this.globalVar.getSubject().subscribe(()=>{
      //console.log(this.layout)
      this.renderBoards();
      
    });
  }

  renderBoards():void{
    let currentBoards: board[] = JSON.parse(this.globalVar.getVar("boardList"));
    for(var i=0; i<currentBoards.length; i++){
      let thisBoard: {id: string, x: number, y: number, w: number, h: number} = {
        'id': currentBoards[i]['id'].toString(), 
        'x': currentBoards[i]['x'],
        'y': currentBoards[i]['y'],
        'w': currentBoards[i]["width"],
        "h": currentBoards[i]["height"]};
      let renderedBoard:boolean =  false;
      for(var j=0; j<this.layout.length; j++){
        if(this.layout[j]['id'] == thisBoard['id']){
          renderedBoard = true;
          break;
        }
      }
      if(!renderedBoard){
        let newLayout: {id: string, x: number, y: number, w: number, h: number}[] = [];
        for(var j=0; j<this.layout.length; j++){
          newLayout.push(this.layout[j]);
        }
        newLayout.push(thisBoard)

        this.layout = newLayout
      }
    }
  }

  deleteBoard(id: string){
    let newLayout: {id: string, x: number, y: number, w: number, h: number}[] = [];
    let currentBoards: board[] = JSON.parse(this.globalVar.getVar("boardList"));
    for(var i: number = 0; i<this.layout.length; i++){
      if(this.layout[i]['id'] != id){
        newLayout.push(this.layout[i]);
      }else{
        currentBoards = currentBoards.splice(i, -1)
      }
    }
    this.globalVar.addVar("boardList", JSON.stringify(currentBoards), false);
    this.layout = newLayout;
  }

  onLayoutUpdated(layout: KtdGridLayout) {
    let currentLayout: board[] = JSON.parse(this.globalVar.getVar("boardList"));
    for(var i=0; i<layout.length; i++){
      currentLayout[i]['x'] = layout[i]['x'];
      currentLayout[i]['y'] = layout[i]['y'];
      currentLayout[i]['width'] = layout[i]['w'];
      currentLayout[i]['height'] = layout[i]['h'];
    }
    this.globalVar.addVar('boardList', JSON.stringify(currentLayout), true);
    this.layout = layout;
  }
}
