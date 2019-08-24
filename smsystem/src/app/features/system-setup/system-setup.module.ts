import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MiscellaneousModule } from 'app/features/miscellaneous/miscellaneous.module';
import { SystemSetupRoutingModule } from './system-setup-routing.module';

import { TinyMCEComponent } from 'app/shared';
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
  MiscellaneousModule,
  SystemSetupRoutingModule,
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
