import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TinyMCEComponent } from 'app/shared';
import { MiscellaneousModule } from 'app/features/miscellaneous/miscellaneous.module';
import { SystemSetupRoutingModule } from './system-setup-routing.module';
import { SystemSetupComponent } from './system-setup.component';

const COMPONENTS = [
  SystemSetupComponent,
  TinyMCEComponent,
];

const ENTRY_COMPONENTS = [

];

const MODULES = [
  FormsModule,
  ReactiveFormsModule,
  SystemSetupRoutingModule,
  MiscellaneousModule,
];

const SERVICES = [
];


@NgModule({
imports: [
  ...MODULES,
],
declarations: [
  ...COMPONENTS,
],
providers: [
 ...SERVICES,
],
entryComponents: [
  ...ENTRY_COMPONENTS,
],
})
export class SystemSetupModule { }
