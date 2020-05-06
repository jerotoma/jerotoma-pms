import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { ThemeModule } from 'app/@theme/theme.module';
import { FeaturesComponent } from './features.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { ECommerceModule } from './resources/e-commerce.module';
import { SecuritiesModule} from './securities/securities.module';
import { FeaturesRoutingModule } from './features-routing.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';

@NgModule({
  imports: [
    FeaturesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    ECommerceModule,
    MiscellaneousModule,
    SecuritiesModule,
  ],
  declarations: [
    FeaturesComponent,
  ],
})
export class FeaturesModule {
}
