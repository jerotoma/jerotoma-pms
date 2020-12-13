import { Component, OnInit, Input, Output, EventEmitter, ElementRef } from '@angular/core';
import {CdkDragDrop, moveItemInArray, CdkDragStart} from '@angular/cdk/drag-drop';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';
import {
  Program,
  AcademicLevel,
  AcademicLevelCompletionOrder,
  CompletionOrder,
} from 'app/models';
import {
  ProgramService,
  AcademicLevelService,
  CompletionOrderService,
  ModalService,
} from 'app/services';
import { QueryParam } from 'app/utils';

@Component({
  selector: 'app-program-create',
  templateUrl: `./program-create.component.html`,
  styleUrls: [`./program-create.component.scss`],
})
export class ProgramCreateComponent implements OnInit {
  @Input() title: string;
  @Input() action: string = 'create';
  @Output() onCreationSuccess = new EventEmitter();
  @Input() id: string;


  programForm: FormGroup;
  program: Program;
  academicLevels: AcademicLevel[];
  academicLevelIDs: number[] = [];
  academicLevelCompletionOrders: AcademicLevelCompletionOrder[] = [];
  academicLevelCompletionOrder: AcademicLevelCompletionOrder;
  completionOrders: CompletionOrder[];
  listDisplay: string = 'none';
  isSubmitting: boolean = false;
  isLoading: boolean = false;

  constructor(
    private programService:  ProgramService,
    private academicLevelService: AcademicLevelService,
    private formBuilder: FormBuilder,
    private modalService: ModalService,
    private element: ElementRef,
    private completionOrderService: CompletionOrderService,
    protected ref: NbDialogRef<ProgramCreateComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadAcademicLevels();
    this.loadCompletionOrders();
    if (this.action === 'edit') {
        this.loadProgram();
    }
  }
  patchProgram() {
    this.programForm.patchValue({
      name: this.program.name,
      code: this.program.code,
      academicLevelIDs: this.program.academicLevelIDs,
      description: this.program.description,
      id: this.program.id,
    });
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.program = this.programForm.value;
        if (this.action === 'edit') {
            this.updateProgram();
        } else {
          this.programService.createProgram(this.program)
              .subscribe((program: Program ) => {
                this.isSubmitting = false;
                  if (program) {
                    this.program = program;
                    this.modalService.openSnackBar('Program ' + program.name + ' has been created', 'success');
                    this.dismiss();
                  }
              });
        }

  }
  updateProgram() {
    this.programService.updateProgram(this.program)
        .subscribe((program: Program ) => {
          this.isSubmitting = false;
          if (program) {
            this.program = program;
            this.modalService.openSnackBar('Program ' + program.name + ' has been updated', 'success');
            this.dismiss();
          }
      });
    }
  getDescriptionContent(description: string) {
   if (description) {
      this.programForm.patchValue({
        description: description,
      });
    }
  }

  onDragDropChange(event: CdkDragDrop<string[]>) {
    this.changeZIndexValue('1040');
    moveItemInArray(this.academicLevels, event.previousIndex, event.currentIndex);
    if (this.academicLevelCompletionOrders) {
      const academicLevelCompletionOrders = this.academicLevelCompletionOrders;
      this.academicLevelCompletionOrders = [];
      academicLevelCompletionOrders.forEach((alCompletionOrder: AcademicLevelCompletionOrder) => {
        if (alCompletionOrder.completionOrderId === event.previousIndex) {
          alCompletionOrder.completionOrderId = event.currentIndex;
        } else if (alCompletionOrder.completionOrderId === event.currentIndex) {
          alCompletionOrder.completionOrderId = event.previousIndex;
        }
        this.academicLevelCompletionOrders.push(alCompletionOrder);
      });
      this.programForm.patchValue({
        academicLevelCompletionOrders: this.academicLevelCompletionOrders,
      }, {emitEvent: false});
    }
  }

  onDragItemStarted(event: CdkDragStart) {
    this.changeZIndexValue('1000');
  }

  changeZIndexValue(value: string) {
   const el: HTMLDivElement = this.element.nativeElement.parentElement.parentElement.parentElement.parentElement;
    el.style.zIndex = value;
  }
  loadForm() {
    this.programForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required ],
      code: ['', Validators.required ],
     //academicLevelCompletionOrders: [null, Validators.required ],
      description: [''],
    });
  }

  loadProgram() {
    this.programService.getProgram(parseInt(this.id, 10)).subscribe((program: Program) => {
      if (program) {
        this.program = program;
        this.patchProgram();
      }
    });
  }

  checkedChange(checked: boolean, academicLevel: AcademicLevel, completionOrder: number) {
    this.academicLevelCompletionOrder = {
      completionOrderId: completionOrder,
      academicLevelId: academicLevel.id,
    };

    if (checked) {
      this.academicLevelCompletionOrders.push(this.academicLevelCompletionOrder);
    } else {
      for (let i = 0; i < this.academicLevelCompletionOrders.length; i++) {
        if ( this.academicLevelCompletionOrders[i].academicLevelId === academicLevel.id) {
          this.academicLevelCompletionOrders.splice(i, 1);
        }
     }
    }
    this.programForm.patchValue({
      academicLevelCompletionOrders: this.academicLevelCompletionOrders,
    }, {emitEvent: false});
  }

  loadAcademicLevels() {
    this.isLoading = true;
    this.academicLevelService.loadAcademicLevelList()
      .subscribe((academicLevels: AcademicLevel[]) => {
        this.isLoading = false;
        if (academicLevels) {
          this.academicLevels = academicLevels;
        }
      });
  }

  loadCompletionOrders() {
    this.isLoading = true;
    this.completionOrderService.getCompletionOrders()
      .subscribe((completionOrders: CompletionOrder[]) => {
        this.isLoading = false;
        if (completionOrders) {
          this.completionOrders = completionOrders;
        }
      });
  }

  getParam(): QueryParam {
    return {
      page: 1,
      pageSize: 10,
      orderby: 'DESC',
      status: '',
      search: '',
      fieldName: '',
      userType: 'program',
    };
  }
}
