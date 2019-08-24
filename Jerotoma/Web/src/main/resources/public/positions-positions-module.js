(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["positions-positions-module"],{

/***/ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/positions/position-create/position-create.component.html":
/*!**************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/system-setup/positions/position-create/position-create.component.html ***!
  \**************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class='row'>\n  <div class=\"col-md-12 col-lg-12 col-xxxl-12\">\n      <form  autocomplete=\"off\" [formGroup]=\"positionForm\" (ngSubmit)=\"onSubmit()\">\n          <nb-card size=\"giant\" status=\"primary\">\n            <nb-card-header>{{ title }}</nb-card-header>\n            <nb-card-body>\n              <div class='row'>\n                  <div class='col-md-12'>\n\n                  </div>\n              </div>\n              <div class=\"row\">\n                  <div class=\"col-md-6\">\n                      <div class=\"form-control-group\">\n                          <label for=\"position-name\" class=\"label\">Position Name</label>\n                          <input\n                              type=\"text\"\n                              nbInput fullWidth\n                              required\n                              id=\"position-name\"\n                              formControlName=\"name\"\n                              placeholder=\"eg. Head Master\">\n                        </div>\n                  </div>\n                  <div class=\"col-md-6\">\n                      <div class=\"form-control-group\">\n                        <label for=\"position-employment-code\" class=\"label\">Position code</label>\n                        <input\n                            type=\"text\"\n                            nbInput fullWidth\n                            autocomplete=\"off\"\n                            required\n                            id=\"position-code\"\n                            formControlName=\"code\"\n                            placeholder=\"eg.  TC-465-678\">\n                      </div>\n                  </div>\n              </div>\n              <div class=\"row\">\n                <div class=\"col-md-12\">\n                    <div class=\"form-control-group\">\n                        <label for=\"position-description\" class=\"label\">Position Description</label>\n                        <textarea\n                            nbInput\n                            fullWidth\n                            formControlName=\"description\"\n                            placeholder=\"\">\n                        </textarea>\n                        <!-- <app-mce-editor (editorKeyup)=getDescriptionContent($event) ></app-mce-editor> -->\n                      </div>\n                </div>\n            </div>\n            </nb-card-body>\n            <nb-card-footer>\n              <button class='push-right' [disabled]=\"!positionForm.valid\" type=\"submit\" nbButton hero status=\"success\">Submit</button>\n              <button class='push-right' nbButton hero status=\"danger\" (click)=\"dismiss()\">Cancel</button>\n            </nb-card-footer>\n          </nb-card>\n      </form>\n  </div>\n</div>\n\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/positions/position-delete/position-delete.component.html":
/*!**************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/system-setup/positions/position-delete/position-delete.component.html ***!
  \**************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nb-card>\n  <nb-card-body>\n      <nb-alert outline=\"danger\">\n         Are you sure you want to delete {{name}} position\n      </nb-alert>\n  </nb-card-body>\n  <nb-card-footer>\n    <button class='push-right' (click)=\"onConfirmed()\" nbButton hero status=\"danger\">Yes</button>\n    <button class='push-right' nbButton hero status=\"success\" (click)=\"dismiss()\">No, Thank you</button>\n  </nb-card-footer>\n</nb-card>\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/positions/positions-view/positions-view.component.html":
/*!************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/system-setup/positions/positions-view/positions-view.component.html ***!
  \************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nb-card class=\"col-md-12 col-lg-12 col-xxxl-12\">\n    <nb-card-header> {{title}} <button class=\"push-right\" nbButton hero status='success' (click)=\"open()\">Add New Teacher</button></nb-card-header>\n    <nb-card-body>\n        <div class=\"mat-elevation-z0\">\n            <table mat-table [dataSource]=\"dataSource\" matSort>\n              <!-- id Column -->\n              <ng-container matColumnDef=\"id\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> No. </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.id}} </td>\n              </ng-container>\n              <!-- Name Column -->\n              <ng-container matColumnDef=\"name\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Name </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.name}} </td>\n              </ng-container>\n               <!-- code Column -->\n              <ng-container matColumnDef=\"code\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Code </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.code}} </td>\n              </ng-container>\n              <!-- description Column -->\n              <ng-container matColumnDef=\"description\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Description </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.description}} </td>\n              </ng-container>\n               <!-- action Column -->\n              <ng-container matColumnDef=\"action\">\n                <th mat-header-cell *matHeaderCellDef>Action</th>\n                <td mat-cell *matCellDef=\"let element\">\n                    <button mat-icon-button [matMenuTriggerFor]=\"menu\">\n                      <mat-icon>more_vert</mat-icon>\n                    </button>\n                    <mat-menu #menu=\"matMenu\" xPosition=\"before\">\n                      <button mat-menu-item (click)=\"edit(element)\"><mat-icon>edit</mat-icon> Edit</button>\n                      <button mat-menu-item (click)=\"delete(element)\"><mat-icon>delete</mat-icon> Delete</button>\n                    </mat-menu>\n                </td>\n              </ng-container>\n              <tr mat-header-row *matHeaderRowDef=\"displayedColumns\"></tr>\n              <tr mat-row *matRowDef=\"let row; columns: displayedColumns;\"></tr>\n            </table>\n            <mat-paginator\n                  [pageSize]='param.pageSize'\n                  [length]='totalNumberOfItems'\n                  [hidePageSize]='hidePageSize'\n                  [pageIndex]=0\n                  (page)='onPageChange($event)'\n                  [pageSizeOptions]=\"pageSizeOptions\"\n                  showFirstLastButtons>\n            </mat-paginator>\n          </div>\n    </nb-card-body>\n  </nb-card>\n"

/***/ }),

/***/ "./src/app/features/system-setup/positions/position-create/position-create.component.scss":
/*!************************************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/position-create/position-create.component.scss ***!
  \************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "nb-checkbox {\n  margin-bottom: 1rem; }\n\n.form-inline [fullWidth] {\n  flex: 1; }\n\n.form-inline > * {\n  margin: 0 1.5rem 1.5rem 0; }\n\nnb-card.inline-form-card nb-card-body {\n  padding-bottom: 0; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy9zeXN0ZW0tc2V0dXAvcG9zaXRpb25zL3Bvc2l0aW9uLWNyZWF0ZS9wb3NpdGlvbi1jcmVhdGUuY29tcG9uZW50LnNjc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxtQkFBbUIsRUFBQTs7QUFHckI7RUFDRSxPQUFPLEVBQUE7O0FBR1Q7RUFDRSx5QkFBeUIsRUFBQTs7QUFHM0I7RUFDRSxpQkFBaUIsRUFBQSIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9wb3NpdGlvbnMvcG9zaXRpb24tY3JlYXRlL3Bvc2l0aW9uLWNyZWF0ZS5jb21wb25lbnQuc2NzcyIsInNvdXJjZXNDb250ZW50IjpbIm5iLWNoZWNrYm94IHtcbiAgbWFyZ2luLWJvdHRvbTogMXJlbTtcbn1cblxuLmZvcm0taW5saW5lIFtmdWxsV2lkdGhdIHtcbiAgZmxleDogMTtcbn1cblxuLmZvcm0taW5saW5lID4gKiB7XG4gIG1hcmdpbjogMCAxLjVyZW0gMS41cmVtIDA7XG59XG5cbm5iLWNhcmQuaW5saW5lLWZvcm0tY2FyZCBuYi1jYXJkLWJvZHkge1xuICBwYWRkaW5nLWJvdHRvbTogMDtcbn1cblxuIl19 */"

/***/ }),

/***/ "./src/app/features/system-setup/positions/position-create/position-create.component.ts":
/*!**********************************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/position-create/position-create.component.ts ***!
  \**********************************************************************************************/
/*! exports provided: PositionCreateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PositionCreateComponent", function() { return PositionCreateComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var app_services_positions__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! app/services/positions */ "./src/app/services/positions/index.ts");





let PositionCreateComponent = class PositionCreateComponent {
    constructor(positionService, formBuilder, ref) {
        this.positionService = positionService;
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
        this.positionForm.patchValue({
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
        this.position = this.positionForm.value;
        this.showMessage.success = false;
        this.showMessage.error = false;
        if (this.action === 'edit') {
            this.updatePosition();
        }
        else {
            this.positionService.createPosition(this.position)
                .subscribe((result) => {
                const resp = result;
                const data = resp.body;
                const status = resp.status;
                if (status !== null && status === 200) {
                    this.showMessage.success = true;
                    this.showMessage.error = false;
                    this.showMessage.message = data ? data.message : '';
                    this.positionForm.reset();
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
        this.positionService.updatePosition(this.position)
            .subscribe((result) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.showMessage.success = true;
                this.showMessage.error = false;
                this.showMessage.message = data ? data.message : '';
                this.positionForm.reset();
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
            this.positionForm.patchValue({
                description: description,
            });
        }
    }
    loadForm() {
        this.positionForm = this.formBuilder.group({
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
            userType: 'position',
        };
    }
};
PositionCreateComponent.ctorParameters = () => [
    { type: app_services_positions__WEBPACK_IMPORTED_MODULE_4__["PositionService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogRef"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], PositionCreateComponent.prototype, "title", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], PositionCreateComponent.prototype, "action", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
], PositionCreateComponent.prototype, "onCreationSuccess", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], PositionCreateComponent.prototype, "name", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], PositionCreateComponent.prototype, "code", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], PositionCreateComponent.prototype, "id", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], PositionCreateComponent.prototype, "description", void 0);
PositionCreateComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-position-create',
        template: __webpack_require__(/*! raw-loader!./position-create.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/positions/position-create/position-create.component.html"),
        styles: [__webpack_require__(/*! ./position-create.component.scss */ "./src/app/features/system-setup/positions/position-create/position-create.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_positions__WEBPACK_IMPORTED_MODULE_4__["PositionService"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogRef"]])
], PositionCreateComponent);



/***/ }),

/***/ "./src/app/features/system-setup/positions/position-delete/position-delete.component.scss":
/*!************************************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/position-delete/position-delete.component.scss ***!
  \************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "nb-alert {\n  margin-bottom: 0; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy9zeXN0ZW0tc2V0dXAvcG9zaXRpb25zL3Bvc2l0aW9uLWRlbGV0ZS9wb3NpdGlvbi1kZWxldGUuY29tcG9uZW50LnNjc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxnQkFBZ0IsRUFBQSIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9wb3NpdGlvbnMvcG9zaXRpb24tZGVsZXRlL3Bvc2l0aW9uLWRlbGV0ZS5jb21wb25lbnQuc2NzcyIsInNvdXJjZXNDb250ZW50IjpbIm5iLWFsZXJ0IHtcbiAgbWFyZ2luLWJvdHRvbTogMDtcbn1cbiJdfQ== */"

/***/ }),

/***/ "./src/app/features/system-setup/positions/position-delete/position-delete.component.ts":
/*!**********************************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/position-delete/position-delete.component.ts ***!
  \**********************************************************************************************/
/*! exports provided: PositionDeleteComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PositionDeleteComponent", function() { return PositionDeleteComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var app_services_positions__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/services/positions */ "./src/app/services/positions/index.ts");




let PositionDeleteComponent = class PositionDeleteComponent {
    constructor(positionService, ref) {
        this.positionService = positionService;
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
    deletePosition() {
        if (this.confirmed) {
            this.positionService.deletePosition(parseInt(this.positionId, 10))
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
        this.deletePosition();
    }
};
PositionDeleteComponent.ctorParameters = () => [
    { type: app_services_positions__WEBPACK_IMPORTED_MODULE_3__["PositionService"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogRef"] }
];
PositionDeleteComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-position-delete',
        template: __webpack_require__(/*! raw-loader!./position-delete.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/positions/position-delete/position-delete.component.html"),
        styles: [__webpack_require__(/*! ./position-delete.component.scss */ "./src/app/features/system-setup/positions/position-delete/position-delete.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_positions__WEBPACK_IMPORTED_MODULE_3__["PositionService"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogRef"]])
], PositionDeleteComponent);



/***/ }),

/***/ "./src/app/features/system-setup/positions/positions-routing.module.ts":
/*!*****************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/positions-routing.module.ts ***!
  \*****************************************************************************/
/*! exports provided: PositionsRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PositionsRoutingModule", function() { return PositionsRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _positions_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./positions.component */ "./src/app/features/system-setup/positions/positions.component.ts");
/* harmony import */ var _positions_view_positions_view_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./positions-view/positions-view.component */ "./src/app/features/system-setup/positions/positions-view/positions-view.component.ts");





const routes = [{
        path: '',
        component: _positions_component__WEBPACK_IMPORTED_MODULE_3__["PositionsComponent"],
        children: [
            {
                path: '',
                component: _positions_view_positions_view_component__WEBPACK_IMPORTED_MODULE_4__["PositionsViewComponent"],
            },
        ],
    }];
let PositionsRoutingModule = class PositionsRoutingModule {
};
PositionsRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forChild(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]],
    })
], PositionsRoutingModule);



/***/ }),

/***/ "./src/app/features/system-setup/positions/positions-view/positions-view.component.scss":
/*!**********************************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/positions-view/positions-view.component.scss ***!
  \**********************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "table {\n  width: 100%; }\n\nbutton {\n  cursor: pointer;\n  margin: 0;\n  border: none; }\n\nbutton:focus {\n  outline: none; }\n\nbutton:hover {\n  background-color: rgba(0, 0, 0, 0.03); }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy9zeXN0ZW0tc2V0dXAvcG9zaXRpb25zL3Bvc2l0aW9ucy12aWV3L3Bvc2l0aW9ucy12aWV3LmNvbXBvbmVudC5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsV0FBVyxFQUFBOztBQUViO0VBQ0UsZUFBZTtFQUNmLFNBQVM7RUFDVCxZQUFZLEVBQUE7O0FBR2Q7RUFDRSxhQUFhLEVBQUE7O0FBRWY7RUFDRSxxQ0FBcUMsRUFBQSIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9wb3NpdGlvbnMvcG9zaXRpb25zLXZpZXcvcG9zaXRpb25zLXZpZXcuY29tcG9uZW50LnNjc3MiLCJzb3VyY2VzQ29udGVudCI6WyJ0YWJsZSB7XG4gIHdpZHRoOiAxMDAlO1xufVxuYnV0dG9uIHtcbiAgY3Vyc29yOiBwb2ludGVyO1xuICBtYXJnaW46IDA7XG4gIGJvcmRlcjogbm9uZTtcbn1cblxuYnV0dG9uOmZvY3VzIHtcbiAgb3V0bGluZTogbm9uZTtcbn1cbmJ1dHRvbjpob3ZlciB7XG4gIGJhY2tncm91bmQtY29sb3I6IHJnYmEoMCwgMCwgMCwgMC4wMyk7O1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/features/system-setup/positions/positions-view/positions-view.component.ts":
/*!********************************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/positions-view/positions-view.component.ts ***!
  \********************************************************************************************/
/*! exports provided: PositionsViewComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PositionsViewComponent", function() { return PositionsViewComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm2015/paginator.js");
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/sort */ "./node_modules/@angular/material/esm2015/sort.js");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm2015/table.js");
/* harmony import */ var _position_create_position_create_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../position-create/position-create.component */ "./src/app/features/system-setup/positions/position-create/position-create.component.ts");
/* harmony import */ var _position_delete_position_delete_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../position-delete/position-delete.component */ "./src/app/features/system-setup/positions/position-delete/position-delete.component.ts");
/* harmony import */ var app_services_positions__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! app/services/positions */ "./src/app/services/positions/index.ts");









/**
 * @title Table with pagination
 */
let PositionsViewComponent = class PositionsViewComponent {
    constructor(positionService, dialogService) {
        this.positionService = positionService;
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
        this.title = 'List of Positions';
        this.hidePageSize = false;
        this.totalNumberOfItems = 20;
        this.pageSizeOptions = [10, 20, 30, 50, 70, 100];
        this.displayedColumns = ['id', 'name', 'code', 'description', 'action'];
        this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_5__["MatTableDataSource"]();
    }
    ngOnInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.loadPositions();
    }
    open() {
        this.dialogService.open(_position_create_position_create_component__WEBPACK_IMPORTED_MODULE_6__["PositionCreateComponent"], {
            context: {
                title: 'Add New Position',
                action: 'create',
            },
        }).onClose.subscribe(_data => {
            this.loadPositions();
        });
    }
    loadPositions() {
        this.positionService.getPositions(this.param)
            .subscribe((result) => {
            const resp = result;
            const status = resp.status;
            if (status !== null && status === 200 && resp.body) {
                const data = resp.body.data;
                this.totalNumberOfItems = data.count;
                this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_5__["MatTableDataSource"](data.positions);
            }
        }, error => {
        });
    }
    edit(position) {
        this.dialogService.open(_position_create_position_create_component__WEBPACK_IMPORTED_MODULE_6__["PositionCreateComponent"], {
            context: {
                title: 'Edit Position',
                action: 'edit',
                id: position.id.toString(),
                code: position.code,
                name: position.name,
                description: position.description,
            },
        }).onClose.subscribe(_data => {
            this.loadPositions();
        });
    }
    delete(position) {
        this.dialogService.open(_position_delete_position_delete_component__WEBPACK_IMPORTED_MODULE_7__["PositionDeleteComponent"], {
            context: {
                title: 'Delete Position',
                action: 'delete',
                positionId: position.id.toString(),
                name: position.name,
            },
        }).onClose.subscribe(_data => {
            this.loadPositions();
        });
    }
    onPageChange(pageEvent) {
        this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
        this.param.pageSize = pageEvent.pageSize;
        this.loadPositions();
    }
};
PositionsViewComponent.ctorParameters = () => [
    { type: app_services_positions__WEBPACK_IMPORTED_MODULE_8__["PositionService"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogService"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"])
], PositionsViewComponent.prototype, "paginator", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_sort__WEBPACK_IMPORTED_MODULE_4__["MatSort"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_sort__WEBPACK_IMPORTED_MODULE_4__["MatSort"])
], PositionsViewComponent.prototype, "sort", void 0);
PositionsViewComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-positions-view',
        template: __webpack_require__(/*! raw-loader!./positions-view.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/system-setup/positions/positions-view/positions-view.component.html"),
        styles: [__webpack_require__(/*! ./positions-view.component.scss */ "./src/app/features/system-setup/positions/positions-view/positions-view.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_positions__WEBPACK_IMPORTED_MODULE_8__["PositionService"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogService"]])
], PositionsViewComponent);



/***/ }),

/***/ "./src/app/features/system-setup/positions/positions.component.scss":
/*!**************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/positions.component.scss ***!
  \**************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9wb3NpdGlvbnMvcG9zaXRpb25zLmNvbXBvbmVudC5zY3NzIn0= */"

/***/ }),

/***/ "./src/app/features/system-setup/positions/positions.component.ts":
/*!************************************************************************!*\
  !*** ./src/app/features/system-setup/positions/positions.component.ts ***!
  \************************************************************************/
/*! exports provided: PositionsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PositionsComponent", function() { return PositionsComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");


let PositionsComponent = class PositionsComponent {
};
PositionsComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-positions',
        template: `<router-outlet></router-outlet>`,
        styles: [__webpack_require__(/*! ./positions.component.scss */ "./src/app/features/system-setup/positions/positions.component.scss")]
    })
], PositionsComponent);



/***/ }),

/***/ "./src/app/features/system-setup/positions/positions.module.ts":
/*!*********************************************************************!*\
  !*** ./src/app/features/system-setup/positions/positions.module.ts ***!
  \*********************************************************************/
/*! exports provided: PositionsModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PositionsModule", function() { return PositionsModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm2015/table.js");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm2015/paginator.js");
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/icon */ "./node_modules/@angular/material/esm2015/icon.js");
/* harmony import */ var _angular_material_menu__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/menu */ "./node_modules/@angular/material/esm2015/menu.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var _positions_routing_module__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./positions-routing.module */ "./src/app/features/system-setup/positions/positions-routing.module.ts");
/* harmony import */ var _positions_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./positions.component */ "./src/app/features/system-setup/positions/positions.component.ts");
/* harmony import */ var _positions_view_positions_view_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./positions-view/positions-view.component */ "./src/app/features/system-setup/positions/positions-view/positions-view.component.ts");
/* harmony import */ var _position_create_position_create_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./position-create/position-create.component */ "./src/app/features/system-setup/positions/position-create/position-create.component.ts");
/* harmony import */ var _position_delete_position_delete_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./position-delete/position-delete.component */ "./src/app/features/system-setup/positions/position-delete/position-delete.component.ts");













const COMPONENTS = [
    _positions_component__WEBPACK_IMPORTED_MODULE_9__["PositionsComponent"],
    _positions_view_positions_view_component__WEBPACK_IMPORTED_MODULE_10__["PositionsViewComponent"],
    _position_create_position_create_component__WEBPACK_IMPORTED_MODULE_11__["PositionCreateComponent"],
    _position_delete_position_delete_component__WEBPACK_IMPORTED_MODULE_12__["PositionDeleteComponent"],
];
const ENTRY_COMPONENTS = [
    _position_create_position_create_component__WEBPACK_IMPORTED_MODULE_11__["PositionCreateComponent"],
    _position_delete_position_delete_component__WEBPACK_IMPORTED_MODULE_12__["PositionDeleteComponent"],
];
const MODULES = [
    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormsModule"],
    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["ReactiveFormsModule"],
    _angular_material_table__WEBPACK_IMPORTED_MODULE_3__["MatTableModule"],
    _angular_material_paginator__WEBPACK_IMPORTED_MODULE_4__["MatPaginatorModule"],
    _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__["MatIconModule"],
    _angular_material_menu__WEBPACK_IMPORTED_MODULE_6__["MatMenuModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbButtonModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbWindowModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbCardModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbInputModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbIconModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbAlertModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_7__["NbDialogModule"].forRoot(),
    _positions_routing_module__WEBPACK_IMPORTED_MODULE_8__["PositionsRoutingModule"],
];
const SERVICES = [];
let PositionsModule = class PositionsModule {
};
PositionsModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
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
], PositionsModule);



/***/ })

}]);
//# sourceMappingURL=positions-positions-module.js.map