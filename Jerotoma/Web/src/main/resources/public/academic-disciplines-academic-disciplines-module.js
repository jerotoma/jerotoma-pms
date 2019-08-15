(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["academic-disciplines-academic-disciplines-module"],{

/***/ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.html":
/*!**************************************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.html ***!
  \**************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class='row'>\n  <div class=\"col-md-12 col-lg-12 col-xxxl-12\">\n      <form  autocomplete=\"off\" [formGroup]=\"academicDisciplineForm\" (ngSubmit)=\"onSubmit()\">\n          <nb-card size=\"giant\" status=\"primary\">\n            <nb-card-header>{{ title }}</nb-card-header>\n            <nb-card-body>\n              <div class='row'>\n                  <div class='col-md-12'>\n\n                  </div>\n              </div>\n              <div class=\"row\">\n                  <div class=\"col-md-6\">\n                      <div class=\"form-control-group\">\n                          <label for=\"academic-discipline-name\" class=\"label\">Academic Discipline Name</label>\n                          <input\n                              type=\"text\"\n                              nbInput fullWidth\n                              required\n                              id=\"academic-discipline-name\"\n                              formControlName=\"name\"\n                              placeholder=\"eg. Computer Engineering\">\n                        </div>\n                  </div>\n                  <div class=\"col-md-6\">\n                      <div class=\"form-control-group\">\n                        <label for=\"academic-discipline-code\" class=\"label\">Academic Discipline code</label>\n                        <input\n                            type=\"text\"\n                            nbInput fullWidth\n                            autocomplete=\"off\"\n                            required\n                            id=\"academic-discipline-code\"\n                            formControlName=\"code\"\n                            placeholder=\"eg.  CN345\">\n                      </div>\n                  </div>\n              </div>\n              <div class=\"row\">\n                <div class=\"col-md-12\">\n                    <div class=\"form-control-group\">\n                        <label for=\"academic-discipline-description\" class=\"label\">Academic Discipline Description</label>\n                        <textarea\n                            nbInput\n                            fullWidth\n                            formControlName=\"description\"\n                            placeholder=\"\">\n                        </textarea>\n                        <!-- <app-mce-editor (editorKeyup)=getDescriptionContent($event) ></app-mce-editor> -->\n                      </div>\n                </div>\n            </div>\n            </nb-card-body>\n            <nb-card-footer>\n              <button class='push-right' [disabled]=\"!academicDisciplineForm.valid\" type=\"submit\" nbButton hero status=\"success\">Submit</button>\n              <button class='push-right' nbButton hero status=\"danger\" (click)=\"dismiss()\">Cancel</button>\n            </nb-card-footer>\n          </nb-card>\n      </form>\n  </div>\n</div>\n\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.html":
/*!**************************************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.html ***!
  \**************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nb-card>\n  <nb-card-body>\n      <nb-alert outline=\"danger\">\n         Are you sure you want to delete {{name}} position\n      </nb-alert>\n  </nb-card-body>\n  <nb-card-footer>\n    <button class='push-right' (click)=\"onConfirmed()\" nbButton hero status=\"danger\">Yes</button>\n    <button class='push-right' nbButton hero status=\"success\" (click)=\"dismiss()\">No, Thank you</button>\n  </nb-card-footer>\n</nb-card>\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.html":
/*!***********************************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.html ***!
  \***********************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nb-card class=\"col-md-12 col-lg-12 col-xxxl-12\">\n    <nb-card-header> {{title}} <button class=\"push-right\" nbButton hero status='success' (click)=\"open()\">Add New Academic Discipline</button></nb-card-header>\n    <nb-card-body>\n        <div class=\"mat-elevation-z0\">\n            <table mat-table [dataSource]=\"dataSource\" matSort>\n              <!-- id Column -->\n              <ng-container matColumnDef=\"id\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> No. </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.id}} </td>\n              </ng-container>\n              <!-- Name Column -->\n              <ng-container matColumnDef=\"name\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Name </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.name}} </td>\n              </ng-container>\n               <!-- code Column -->\n              <ng-container matColumnDef=\"code\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Code </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.code}} </td>\n              </ng-container>\n              <!-- description Column -->\n              <ng-container matColumnDef=\"description\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Description </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.description}} </td>\n              </ng-container>\n               <!-- action Column -->\n              <ng-container matColumnDef=\"action\">\n                <th mat-header-cell *matHeaderCellDef>Action</th>\n                <td mat-cell *matCellDef=\"let element\">\n                    <button mat-icon-button [matMenuTriggerFor]=\"menu\">\n                      <mat-icon>more_vert</mat-icon>\n                    </button>\n                    <mat-menu #menu=\"matMenu\" xPosition=\"before\">\n                      <button mat-menu-item (click)=\"edit(element)\"><mat-icon>edit</mat-icon> Edit</button>\n                      <button mat-menu-item (click)=\"delete(element)\"><mat-icon>delete</mat-icon> Delete</button>\n                    </mat-menu>\n                </td>\n              </ng-container>\n              <tr mat-header-row *matHeaderRowDef=\"displayedColumns\"></tr>\n              <tr mat-row *matRowDef=\"let row; columns: displayedColumns;\"></tr>\n            </table>\n            <mat-paginator\n                  [pageSize]='param.pageSize'\n                  [length]='totalNumberOfItems'\n                  [hidePageSize]='hidePageSize'\n                  [pageIndex]=0\n                  (page)='onPageChange($event)'\n                  [pageSizeOptions]=\"pageSizeOptions\"\n                  showFirstLastButtons>\n            </mat-paginator>\n          </div>\n    </nb-card-body>\n  </nb-card>\n"

/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/academic-disciplines-routing.module.ts":
/*!***************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/academic-disciplines-routing.module.ts ***!
  \***************************************************************************************************/
/*! exports provided: AcademicDisciplinesRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AcademicDisciplinesRoutingModule", function() { return AcademicDisciplinesRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _academic_disciplines_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./academic-disciplines.component */ "./src/app/features/system-setup/academic-disciplines/academic-disciplines.component.ts");
/* harmony import */ var _discipline_view_academic_disciplines_view_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./discipline-view/academic-disciplines-view.component */ "./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.ts");





const routes = [{
        path: '',
        component: _academic_disciplines_component__WEBPACK_IMPORTED_MODULE_3__["AcademicDisciplinesComponent"],
        children: [
            {
                path: '',
                component: _discipline_view_academic_disciplines_view_component__WEBPACK_IMPORTED_MODULE_4__["AcademicDisciplinesViewComponent"],
            },
        ],
    }];
let AcademicDisciplinesRoutingModule = class AcademicDisciplinesRoutingModule {
};
AcademicDisciplinesRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forChild(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]],
    })
], AcademicDisciplinesRoutingModule);



/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/academic-disciplines.component.scss":
/*!************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/academic-disciplines.component.scss ***!
  \************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9hY2FkZW1pYy1kaXNjaXBsaW5lcy9hY2FkZW1pYy1kaXNjaXBsaW5lcy5jb21wb25lbnQuc2NzcyJ9 */"

/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/academic-disciplines.component.ts":
/*!**********************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/academic-disciplines.component.ts ***!
  \**********************************************************************************************/
/*! exports provided: AcademicDisciplinesComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AcademicDisciplinesComponent", function() { return AcademicDisciplinesComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");


let AcademicDisciplinesComponent = class AcademicDisciplinesComponent {
};
AcademicDisciplinesComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-academic-disciplines',
        template: `<router-outlet></router-outlet>`,
        styles: [__webpack_require__(/*! ./academic-disciplines.component.scss */ "./src/app/features/system-setup/academic-disciplines/academic-disciplines.component.scss")]
    })
], AcademicDisciplinesComponent);



/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/academic-disciplines.module.ts":
/*!*******************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/academic-disciplines.module.ts ***!
  \*******************************************************************************************/
/*! exports provided: AcademicDisciplinesModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AcademicDisciplinesModule", function() { return AcademicDisciplinesModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm2015/table.js");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm2015/paginator.js");
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/icon */ "./node_modules/@angular/material/esm2015/icon.js");
/* harmony import */ var _angular_material_menu__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/menu */ "./node_modules/@angular/material/esm2015/menu.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var _academic_disciplines_routing_module__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./academic-disciplines-routing.module */ "./src/app/features/system-setup/academic-disciplines/academic-disciplines-routing.module.ts");
/* harmony import */ var _academic_disciplines_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./academic-disciplines.component */ "./src/app/features/system-setup/academic-disciplines/academic-disciplines.component.ts");
/* harmony import */ var _discipline_view_academic_disciplines_view_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./discipline-view/academic-disciplines-view.component */ "./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.ts");
/* harmony import */ var _discipline_create_academic_discipline_create_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./discipline-create/academic-discipline-create.component */ "./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.ts");
/* harmony import */ var _discipline_delete_academic_discipline_delete_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./discipline-delete/academic-discipline-delete.component */ "./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.ts");













const COMPONENTS = [
    _academic_disciplines_component__WEBPACK_IMPORTED_MODULE_9__["AcademicDisciplinesComponent"],
    _discipline_view_academic_disciplines_view_component__WEBPACK_IMPORTED_MODULE_10__["AcademicDisciplinesViewComponent"],
    _discipline_create_academic_discipline_create_component__WEBPACK_IMPORTED_MODULE_11__["AcademicDisciplineCreateComponent"],
    _discipline_delete_academic_discipline_delete_component__WEBPACK_IMPORTED_MODULE_12__["AcademicDisciplineDeleteComponent"],
];
const ENTRY_COMPONENTS = [
    _discipline_create_academic_discipline_create_component__WEBPACK_IMPORTED_MODULE_11__["AcademicDisciplineCreateComponent"],
    _discipline_delete_academic_discipline_delete_component__WEBPACK_IMPORTED_MODULE_12__["AcademicDisciplineDeleteComponent"],
];
const MODULES = [
    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormsModule"],
    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["ReactiveFormsModule"],
    _academic_disciplines_routing_module__WEBPACK_IMPORTED_MODULE_8__["AcademicDisciplinesRoutingModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbButtonModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbWindowModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbCardModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbInputModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbIconModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbAlertModule"],
    _angular_material_table__WEBPACK_IMPORTED_MODULE_3__["MatTableModule"],
    _angular_material_paginator__WEBPACK_IMPORTED_MODULE_4__["MatPaginatorModule"],
    _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__["MatIconModule"],
    _angular_material_menu__WEBPACK_IMPORTED_MODULE_6__["MatMenuModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbDialogModule"].forRoot(),
];
const SERVICES = [];
let AcademicDisciplinesModule = class AcademicDisciplinesModule {
};
AcademicDisciplinesModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
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
], AcademicDisciplinesModule);



/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.scss":
/*!************************************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.scss ***!
  \************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "nb-checkbox {\n  margin-bottom: 1rem; }\n\n.form-inline [fullWidth] {\n  flex: 1; }\n\n.form-inline > * {\n  margin: 0 1.5rem 1.5rem 0; }\n\nnb-card.inline-form-card nb-card-body {\n  padding-bottom: 0; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy9zeXN0ZW0tc2V0dXAvYWNhZGVtaWMtZGlzY2lwbGluZXMvZGlzY2lwbGluZS1jcmVhdGUvYWNhZGVtaWMtZGlzY2lwbGluZS1jcmVhdGUuY29tcG9uZW50LnNjc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxtQkFBbUIsRUFBQTs7QUFHckI7RUFDRSxPQUFPLEVBQUE7O0FBR1Q7RUFDRSx5QkFBeUIsRUFBQTs7QUFHM0I7RUFDRSxpQkFBaUIsRUFBQSIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9hY2FkZW1pYy1kaXNjaXBsaW5lcy9kaXNjaXBsaW5lLWNyZWF0ZS9hY2FkZW1pYy1kaXNjaXBsaW5lLWNyZWF0ZS5jb21wb25lbnQuc2NzcyIsInNvdXJjZXNDb250ZW50IjpbIm5iLWNoZWNrYm94IHtcbiAgbWFyZ2luLWJvdHRvbTogMXJlbTtcbn1cblxuLmZvcm0taW5saW5lIFtmdWxsV2lkdGhdIHtcbiAgZmxleDogMTtcbn1cblxuLmZvcm0taW5saW5lID4gKiB7XG4gIG1hcmdpbjogMCAxLjVyZW0gMS41cmVtIDA7XG59XG5cbm5iLWNhcmQuaW5saW5lLWZvcm0tY2FyZCBuYi1jYXJkLWJvZHkge1xuICBwYWRkaW5nLWJvdHRvbTogMDtcbn1cblxuIl19 */"

/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.ts":
/*!**********************************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.ts ***!
  \**********************************************************************************************************************/
/*! exports provided: AcademicDisciplineCreateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AcademicDisciplineCreateComponent", function() { return AcademicDisciplineCreateComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var app_services_academic_disciplines_academic_discipline_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! app/services/academic-disciplines/academic-discipline.service */ "./src/app/services/academic-disciplines/academic-discipline.service.ts");





let AcademicDisciplineCreateComponent = class AcademicDisciplineCreateComponent {
    constructor(academicDisciplineService, formBuilder, ref) {
        this.academicDisciplineService = academicDisciplineService;
        this.formBuilder = formBuilder;
        this.ref = ref;
        this.action = 'create';
        this.onCreationSuccess = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.name = '';
        this.code = '';
        this.id = '0';
        this.description = '';
        this.showMessage = {
            error: false,
            success: false,
            message: '',
        };
        this.listDisplay = 'none';
    }
    ngOnInit() {
        this.loadForm();
        if (this.action === 'edit') {
            this.patchPosition();
        }
    }
    patchPosition() {
        this.academicDisciplineForm.patchValue({
            name: this.name,
            description: this.description,
            code: this.code,
            id: parseInt(this.id, 10),
        });
    }
    dismiss() {
        this.ref.close();
    }
    onSubmit() {
        this.academicDiscipline = this.academicDisciplineForm.value;
        this.showMessage.success = false;
        this.showMessage.error = false;
        if (this.action === 'edit') {
            this.updatePosition();
        }
        else {
            this.academicDisciplineService.createAcademicDiscipline(this.academicDiscipline)
                .subscribe((result) => {
                const resp = result;
                const data = resp.body;
                const status = resp.status;
                if (status !== null && status === 200) {
                    this.showMessage.success = true;
                    this.showMessage.error = false;
                    this.showMessage.message = data ? data.message : '';
                    this.academicDisciplineForm.reset();
                    this.dismiss();
                }
                else {
                    this.showMessage.success = false;
                    this.showMessage.error = true;
                    this.showMessage.message = data ? data.message : '';
                }
            }, error => {
                this.showMessage.error = true;
                this.showMessage.success = false;
                this.showMessage.message = error ? error.error.message : '';
            });
        }
    }
    updatePosition() {
        this.academicDisciplineService.updateAcademicDiscipline(this.academicDiscipline)
            .subscribe((result) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.showMessage.success = true;
                this.showMessage.error = false;
                this.showMessage.message = data ? data.message : '';
                this.academicDisciplineForm.reset();
                this.dismiss();
            }
            else {
                this.showMessage.success = false;
                this.showMessage.error = true;
                this.showMessage.message = data ? data.message : '';
            }
        }, error => {
            this.showMessage.error = true;
            this.showMessage.success = false;
            this.showMessage.message = error ? error.error.message : '';
        });
    }
    getDescriptionContent(description) {
        if (description) {
            this.academicDisciplineForm.patchValue({
                description: description,
            });
        }
    }
    loadForm() {
        this.academicDisciplineForm = this.formBuilder.group({
            id: [null],
            name: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            code: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            description: [''],
        });
    }
    getParam() {
        return {
            page: 1,
            pageSize: 10,
            orderby: 'DESC',
            status: '',
            search: '',
            fieldName: '',
            userType: 'academicDiscipline',
        };
    }
};
AcademicDisciplineCreateComponent.ctorParameters = () => [
    { type: app_services_academic_disciplines_academic_discipline_service__WEBPACK_IMPORTED_MODULE_4__["AcademicDisciplineService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogRef"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], AcademicDisciplineCreateComponent.prototype, "title", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], AcademicDisciplineCreateComponent.prototype, "action", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
], AcademicDisciplineCreateComponent.prototype, "onCreationSuccess", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], AcademicDisciplineCreateComponent.prototype, "name", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], AcademicDisciplineCreateComponent.prototype, "code", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], AcademicDisciplineCreateComponent.prototype, "id", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], AcademicDisciplineCreateComponent.prototype, "description", void 0);
AcademicDisciplineCreateComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-academic-discipline-create',
        template: __webpack_require__(/*! raw-loader!./academic-discipline-create.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.html"),
        styles: [__webpack_require__(/*! ./academic-discipline-create.component.scss */ "./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_academic_disciplines_academic_discipline_service__WEBPACK_IMPORTED_MODULE_4__["AcademicDisciplineService"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogRef"]])
], AcademicDisciplineCreateComponent);



/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.scss":
/*!************************************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.scss ***!
  \************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "nb-alert {\n  margin-bottom: 0; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy9zeXN0ZW0tc2V0dXAvYWNhZGVtaWMtZGlzY2lwbGluZXMvZGlzY2lwbGluZS1kZWxldGUvYWNhZGVtaWMtZGlzY2lwbGluZS1kZWxldGUuY29tcG9uZW50LnNjc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxnQkFBZ0IsRUFBQSIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9hY2FkZW1pYy1kaXNjaXBsaW5lcy9kaXNjaXBsaW5lLWRlbGV0ZS9hY2FkZW1pYy1kaXNjaXBsaW5lLWRlbGV0ZS5jb21wb25lbnQuc2NzcyIsInNvdXJjZXNDb250ZW50IjpbIm5iLWFsZXJ0IHtcbiAgbWFyZ2luLWJvdHRvbTogMDtcbn1cbiJdfQ== */"

/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.ts":
/*!**********************************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.ts ***!
  \**********************************************************************************************************************/
/*! exports provided: AcademicDisciplineDeleteComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AcademicDisciplineDeleteComponent", function() { return AcademicDisciplineDeleteComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var app_services_academic_disciplines_academic_discipline_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/services/academic-disciplines/academic-discipline.service */ "./src/app/services/academic-disciplines/academic-discipline.service.ts");




let AcademicDisciplineDeleteComponent = class AcademicDisciplineDeleteComponent {
    constructor(academicDisciplineService, ref) {
        this.academicDisciplineService = academicDisciplineService;
        this.ref = ref;
        this.positionId = '0';
        this.title = '';
        this.action = '';
        this.name = '';
        this.confirmed = false;
        this.showMessage = {
            error: false,
            success: false,
            message: '',
        };
    }
    ngOnInit() {
        this.confirmed = this.action === 'delete';
    }
    deleteAcademicDiscipline() {
        if (this.confirmed) {
            this.academicDisciplineService.deleteAcademicDiscipline(parseInt(this.positionId, 10))
                .subscribe((result) => {
                const resp = result;
                const data = resp.body;
                const status = resp.status;
                if (status !== null && status === 200) {
                    this.dismiss();
                }
                else {
                    this.showMessage.success = false;
                    this.showMessage.error = true;
                    this.showMessage.message = data ? data.message : '';
                }
            }, error => {
                this.showMessage.error = true;
                this.showMessage.success = false;
                this.showMessage.message = error ? error.error.message : '';
            });
        }
    }
    dismiss() {
        this.ref.close();
    }
    onConfirmed() {
        this.confirmed = true;
        this.deleteAcademicDiscipline();
    }
};
AcademicDisciplineDeleteComponent.ctorParameters = () => [
    { type: app_services_academic_disciplines_academic_discipline_service__WEBPACK_IMPORTED_MODULE_3__["AcademicDisciplineService"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogRef"] }
];
AcademicDisciplineDeleteComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-academic-discipline-delete',
        template: __webpack_require__(/*! raw-loader!./academic-discipline-delete.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.html"),
        styles: [__webpack_require__(/*! ./academic-discipline-delete.component.scss */ "./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_academic_disciplines_academic_discipline_service__WEBPACK_IMPORTED_MODULE_3__["AcademicDisciplineService"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogRef"]])
], AcademicDisciplineDeleteComponent);



/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.scss":
/*!*********************************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.scss ***!
  \*********************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "table {\n  width: 100%; }\n\nbutton {\n  cursor: pointer;\n  margin: 0;\n  border: none; }\n\nbutton:focus {\n  outline: none; }\n\nbutton:hover {\n  background-color: rgba(0, 0, 0, 0.03); }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy9zeXN0ZW0tc2V0dXAvYWNhZGVtaWMtZGlzY2lwbGluZXMvZGlzY2lwbGluZS12aWV3L2FjYWRlbWljLWRpc2NpcGxpbmVzLXZpZXcuY29tcG9uZW50LnNjc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxXQUFXLEVBQUE7O0FBRWI7RUFDRSxlQUFlO0VBQ2YsU0FBUztFQUNULFlBQVksRUFBQTs7QUFHZDtFQUNFLGFBQWEsRUFBQTs7QUFFZjtFQUNFLHFDQUFxQyxFQUFBIiwiZmlsZSI6InNyYy9hcHAvZmVhdHVyZXMvc3lzdGVtLXNldHVwL2FjYWRlbWljLWRpc2NpcGxpbmVzL2Rpc2NpcGxpbmUtdmlldy9hY2FkZW1pYy1kaXNjaXBsaW5lcy12aWV3LmNvbXBvbmVudC5zY3NzIiwic291cmNlc0NvbnRlbnQiOlsidGFibGUge1xuICB3aWR0aDogMTAwJTtcbn1cbmJ1dHRvbiB7XG4gIGN1cnNvcjogcG9pbnRlcjtcbiAgbWFyZ2luOiAwO1xuICBib3JkZXI6IG5vbmU7XG59XG5cbmJ1dHRvbjpmb2N1cyB7XG4gIG91dGxpbmU6IG5vbmU7XG59XG5idXR0b246aG92ZXIge1xuICBiYWNrZ3JvdW5kLWNvbG9yOiByZ2JhKDAsIDAsIDAsIDAuMDMpOztcbn1cbiJdfQ== */"

/***/ }),

/***/ "./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.ts":
/*!*******************************************************************************************************************!*\
  !*** ./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.ts ***!
  \*******************************************************************************************************************/
/*! exports provided: AcademicDisciplinesViewComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AcademicDisciplinesViewComponent", function() { return AcademicDisciplinesViewComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm2015/paginator.js");
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/sort */ "./node_modules/@angular/material/esm2015/sort.js");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm2015/table.js");
/* harmony import */ var _discipline_create_academic_discipline_create_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../discipline-create/academic-discipline-create.component */ "./src/app/features/system-setup/academic-disciplines/discipline-create/academic-discipline-create.component.ts");
/* harmony import */ var _discipline_delete_academic_discipline_delete_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../discipline-delete/academic-discipline-delete.component */ "./src/app/features/system-setup/academic-disciplines/discipline-delete/academic-discipline-delete.component.ts");
/* harmony import */ var app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! app/services/academic-disciplines */ "./src/app/services/academic-disciplines/index.ts");









/**
 * @title Table with pagination
 */
let AcademicDisciplinesViewComponent = class AcademicDisciplinesViewComponent {
    constructor(academicDisciplineService, dialogService) {
        this.academicDisciplineService = academicDisciplineService;
        this.dialogService = dialogService;
        this.param = {
            page: 1,
            pageSize: 10,
            orderby: 'DESC',
            status: '',
            search: '',
            fieldName: '',
            userType: 'teacher',
        };
        this.title = 'List of Academic Discipline';
        this.hidePageSize = false;
        this.totalNumberOfItems = 20;
        this.pageSizeOptions = [10, 20, 30, 50, 70, 100];
        this.displayedColumns = ['id', 'name', 'code', 'description', 'action'];
        this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_5__["MatTableDataSource"]();
    }
    ngOnInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.loadAcademicDisciplines();
    }
    open() {
        this.dialogService.open(_discipline_create_academic_discipline_create_component__WEBPACK_IMPORTED_MODULE_6__["AcademicDisciplineCreateComponent"], {
            context: {
                title: 'Add New Academic Discipline',
                action: 'create',
            },
        }).onClose.subscribe(_data => {
            this.loadAcademicDisciplines();
        });
    }
    loadAcademicDisciplines() {
        this.academicDisciplineService.getAcademicDisciplines(this.param)
            .subscribe((result) => {
            const resp = result;
            const status = resp.status;
            if (status !== null && status === 200 && resp.body) {
                const data = resp.body.data;
                this.totalNumberOfItems = data.count;
                this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_5__["MatTableDataSource"](data.academicDisciplines);
            }
        }, error => {
        });
    }
    edit(academicDiscipline) {
        this.dialogService.open(_discipline_create_academic_discipline_create_component__WEBPACK_IMPORTED_MODULE_6__["AcademicDisciplineCreateComponent"], {
            context: {
                title: 'Edit Academic Discipline',
                action: 'edit',
                id: academicDiscipline.id.toString(),
                code: academicDiscipline.code,
                name: academicDiscipline.name,
                description: academicDiscipline.description,
            },
        }).onClose.subscribe(_data => {
            this.loadAcademicDisciplines();
        });
    }
    delete(academicDiscipline) {
        this.dialogService.open(_discipline_delete_academic_discipline_delete_component__WEBPACK_IMPORTED_MODULE_7__["AcademicDisciplineDeleteComponent"], {
            context: {
                title: 'Delete Academic Discipline',
                action: 'delete',
                positionId: academicDiscipline.id.toString(),
                name: academicDiscipline.name,
            },
        }).onClose.subscribe(_data => {
            this.loadAcademicDisciplines();
        });
    }
    onPageChange(pageEvent) {
        this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
        this.param.pageSize = pageEvent.pageSize;
        this.loadAcademicDisciplines();
    }
};
AcademicDisciplinesViewComponent.ctorParameters = () => [
    { type: app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_8__["AcademicDisciplineService"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogService"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"])
], AcademicDisciplinesViewComponent.prototype, "paginator", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_sort__WEBPACK_IMPORTED_MODULE_4__["MatSort"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_sort__WEBPACK_IMPORTED_MODULE_4__["MatSort"])
], AcademicDisciplinesViewComponent.prototype, "sort", void 0);
AcademicDisciplinesViewComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-academic-disciplines-view',
        template: __webpack_require__(/*! raw-loader!./academic-disciplines-view.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.html"),
        styles: [__webpack_require__(/*! ./academic-disciplines-view.component.scss */ "./src/app/features/system-setup/academic-disciplines/discipline-view/academic-disciplines-view.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_8__["AcademicDisciplineService"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogService"]])
], AcademicDisciplinesViewComponent);



/***/ })

}]);
//# sourceMappingURL=academic-disciplines-academic-disciplines-module.js.map