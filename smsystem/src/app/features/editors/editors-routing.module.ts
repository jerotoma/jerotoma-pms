import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EditorsComponent } from './editors.component';
import { TinyMCEComponent } from 'app/shared';
import { TinyMCEditorComponent } from './tiny-mce/tiny-mceditor.component';
import { CKEditorComponent } from './ckeditor/ckeditor.component';

const routes: Routes = [{
  path: '',
  component: EditorsComponent,
  children: [{
    path: 'tinymce',
    component: TinyMCEditorComponent,
  }, {
    path: 'ckeditor',
    component: CKEditorComponent,
  }],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EditorsRoutingModule { }

export const routedComponents = [
  EditorsComponent,
  TinyMCEComponent,
  CKEditorComponent,
  TinyMCEditorComponent,
];
