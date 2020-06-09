import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { LayoutModule } from 'app/layouts/layout.module';
import { FeaturesComponent } from './features.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { SecuritiesModule} from './securities/securities.module';
import { FeaturesRoutingModule } from './features-routing.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import { AttendancesModule } from './attendances/attendances.module';

@NgModule({
  imports: [
    FeaturesRoutingModule,
    LayoutModule,
    NbMenuModule,
    DashboardModule,
    MiscellaneousModule,
    SecuritiesModule,
    AttendancesModule,
  ],
  declarations: [
    FeaturesComponent,
  ],
})
export class FeaturesModule {
}
