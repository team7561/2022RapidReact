import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { catchError, Subscription, throwError } from 'rxjs';
import { DynamicGlobalsService } from 'src/app/services/globals/dynamic-globals.service';
import { cameraData } from 'src/model';

@Component({
  selector: 'app-camera-view',
  templateUrl: './camera-view.component.html',
  styleUrls: ['./camera-view.component.scss']
})
export class CameraViewComponent implements OnInit {
  public cameraDataOptions: cameraData[] = JSON.parse(this.globalVars.getVar("cameraAdresses"));
  public selectedCameraOption: cameraData = this.cameraDataOptions[0];
  public selectedCameraOptionName: string = this.selectedCameraOption['name'];

  public linkWorks: boolean = true;

  private globalSub: Subscription;
  constructor(private globalVars: DynamicGlobalsService, private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.globalVars.getSubject().subscribe(()=>{
      this.cameraDataOptions = JSON.parse(this.globalVars.getVar("cameraAdresses"));
    });
    this.updateSelectedSource();
  }

  addNewSource(event: Event):void{
    event.preventDefault();

    let camName: string = (document.getElementById("nameInput") as HTMLInputElement).value;
    let camIP: string = (document.getElementById("ipInput") as HTMLInputElement).value;

    this.cameraDataOptions.push({"ip": camIP, "name": camName});
    setTimeout(()=>{
      (document.getElementById("nameInput") as HTMLInputElement).value = "";
      (document.getElementById("ipInput") as HTMLInputElement).value = "";
    }, 50)
    this.globalVars.addVar("cameraAdresses", JSON.stringify(this.cameraDataOptions), false);
  }

  updateSelectedSource():void{
    this.linkWorks = false;
    for(var i=0; i<this.cameraDataOptions.length; i++){
      if(this.cameraDataOptions[i]['name'] == this.selectedCameraOptionName){
        this.selectedCameraOption = this.cameraDataOptions[i];
        break;
      }
    }
    this.checkLinkWorks("http://" + this.selectedCameraOption["ip"]);
  }

  checkLinkWorks(link: string): void{
    console.log(link)
    this.httpClient.get(link).pipe(catchError(()=>{
      console.log("FAILED");
      this.linkWorks = false;
      return throwError("Couldn't get camera info")
    })).subscribe((res)=>{
      console.log(res)
      this.linkWorks = true;
    });
  }



}
