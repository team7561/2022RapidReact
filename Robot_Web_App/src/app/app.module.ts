import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Angular modules
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';

// Custom modules
import { ConnectionMenuComponent } from './components/connection-menu/connection-menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SwerveDirectionComponent } from './components/swerve-direction/swerve-direction.component';
import { OffsetsComponent } from './components/offsets/offsets.component';
import { GeneralStatsComponent } from './components/general-stats/general-stats.component';
import { AutoComponent } from './components/auto/auto.component';
import { SettingsComponent } from './components/settings/settings.component';
import { ShooterControlComponent } from './components/shooter-control/shooter-control.component';

// Angular material imports
import { MatInputModule } from '@angular/material/input';
import { TimerComponent } from './components/timer/timer.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatSliderModule } from '@angular/material/slider';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  declarations: [
    AppComponent,
    ConnectionMenuComponent,
    TimerComponent,
    SwerveDirectionComponent,
    GeneralStatsComponent,
    OffsetsComponent,
    AutoComponent,
    ShooterControlComponent,
    SettingsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    HttpClientModule,
    MatCheckboxModule,
    MatTabsModule,
    MatIconModule,
    MatSliderModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
