import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { PanelScreenComponent } from './components/control panel/panel-screen/panel-screen.component';
import { HomeScreenComponent } from './components/home/home-screen/home-screen.component';

const routes: Routes = [
  {
    "path": "",
    "component": HomeScreenComponent
  },
  {
    "path": "control_panel",
    "component": PanelScreenComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
