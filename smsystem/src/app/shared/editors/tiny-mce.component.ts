
import {
  Component,
  OnDestroy,
  AfterViewInit,
  Output,
  EventEmitter,
  ElementRef,
} from '@angular/core';
import { environment } from 'environments/environment';

@Component({
  selector: 'app-mce-editor',
  template: '',
})
export class TinyMCEComponent implements OnDestroy, AfterViewInit {

  @Output() editorKeyup = new EventEmitter<any>();
  editor: any;

  constructor(private host: ElementRef) { }

  ngAfterViewInit() {
    tinymce.init({
      target: this.host.nativeElement,
      plugins: ['link', 'paste', 'table'],
      skin_url: environment.END_POINTS.base + '/assets/skins/lightgray',
      setup: (editor: any) => {
        this.editor = editor;
        editor.on('keyup', () => {
          this.editorKeyup.emit(editor.getContent());
        });
      },
      height: '320',
    });
  }

  ngOnDestroy() {
    tinymce.remove(this.editor);
  }
}
