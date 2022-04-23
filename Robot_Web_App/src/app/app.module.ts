import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

// Angular modules
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';

// Custom modules
import { ConnectionMenuComponent } from './components/connection-menu/connection-menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SwerveDirectionComponent } from './components/drivetrain/swerve-direction/swerve-direction.component';
import { OffsetsComponent } from './components/drivetrain/offsets/offsets.component';
import { GeneralStatsComponent } from './components/general-stats/general-stats.component';
import { AutoComponent } from './components/auto/auto.component';
import { SettingsComponent } from './components/settings/settings.component';
import { ShooterControlComponent } from './components/shooter/shooter-control/shooter-control.component';

// Angular material imports
import { MatInputModule } from '@angular/material/input';
import { TimerComponent } from './components/timer/timer.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { MatSliderModule } from '@angular/material/slider';
import { MatSelectModule } from '@angular/material/select';
import { GraphsComponent } from './components/graphs/graph/graph.component';
import { NgChartsModule } from 'ng2-charts';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { FooterComponent } from './components/footer/footer.component';
import { DrivetrainPopupComponent } from './components/drivetrain/drivetrain-popup/drivetrain-popup.component';
import { LineGraphComponent } from './components/graphs/line-graph/line-graph.component';
import { VisionGraphComponent } from './components/graphs/vision-graph/vision-graph.component';
import { ShooterPopupComponent } from './components/shooter/shooter-popup/shooter-popup.component';
import { ShooterStatusComponent } from './components/shooter/shooter-status/shooter-status.component';
import { SubsystemsComponent } from './components/subsystems/subsystems.component';
import { IntakePopupComponent } from './components/intake/intake-popup/intake-popup.component';
import { IntakeControlComponent } from './components/intake/intake-control/intake-control.component';
import { InjectorPopupComponent } from './components/injector/injector-popup/injector-popup.component';
import { InjectorControlComponent } from './components/injector/injector-control/injector-control.component';

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
    GraphsComponent,
    NotificationsComponent,
    FooterComponent,
    DrivetrainPopupComponent,
    LineGraphComponent,
    VisionGraphComponent,
    ShooterPopupComponent,
    ShooterStatusComponent,
    SubsystemsComponent,
    IntakePopupComponent,
    IntakeControlComponent,
    InjectorPopupComponent,
    InjectorControlComponent,
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
    MatSelectModule,
    NgChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
