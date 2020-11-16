import { Component, OnInit, Input, Output, EventEmitter, ElementRef } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import {CdkDragDrop, moveItemInArray, CdkDragStart} from '@angular/cdk/drag-drop';

import { NbDialogRef } from '@nebular/theme';
import { Program, AcademicLevel, AcademicLevelCompletionOrder,  CompletionOrder} from 'app/models';
import {
  ProgramService,
  AcademicLevelService,
  CompletionOrderService,
  ModalService,
} from 'app/services';
import { QueryParam, OPEN_CLOSE_ANIMATION } from 'app/utils';
import { Element } from '@angular/compiler';

@Component({
  selector: 'app-program-edit',
  animations: OPEN_CLOSE_ANIMATION,
  templateUrl: `./program-edit.component.html`,
  styleUrls: [`./program-edit.component.scss`],
})
export class ProgramEditComponent implements OnInit {
  @Output() onCreationSuccess = new EventEmitter();
  @Input('program')  program: Program;
  programForm: FormGroup;
  isAddMore: boolean = false;

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
    protected ref: NbDialogRef<ProgramEditComponent>) {}

  ngOnInit() {
    this.loadForm();
    this.loadCompletionOrders();
    this.patchProgram();
  }
  patchProgram() {
    this.programForm.patchValue({
      name: this.program.name,
      code: this.program.code,
      academicLevelIDs: this.program.academicLevelIDs,
      description: this.program.description,
      id: this.program.id,
    });
    this.pushAcademicLevels(this.program.academicLevels);
  }
  dismiss() {
    this.ref.close();
  }

  onSubmit() {
    this.isSubmitting = true;
    this.program = this.programForm.value;
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

  loadForm() {
    this.programForm = this.formBuilder.group({
      id: [null],
      name: ['', Validators.required ],
      code: ['', Validators.required ],
      academicLevelIDs: ['', Validators.required ],
      academicLevelCompletionOrders: [null, Validators.required ],
      description: [''],
    });
  }

  onDragDropChange(event: CdkDragDrop<string[]>) {
    this.changeZIndexValue('1040');
    moveItemInArray(this.academicLevels, event.previousIndex, event.currentIndex);
    window.console.log(this.academicLevels);
  }

  onDragItemStarted(event: CdkDragStart) {
    this.changeZIndexValue('1000');
  }

  changeZIndexValue(value: string) {
   const el: HTMLDivElement = this.element.nativeElement.parentElement.parentElement.parentElement.parentElement;
    el.style.zIndex = value;
  }

  onCompletionOrderSelectedChange(completionOrderId: number, academicLevel: AcademicLevel) {
    this.academicLevelCompletionOrder = {
      completionOrderId: completionOrderId,
      academicLevelId: academicLevel.id,
    };

    if (!this.academicLevelCompletionOrders.find(academicLevelCompletionOrder => academicLevelCompletionOrder.academicLevelId === academicLevel.id &&
      academicLevelCompletionOrder.completionOrderId === completionOrderId)) {
      this.academicLevelCompletionOrders.push(this.academicLevelCompletionOrder);
      this.programForm.patchValue({
        academicLevelCompletionOrders: this.academicLevelCompletionOrders,
      }, {emitEvent: false});
    }
  }


  removeAcademicLevel(event: any, academicLevel: AcademicLevel, isRemoveLevel: boolean) {
    event.preventDefault();
    event.stopPropagation();
    if (isRemoveLevel) {
      this.programService.removeAcademicLevelFromProgram(this.program.id, academicLevel.id).subscribe((success: boolean) => {
        if (success) {
          this.checkedChange(false, academicLevel);
            for (let i = 0; i < this.program.academicLevels.length; i++) {
              if ( this.program.academicLevels[i].id === academicLevel.id) {
                this.program.academicLevels.splice(i, 1);
              }
          }
        }
      });
    }
  }

  checkedChange(checked: boolean, academicLevel: AcademicLevel) {
    if (checked) {
      this.academicLevelIDs.push(academicLevel.id);
    } else {
      for (let i = 0; i < this.academicLevelIDs.length; i++) {
        if ( this.academicLevelIDs[i] === academicLevel.id) {
          this.academicLevelIDs.splice(i, 1);
        }
     }
    }
    this.programForm.patchValue({
      academicLevelIDs: this.academicLevelIDs,
    });
  }

  addMoreAcademicLevel() {
    this.isAddMore = true;
    if (this.program) {
      this.loadUnAddedAcademicLevelByProgram(this.program.id);
    }
  }

  loadUnAddedAcademicLevelByProgram(programId: number) {
    this.isLoading = true;
    this.academicLevels = [];
    this.academicLevelService.loadUnAddedAcademicLevelByProgram(programId).subscribe((academicLevels: AcademicLevel[]) => {
      this.isLoading = false;
      if (academicLevels && academicLevels.length > 0) {
        this.academicLevels = academicLevels;
      } else {
        this.modalService.openSnackBar('No more level were found', 'success');
      }
    });
  }

  pushAcademicLevels(academicLevels: AcademicLevel[]) {
    this.academicLevelIDs  = [];
    academicLevels.forEach((academicLevel) => {
      this.academicLevelIDs.push(academicLevel.id);
    });
    this.programForm.patchValue({
      academicLevelIDs: this.academicLevelIDs,
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
}
