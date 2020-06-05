import { NgModule } from '@angular/core';
import {
  NbActionsModule,
  NbButtonModule,
  NbCardModule,
  NbTabsetModule,
  NbUserModule,
  NbRadioModule,
  NbSelectModule,
  NbListModule,
  NbIconModule,
} from '@nebular/theme';
import { NgxEchartsModule } from 'ngx-echarts';

import { LayoutModule } from 'app/layouts/layout.module';
import { DashboardComponent } from './dashboard.component';
import { CounterCardComponent } from './counter-card/counter-card.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    FormsModule,
    LayoutModule,
    NbCardModule,
    NbUserModule,
    NbButtonModule,
    NbTabsetModule,
    NbActionsModule,
    NbRadioModule,
    NbSelectModule,
    NbListModule,
    NbIconModule,
    NbButtonModule,
    NgxEchartsModule,
  ],
  declarations: [
    DashboardComponent,
    CounterCardComponent,
  ],
})
export class DashboardModule { }
