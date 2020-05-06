import { NgModule } from '@angular/core';
import { NbMenuModule } from '@nebular/theme';

import { ThemeModule } from 'app/@theme/theme.module';
import { FeaturesComponent } from './features.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { ECommerceModule } from './resources/e-commerce.module';
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
  ],
  declarations: [
    FeaturesComponent,
  ],
})
export class FeaturesModule {
}
