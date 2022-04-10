import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Angular modules
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';

// Custom modules
import { ConnectionMenuComponent } from './components/connection-menu/connection-menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Angular material imports
import { MatInputModule } from '@angular/material/input';
import { TimerComponent } from './components/timer/timer.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTabsModule } from '@angular/material/tabs';

@NgModule({
  declarations: [
    AppComponent,
    ConnectionMenuComponent,
    TimerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    HttpClientModule,
    MatCheckboxModule,
    MatTabsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
