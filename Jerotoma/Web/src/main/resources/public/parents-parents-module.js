(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["parents-parents-module"],{

/***/ "./node_modules/raw-loader/index.js!./src/app/features/users/parents/create/parent-create.component.html":
/*!******************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/users/parents/create/parent-create.component.html ***!
  \******************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nb-card>\n  <nb-card-header status=\"danger\" >{{ title }}</nb-card-header>\n  <nb-card-body>\n    <nb-stepper #stepper [linear]=\"linearMode\" >\n        <nb-step [stepControl]=\"parentForm\" label=\"Parent Details\">\n            <form [formGroup]=\"parentForm\" class=\"step-container\" (ngSubmit)=\"onParentSubmit()\">\n                <div class='row'>\n                  <div class='col-md-12'>\n                      <nb-alert *ngIf=\"showMessage.error\" outline=\"danger\" role=\"alert\">\n                          <p class=\"alert-title\"><b>Sorry!</b></p>\n                          <ul class=\"alert-message-list\">\n                              <li class=\"alert-message\">{{ showMessage.message }}</li>\n                            </ul>\n                        </nb-alert>\n                        <nb-alert *ngIf=\"showMessage.success\" outline=\"success\" role=\"alert\">\n                          <p class=\"alert-title\"><b>Success!</b></p>\n                          <ul class=\"alert-message-list\">\n                            <li class=\"alert-message\">{{ showMessage.message }}</li>\n                          </ul>\n                        </nb-alert>\n                  </div>\n                </div>\n                <div class=\"row\">\n                  <div class=\"col-md-6\">\n                    <div class=\"form-control-group\">\n                      <label for=\"parent-first-name\" class=\"label\">First Name</label>\n                      <input\n                            type=\"text\"\n                            nbInput fullWidth\n                            autocomplete=\"new-password\"\n                            required\n                            id=\"parent-first-name\"\n                            [status]=\"parentForm.controls['firstName'].invalid && parentForm.controls['firstName'].touched ? 'danger' : ''\"\n                            formControlName=\"firstName\"\n                            placeholder=\"First Name\">\n                      <ng-container *ngIf=\"parentForm.controls['firstName'].invalid && parentForm.controls['firstName'].touched\">\n                        <p class=\"error-message\">\n                          First Name is required!\n                        </p>\n                      </ng-container>\n                    </div>\n                  </div>\n                  <div class=\"col-md-6\">\n                    <div class=\"form-control-group\">\n                      <label for=\"parent-last-name\" class=\"label\">Last Name</label>\n                      <input\n                            required\n                            type=\"text\"\n                            autocomplete=\"new-password\"\n                            nbInput fullWidth\n                            id=\"parent-last-name\"\n                            [status]=\"parentForm.controls['lastName'].invalid && parentForm.controls['lastName'].touched ? 'danger' : ''\"\n                            formControlName=\"lastName\"\n                            placeholder=\"Last Name\">\n                      <ng-container *ngIf=\"parentForm.controls['lastName'].invalid && parentForm.controls['lastName'].touched\">\n                        <p class=\"error-message\">\n                          Last Name is required!\n                        </p>\n                      </ng-container>\n                  </div>\n                  </div>\n                </div>\n                <div class=\"row\">\n                  <div class=\"col-md-12\">\n                    <div class=\"form-control-group\">\n                      <label for=\"student-middle-names\" class=\"label\">Other Names</label>\n                      <input\n                            type=\"text\"\n                            nbInput fullWidth\n                            autocomplete=\"new-password\"\n                            required\n                            id=\"student-middle-names\"\n                            formControlName=\"middleNames\"\n                            placeholder=\"Other  Names\">\n                    </div>\n                  </div>\n                </div>\n                <div class=\"row\">\n                    <div class=\"col-md-6\">\n                        <div class=\"form-control-group\">\n                            <label for=\"parent-gender\" class=\"label\">Gender</label>\n                            <nb-radio-group formControlName=\"gender\">\n                              <nb-radio [value]=\"'male'\">Male</nb-radio>\n                              <nb-radio [value]=\"'female'\">Female</nb-radio>\n                            </nb-radio-group>\n                        </div>\n                    </div>\n                    <div class=\"col-md-6\">\n                        <div class=\"form-control-group\">\n                          <label for=\"parent-occupation\" class=\"label\">Occupation</label>\n                          <input\n                              nbInput fullWidth\n                              id=\"parent-occupation\"\n                              formControlName=\"occupation\"\n                              [status]=\"parentForm.controls['occupation'].invalid && parentForm.controls['occupation'].touched ? 'danger' : ''\"\n                              placeholder=\"Occupation\">\n                        </div>\n                    </div>\n                </div>\n                <div class=\"row\">\n                  <div class=\"col-md-6\">\n                    <div class=\"form-control-group\">\n                      <label for=\"student-email-address\" class=\"label\">Email Address</label>\n                      <input\n                            type=\"text\"\n                            nbInput fullWidth\n                            autocomplete=\"new-password\"\n                            required\n                            id=\"student-email-address\"\n                            formControlName=\"emailAddress\"\n                            placeholder=\"Email Address\">\n                    </div>\n                  </div>\n                  <div class=\"col-md-6\">\n                    <div class=\"form-control-group\">\n                      <label for=\"student-phone-number\" class=\"label\">Emergency Phone Number</label>\n                      <input\n                            type=\"text\"\n                            nbInput fullWidth\n                            autocomplete=\"new-password\"\n                            required\n                            id=\"student-phone-number\"\n                            formControlName=\"phoneNumber\"\n                            placeholder=\"Emergency Phone Number\">\n                    </div>\n                  </div>\n                </div>\n                <app-address (onChanges)=\"onParentAddressChange($event)\" ></app-address>\n                <div class=\"form-control-group\">\n                  <button class='push-right' [disabled]=\"!parentForm.valid\" type=\"submit\" nbStepperNext nbButton hero status=\"success\">Create Parent</button>\n                </div>\n               </form>\n        </nb-step>\n        <nb-step [stepControl]=\"studentForm\" label=\"Student Details\">\n          <form [formGroup]=\"studentForm\" class=\"step-container\" (ngSubmit)=\"onStudentSubmit()\">\n            <div class='row'>\n              <div class='col-md-12'>\n                  <nb-alert *ngIf=\"showMessage.error\" outline=\"danger\" role=\"alert\">\n                      <p class=\"alert-title\"><b>Sorry!</b></p>\n                      <ul class=\"alert-message-list\">\n                          <li class=\"alert-message\">{{ showMessage.message }}</li>\n                        </ul>\n                    </nb-alert>\n                    <nb-alert *ngIf=\"showMessage.success\" outline=\"success\" role=\"alert\">\n                      <p class=\"alert-title\"><b>Success!</b></p>\n                      <ul class=\"alert-message-list\">\n                        <li class=\"alert-message\">{{ showMessage.message }}</li>\n                      </ul>\n                    </nb-alert>\n              </div>\n            </div>\n            <div class=\"row\">\n              <div class=\"col-md-6\">\n                <div class=\"form-control-group\">\n                  <label for=\"student-first-name\" class=\"label\">First Name</label>\n                  <input\n                        type=\"text\"\n                        nbInput fullWidth\n                        autocomplete=\"new-password\"\n                        required\n                        id=\"student-first-name\"\n                        [status]=\"studentForm.controls['firstName'].invalid && studentForm.controls['firstName'].touched ? 'danger' : ''\"\n                        formControlName=\"firstName\"\n                        placeholder=\"First Name\">\n                  <ng-container *ngIf=\"studentForm.controls['firstName'].invalid && studentForm.controls['firstName'].touched\">\n                    <p class=\"error-message\">\n                      First Name is required!\n                    </p>\n                  </ng-container>\n                </div>\n              </div>\n              <div class=\"col-md-6\">\n                <div class=\"form-control-group\">\n                  <label for=\"student-last-name\" class=\"label\">Last Name</label>\n                  <input\n                        required\n                        type=\"text\"\n                        autocomplete=\"new-password\"\n                        nbInput fullWidth\n                        id=\"student-last-name\"\n                        [status]=\"studentForm.controls['lastName'].invalid && studentForm.controls['lastName'].touched ? 'danger' : ''\"\n                        formControlName=\"lastName\"\n                        placeholder=\"Last Name\">\n                  <ng-container *ngIf=\"studentForm.controls['lastName'].invalid && studentForm.controls['lastName'].touched\">\n                    <p class=\"error-message\">\n                      Last Name is required!\n                    </p>\n                  </ng-container>\n                </div>\n              </div>\n            </div>\n            <div class=\"row\">\n              <div class=\"col-md-12\">\n                <div class=\"form-control-group\">\n                  <label for=\"student-middle-names\" class=\"label\">Other Names</label>\n                  <input\n                        type=\"text\"\n                        nbInput fullWidth\n                        autocomplete=\"new-password\"\n                        required\n                        id=\"student-middle-names\"\n                        formControlName=\"middleNames\"\n                        placeholder=\"Other  Names\">\n                </div>\n              </div>\n            </div>\n            <div class=\"row\">\n                <div class=\"col-md-6\">\n                    <div class=\"form-control-group\">\n                        <label for=\"student-position\" class=\"label\">Gender</label>\n                        <nb-radio-group formControlName=\"gender\">\n                          <nb-radio [value]=\"'male'\">Male</nb-radio>\n                          <nb-radio [value]=\"'female'\">Female</nb-radio>\n                        </nb-radio-group>\n                    </div>\n                </div>\n                <div class=\"col-md-6\">\n                    <div class=\"form-control-group\">\n                      <label for=\"student-birthDate\" class=\"label\">Date of Birth (yyyy/MM/dd)</label>\n                      <input\n                          [nbDatepicker]=\"birthDate\"\n                          nbInput fullWidth\n                          id=\"student-birthDate\"\n                          formControlName=\"birthDate\"\n                          [status]=\"studentForm.controls['birthDate'].invalid && studentForm.controls['birthDate'].touched ? 'danger' : ''\"\n                          placeholder=\"Birth Date\">\n                      <ng-container *ngIf=\"studentForm.controls['birthDate'].invalid && studentForm.controls['birthDate'].touched\">\n                        <p class=\"error-message\">\n                          Date of Birth is required!\n                        </p>\n                      </ng-container>\n                      <nb-datepicker #birthDate format=\"yyyy/MM/dd\"></nb-datepicker>\n                    </div>\n                </div>\n            </div>\n            <div class=\"row\">\n              <div class=\"col-md-6\">\n                <div class=\"form-control-group\">\n                  <label for=\"student-email-address\" class=\"label\">Email Address</label>\n                  <input\n                        type=\"text\"\n                        nbInput fullWidth\n                        autocomplete=\"new-password\"\n                        required\n                        id=\"student-email-address\"\n                        formControlName=\"emailAddress\"\n                        placeholder=\"Email Address\">\n                </div>\n              </div>\n              <div class=\"col-md-6\">\n                <div class=\"form-control-group\">\n                  <label for=\"student-phone-number\" class=\"label\">Emergency Phone Number</label>\n                  <input\n                        type=\"text\"\n                        nbInput fullWidth\n                        autocomplete=\"new-password\"\n                        required\n                        id=\"student-phone-number\"\n                        formControlName=\"phoneNumber\"\n                        placeholder=\"Emergency Phone Number\">\n                </div>\n              </div>\n            </div>\n            <app-address (onChanges)=\"onStudentAddressChange($event)\"></app-address>\n            <div class=\"form-control-group\">\n              <button class='push-right' [disabled]=\"!studentForm.valid\" type=\"submit\"  nbStepperNext nbButton hero status=\"success\">Create Student</button>\n            </div>\n          </form>\n        </nb-step>\n        <nb-step [hidden]=\"true\">\n          <div class=\"step-container\">\n            <h3>Complete Student Registration!</h3>\n            <button class='push-right' nbButton (click)=\"onSubmit()\">Submit</button>\n          </div>\n        </nb-step>\n    </nb-stepper>\n  </nb-card-body>\n</nb-card>\n\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/features/users/parents/views/parents-view.component.html":
/*!****************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/users/parents/views/parents-view.component.html ***!
  \****************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nb-card class=\"col-md-12 col-lg-12 col-xxxl-12\">\n  <nb-card-header> {{title}} <button class=\"push-right\" nbButton hero status='success' (click)=\"open()\">Add New Student</button></nb-card-header>\n  <nb-card-body>\n      <div class=\"mat-elevation-z0\">\n          <table mat-table [dataSource]=\"dataSource\" matSort>\n            <!-- id Column -->\n            <ng-container matColumnDef=\"id\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> No. </th>\n              <td mat-cell *matCellDef=\"let element; let i = index;\"> {{i + 1}} </td>\n            </ng-container>\n             <!-- Student Number On Column -->\n             <ng-container matColumnDef=\"studentNumber\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Student Number</th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.studentNumber}} </td>\n              </ng-container>\n            <!-- Full Column -->\n            <ng-container matColumnDef=\"fullName\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> Full Name </th>\n              <td mat-cell *matCellDef=\"let element\"> {{element.fullName}} </td>\n            </ng-container>\n             <!-- Gender Column -->\n            <ng-container matColumnDef=\"gender\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> Gender </th>\n              <td mat-cell *matCellDef=\"let element\"> {{element.gender}} </td>\n            </ng-container>\n\n             <!-- Email Address Column -->\n             <ng-container matColumnDef=\"emailAddress\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Email Address </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.emailAddress}} </td>\n              </ng-container>\n               <!-- Phone Number Column -->\n              <ng-container matColumnDef=\"phoneNumber\">\n                <th mat-header-cell *matHeaderCellDef mat-sort-header> Phone Number </th>\n                <td mat-cell *matCellDef=\"let element\"> {{element.phoneNumber}} </td>\n              </ng-container>\n            <!-- Registered On Column -->\n            <ng-container matColumnDef=\"createdOn\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> Registered On</th>\n              <td mat-cell *matCellDef=\"let element\"> {{element.createdOn}} </td>\n            </ng-container>\n             <!-- action Column -->\n            <ng-container matColumnDef=\"action\">\n              <th mat-header-cell *matHeaderCellDef>Action</th>\n              <td mat-cell *matCellDef=\"let element\">\n                  <button mat-icon-button [matMenuTriggerFor]=\"menu\">\n                    <mat-icon>more_vert</mat-icon>\n                  </button>\n                  <mat-menu #menu=\"matMenu\" xPosition=\"before\">\n                    <button mat-menu-item (click)=\"view(element)\"><mat-icon>visibility</mat-icon> View</button>\n                    <button mat-menu-item (click)=\"edit(element)\"><mat-icon>edit</mat-icon> Edit</button>\n                    <button mat-menu-item (click)=\"delete(element)\"><mat-icon>delete</mat-icon> Delete</button>\n                  </mat-menu>\n              </td>\n            </ng-container>\n            <tr mat-header-row *matHeaderRowDef=\"displayedColumns\"></tr>\n            <tr mat-row *matRowDef=\"let row; columns: displayedColumns;\"></tr>\n          </table>\n          <mat-paginator\n                [pageSize]='param.pageSize'\n                [length]='totalNumberOfItems'\n                [hidePageSize]='hidePageSize'\n                [pageIndex]=0\n                (page)='onPageChange($event)'\n                [pageSizeOptions]=\"pageSizeOptions\"\n                showFirstLastButtons>\n          </mat-paginator>\n        </div>\n  </nb-card-body>\n</nb-card>\n\n"

/***/ }),

/***/ "./src/app/features/users/parents/create/parent-create.component.scss":
/*!****************************************************************************!*\
  !*** ./src/app/features/users/parents/create/parent-create.component.scss ***!
  \****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "nb-checkbox {\n  margin-bottom: 1rem; }\n\n.form-inline [fullWidth] {\n  flex: 1; }\n\n.form-inline > * {\n  margin: 0 1.5rem 1.5rem 0; }\n\nnb-card.inline-form-card nb-card-body {\n  padding-bottom: 0; }\n\nnb-dialog-container {\n  width: 1200px; }\n\n.dialog-action-close,\n.nb-theme-default [nbButton].appearance-ghost.size-medium {\n  padding: 0 !important; }\n\nbutton.appearance-ghost:focus {\n  box-shadow: none;\n  outline: none; }\n\nnb-icon {\n  font-size: 2.25rem;\n  line-height: 1;\n  width: 1em;\n  height: 1em; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy91c2Vycy9wYXJlbnRzL2NyZWF0ZS9wYXJlbnQtY3JlYXRlLmNvbXBvbmVudC5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsbUJBQW1CLEVBQUE7O0FBR3JCO0VBQ0UsT0FBTyxFQUFBOztBQUdUO0VBQ0UseUJBQXlCLEVBQUE7O0FBRzNCO0VBQ0UsaUJBQWlCLEVBQUE7O0FBR25CO0VBQ0UsYUFBWSxFQUFBOztBQUVkOztFQUVFLHFCQUFxQixFQUFBOztBQUd2QjtFQUNFLGdCQUFnQjtFQUNoQixhQUFhLEVBQUE7O0FBR2Y7RUFDRSxrQkFBa0I7RUFDbEIsY0FBYztFQUNkLFVBQVU7RUFDVixXQUFXLEVBQUEiLCJmaWxlIjoic3JjL2FwcC9mZWF0dXJlcy91c2Vycy9wYXJlbnRzL2NyZWF0ZS9wYXJlbnQtY3JlYXRlLmNvbXBvbmVudC5zY3NzIiwic291cmNlc0NvbnRlbnQiOlsibmItY2hlY2tib3gge1xuICBtYXJnaW4tYm90dG9tOiAxcmVtO1xufVxuXG4uZm9ybS1pbmxpbmUgW2Z1bGxXaWR0aF0ge1xuICBmbGV4OiAxO1xufVxuXG4uZm9ybS1pbmxpbmUgPiAqIHtcbiAgbWFyZ2luOiAwIDEuNXJlbSAxLjVyZW0gMDtcbn1cblxubmItY2FyZC5pbmxpbmUtZm9ybS1jYXJkIG5iLWNhcmQtYm9keSB7XG4gIHBhZGRpbmctYm90dG9tOiAwO1xufVxuXG5uYi1kaWFsb2ctY29udGFpbmVyIHtcbiAgd2lkdGg6MTIwMHB4O1xufVxuLmRpYWxvZy1hY3Rpb24tY2xvc2UsXG4ubmItdGhlbWUtZGVmYXVsdCBbbmJCdXR0b25dLmFwcGVhcmFuY2UtZ2hvc3Quc2l6ZS1tZWRpdW0ge1xuICBwYWRkaW5nOiAwICFpbXBvcnRhbnQ7XG59XG5cbmJ1dHRvbi5hcHBlYXJhbmNlLWdob3N0OmZvY3VzIHtcbiAgYm94LXNoYWRvdzogbm9uZTtcbiAgb3V0bGluZTogbm9uZTtcbn1cblxubmItaWNvbiB7XG4gIGZvbnQtc2l6ZTogMi4yNXJlbTtcbiAgbGluZS1oZWlnaHQ6IDE7XG4gIHdpZHRoOiAxZW07XG4gIGhlaWdodDogMWVtO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/features/users/parents/create/parent-create.component.ts":
/*!**************************************************************************!*\
  !*** ./src/app/features/users/parents/create/parent-create.component.ts ***!
  \**************************************************************************/
/*! exports provided: ParentCreateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ParentCreateComponent", function() { return ParentCreateComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var app_services_users__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! app/services/users */ "./src/app/services/users/index.ts");
/* harmony import */ var app_services_positions__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! app/services/positions */ "./src/app/services/positions/index.ts");
/* harmony import */ var app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! app/services/academic-disciplines */ "./src/app/services/academic-disciplines/index.ts");
/* harmony import */ var app_utils__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! app/utils */ "./src/app/utils/index.ts");








let ParentCreateComponent = class ParentCreateComponent {
    constructor(positionService, academicDisciplineService, userService, formBuilder) {
        this.positionService = positionService;
        this.academicDisciplineService = academicDisciplineService;
        this.userService = userService;
        this.formBuilder = formBuilder;
        this.title = 'Create New Parent';
        this.onUserCreationSuccess = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.action = 'create';
        this.showMessage = {
            error: false,
            success: false,
            message: '',
        };
        this.listDisplay = 'none';
        this.linearMode = true;
    }
    toggleLinearMode() {
        this.linearMode = !this.linearMode;
    }
    ngOnInit() {
        this.loadStudentForm();
        this.loadParentForm();
    }
    ngAfterViewInit() {
        if (this.action === 'edit') {
            this.loadTeacher(parseInt(this.studentId, 10));
        }
    }
    dismiss() {
    }
    onStudentSubmit() {
        this.title = 'Create New Student';
    }
    onParentSubmit() {
        this.title = 'Create New Parent';
    }
    onSubmit() {
        window.console.log(this.studentForm, this.parentForm);
        if (this.studentForm.valid && this.parentForm.valid) {
            const dob = this.studentForm.get('birthDate');
            if (dob && dob.valid) {
                this.studentForm.patchValue({
                    birthDate: Object(app_utils__WEBPACK_IMPORTED_MODULE_7__["DateFormatter"])(dob.value).format('YYYY/MM/DD'),
                });
            }
            const data = {
                student: this.studentForm.value,
                parent: this.parentForm.value,
                userType: 'studentAndParent',
            };
            this.postData(data);
        }
    }
    postData(data) {
        this.showMessage.success = false;
        this.showMessage.error = false;
        if (this.action === 'edit') {
            this.updateData(data);
        }
        else {
            this.userService.addUser(data).subscribe((result) => {
                const resp = result;
                const status = resp.status;
                if (status !== null && status === 200) {
                    this.showMessage.success = true;
                    this.showMessage.error = false;
                    this.showMessage.message = resp ? resp.body.message : '';
                    this.resetForms();
                }
                else {
                    this.showMessage.success = false;
                    this.showMessage.error = true;
                    this.showMessage.message = resp ? resp.body.message : '';
                }
            }, error => {
                this.showMessage.error = true;
                this.showMessage.success = false;
                this.showMessage.message = error ? error.error.message : '';
            });
        }
    }
    updateData(data) {
        this.userService.updateUser(data).subscribe((result) => {
            const resp = result;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.showMessage.success = true;
                this.studentForm.reset();
                this.onUserCreationSuccess.emit(this.showMessage.success);
                this.showMessage.error = false;
                this.showMessage.message = resp ? resp.body.message : '';
            }
            else {
                this.showMessage.success = false;
                this.showMessage.error = true;
                this.showMessage.message = resp ? resp.body.message : '';
            }
        }, error => {
            this.showMessage.error = true;
            this.showMessage.success = false;
            this.showMessage.message = error ? error.error.message : '';
        });
    }
    resetForms() {
        this.studentForm.reset();
        this.parentForm.reset();
        this.stepper.reset();
    }
    loadStudentForm() {
        this.studentForm = this.formBuilder.group({
            id: [null],
            firstName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            lastName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            middleNames: [''],
            phoneNumber: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            emailAddress: [null],
            gender: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            picture: [''],
            userType: ['student'],
            birthDate: ['', Object(app_utils__WEBPACK_IMPORTED_MODULE_7__["DateValidator"])('yyyy/MM/dd')],
            address: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
        });
    }
    loadParentForm() {
        this.parentForm = this.formBuilder.group({
            id: [null],
            firstName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            lastName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            middleNames: [''],
            occupation: [''],
            phoneNumber: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            emailAddress: [null],
            gender: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            picture: [''],
            userType: ['parent'],
            address: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
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
            userType: 'studentAndParent',
        };
    }
    loadTeacher(studentId) {
        this.userService.loadUser(studentId, 'student').subscribe((result) => {
            const resp = result;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.student = resp.body.data;
                this.studentForm.patchValue({
                    id: this.student.id,
                    firstName: this.student.firstName,
                    lastName: this.student.lastName,
                    occupation: this.student.occupation,
                    gender: this.student.gender,
                    picture: this.student.picture,
                    birthDate: Object(app_utils__WEBPACK_IMPORTED_MODULE_7__["DateFormatter"])(this.student.birthDate, 'YYYY/MM/DD', false),
                    userType: 'student',
                    fullName: this.student.fullName,
                });
            }
        }, error => {
            this.showMessage.error = true;
            this.showMessage.success = false;
            this.showMessage.message = error ? error.error.message : '';
        });
    }
    onStudentAddressChange(addressWrapper) {
        if (!addressWrapper.isValid) {
            this.studentForm.controls['address'].setErrors({ invalidAddress: true });
        }
        else {
            this.studentForm.controls['address'].setErrors(null);
            this.studentForm.patchValue({ address: addressWrapper.address });
        }
    }
    onParentAddressChange(addressWrapper) {
        if (!addressWrapper.isValid) {
            this.parentForm.controls['address'].setErrors({ invalidAddress: true });
        }
        else {
            this.parentForm.controls['address'].setErrors(null);
            this.parentForm.patchValue({ address: addressWrapper.address });
        }
    }
};
ParentCreateComponent.ctorParameters = () => [
    { type: app_services_positions__WEBPACK_IMPORTED_MODULE_5__["PositionService"] },
    { type: app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_6__["AcademicDisciplineService"] },
    { type: app_services_users__WEBPACK_IMPORTED_MODULE_4__["UserService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
], ParentCreateComponent.prototype, "onUserCreationSuccess", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])('stepper', { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbStepperComponent"])
], ParentCreateComponent.prototype, "stepper", void 0);
ParentCreateComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-parent-create',
        template: __webpack_require__(/*! raw-loader!./parent-create.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/users/parents/create/parent-create.component.html"),
        styles: [__webpack_require__(/*! ./parent-create.component.scss */ "./src/app/features/users/parents/create/parent-create.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_positions__WEBPACK_IMPORTED_MODULE_5__["PositionService"],
        app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_6__["AcademicDisciplineService"],
        app_services_users__WEBPACK_IMPORTED_MODULE_4__["UserService"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"]])
], ParentCreateComponent);



/***/ }),

/***/ "./src/app/features/users/parents/parents-routing.module.ts":
/*!******************************************************************!*\
  !*** ./src/app/features/users/parents/parents-routing.module.ts ***!
  \******************************************************************/
/*! exports provided: StudentsRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "StudentsRoutingModule", function() { return StudentsRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var app_services_guards_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/services/guards/auth-guard.service */ "./src/app/services/guards/auth-guard.service.ts");
/* harmony import */ var _parents_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./parents.component */ "./src/app/features/users/parents/parents.component.ts");
/* harmony import */ var _create_parent_create_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./create/parent-create.component */ "./src/app/features/users/parents/create/parent-create.component.ts");
/* harmony import */ var _views_parents_view_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./views/parents-view.component */ "./src/app/features/users/parents/views/parents-view.component.ts");

// Modules


// Service

// Components



// Utilities
const routes = [
    {
        path: '',
        canActivateChild: [app_services_guards_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__["AuthGuard"]],
        component: _parents_component__WEBPACK_IMPORTED_MODULE_4__["ParentsComponent"],
        children: [
            {
                path: '',
                component: _views_parents_view_component__WEBPACK_IMPORTED_MODULE_6__["ParentsViewComponent"],
            },
            {
                path: 'create',
                component: _create_parent_create_component__WEBPACK_IMPORTED_MODULE_5__["ParentCreateComponent"],
            },
            {
                path: 'views',
                component: _views_parents_view_component__WEBPACK_IMPORTED_MODULE_6__["ParentsViewComponent"],
            },
        ],
    },
];
let StudentsRoutingModule = class StudentsRoutingModule {
};
StudentsRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forChild(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]],
    })
], StudentsRoutingModule);



/***/ }),

/***/ "./src/app/features/users/parents/parents.component.ts":
/*!*************************************************************!*\
  !*** ./src/app/features/users/parents/parents.component.ts ***!
  \*************************************************************/
/*! exports provided: ParentsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ParentsComponent", function() { return ParentsComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");


let ParentsComponent = class ParentsComponent {
};
ParentsComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-parents',
        template: `
    <router-outlet></router-outlet>
  `,
    })
], ParentsComponent);



/***/ }),

/***/ "./src/app/features/users/parents/parents.module.ts":
/*!**********************************************************!*\
  !*** ./src/app/features/users/parents/parents.module.ts ***!
  \**********************************************************/
/*! exports provided: ParentsModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ParentsModule", function() { return ParentsModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! app/shared/shared.module */ "./src/app/shared/shared.module.ts");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm2015/table.js");
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/select */ "./node_modules/@angular/material/esm2015/select.js");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm2015/paginator.js");
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/icon */ "./node_modules/@angular/material/esm2015/icon.js");
/* harmony import */ var _angular_material_menu__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/menu */ "./node_modules/@angular/material/esm2015/menu.js");
/* harmony import */ var _nebular_moment__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @nebular/moment */ "./node_modules/@nebular/moment/fesm2015/index.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var app_theme_theme_module__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! app/@theme/theme.module */ "./src/app/@theme/theme.module.ts");
/* harmony import */ var _parents_routing_module__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./parents-routing.module */ "./src/app/features/users/parents/parents-routing.module.ts");
/* harmony import */ var _parents_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./parents.component */ "./src/app/features/users/parents/parents.component.ts");
/* harmony import */ var _create_parent_create_component__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./create/parent-create.component */ "./src/app/features/users/parents/create/parent-create.component.ts");
/* harmony import */ var _views_parents_view_component__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./views/parents-view.component */ "./src/app/features/users/parents/views/parents-view.component.ts");















const COMPONENTS = [
    _parents_component__WEBPACK_IMPORTED_MODULE_12__["ParentsComponent"],
    _views_parents_view_component__WEBPACK_IMPORTED_MODULE_14__["ParentsViewComponent"],
    _create_parent_create_component__WEBPACK_IMPORTED_MODULE_13__["ParentCreateComponent"],
];
const ENTRY_COMPONENTS = [
    _create_parent_create_component__WEBPACK_IMPORTED_MODULE_13__["ParentCreateComponent"],
];
const MODULES = [
    app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__["SharedModule"],
    app_theme_theme_module__WEBPACK_IMPORTED_MODULE_10__["ThemeModule"],
    _nebular_moment__WEBPACK_IMPORTED_MODULE_8__["NbMomentDateModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbTabsetModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbRadioModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbDatepickerModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbAlertModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbRouteTabsetModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbStepperModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbCardModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbCheckboxModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbSelectModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbInputModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbButtonModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbListModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbAccordionModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbUserModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbActionsModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbIconModule"],
    _angular_material_table__WEBPACK_IMPORTED_MODULE_3__["MatTableModule"],
    _angular_material_paginator__WEBPACK_IMPORTED_MODULE_5__["MatPaginatorModule"],
    _angular_material_icon__WEBPACK_IMPORTED_MODULE_6__["MatIconModule"],
    _angular_material_menu__WEBPACK_IMPORTED_MODULE_7__["MatMenuModule"],
    _angular_material_select__WEBPACK_IMPORTED_MODULE_4__["MatSelectModule"],
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbDialogModule"].forRoot({
        closeOnBackdropClick: false,
        hasScroll: false,
    }),
    _nebular_theme__WEBPACK_IMPORTED_MODULE_9__["NbWindowModule"].forRoot(),
    _parents_routing_module__WEBPACK_IMPORTED_MODULE_11__["StudentsRoutingModule"],
];
const SERVICES = [];
let ParentsModule = class ParentsModule {
};
ParentsModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
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
], ParentsModule);



/***/ }),

/***/ "./src/app/features/users/parents/views/parents-view.component.scss":
/*!**************************************************************************!*\
  !*** ./src/app/features/users/parents/views/parents-view.component.scss ***!
  \**************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "table {\n  width: 100%; }\n\nbutton {\n  cursor: pointer;\n  margin: 0;\n  border: none; }\n\nbutton:focus {\n  outline: none; }\n\nbutton:hover {\n  background-color: rgba(0, 0, 0, 0.03); }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy91c2Vycy9wYXJlbnRzL3ZpZXdzL3BhcmVudHMtdmlldy5jb21wb25lbnQuc2NzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLFdBQVcsRUFBQTs7QUFFYjtFQUNFLGVBQWU7RUFDZixTQUFTO0VBQ1QsWUFBWSxFQUFBOztBQUdkO0VBQ0UsYUFBYSxFQUFBOztBQUVmO0VBQ0UscUNBQXFDLEVBQUEiLCJmaWxlIjoic3JjL2FwcC9mZWF0dXJlcy91c2Vycy9wYXJlbnRzL3ZpZXdzL3BhcmVudHMtdmlldy5jb21wb25lbnQuc2NzcyIsInNvdXJjZXNDb250ZW50IjpbInRhYmxlIHtcbiAgd2lkdGg6IDEwMCU7XG59XG5idXR0b24ge1xuICBjdXJzb3I6IHBvaW50ZXI7XG4gIG1hcmdpbjogMDtcbiAgYm9yZGVyOiBub25lO1xufVxuXG5idXR0b246Zm9jdXMge1xuICBvdXRsaW5lOiBub25lO1xufVxuYnV0dG9uOmhvdmVyIHtcbiAgYmFja2dyb3VuZC1jb2xvcjogcmdiYSgwLCAwLCAwLCAwLjAzKTs7XG59XG4iXX0= */"

/***/ }),

/***/ "./src/app/features/users/parents/views/parents-view.component.ts":
/*!************************************************************************!*\
  !*** ./src/app/features/users/parents/views/parents-view.component.ts ***!
  \************************************************************************/
/*! exports provided: ParentsViewComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ParentsViewComponent", function() { return ParentsViewComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm2015/paginator.js");
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/sort */ "./node_modules/@angular/material/esm2015/sort.js");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm2015/table.js");
/* harmony import */ var app_services_users__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! app/services/users */ "./src/app/services/users/index.ts");
/* harmony import */ var app_features_users_user_delete_user_delete_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! app/features/users/user-delete/user-delete.component */ "./src/app/features/users/user-delete/user-delete.component.ts");









let ParentsViewComponent = class ParentsViewComponent {
    constructor(userService, dialogService, router) {
        this.userService = userService;
        this.dialogService = dialogService;
        this.router = router;
        this.title = 'Parent\'s List';
        this.baseURL = '/dashboard/users/students/';
        this.hidePageSize = false;
        this.totalNumberOfItems = 20;
        this.pageSizeOptions = [10, 20, 30, 50, 70, 100];
        this.displayedColumns = ['id', 'studentNumber', 'fullName', 'gender', 'emailAddress', 'phoneNumber', 'createdOn', 'action'];
        this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_6__["MatTableDataSource"]();
        this.param = {
            page: 1,
            pageSize: 10,
            orderby: 'DESC',
            status: '',
            search: '',
            fieldName: '',
            userType: 'student',
        };
        this.students = [];
    }
    ngOnInit() {
        this.loadUsers();
    }
    open() {
        this.router.navigate([this.baseURL + '/create']);
    }
    edit(student) {
        this.router.navigate([this.baseURL + 'edit/' + student.id]);
    }
    view(student) {
        this.router.navigate([this.baseURL + '/' + student.id]);
    }
    delete(student) {
        this.dialogService.open(app_features_users_user_delete_user_delete_component__WEBPACK_IMPORTED_MODULE_8__["UserDeleteComponent"], {
            context: {
                title: 'Delete Student',
                action: 'delete',
                userType: 'student',
                userId: student.id.toString(),
                name: student.fullName,
            },
        }).onClose.subscribe(_data => {
            this.loadUsers();
        });
    }
    onPageChange(pageEvent) {
        this.param.page = pageEvent.pageIndex === 0 ? 1 : pageEvent.pageIndex;
        this.param.pageSize = pageEvent.pageSize;
        this.loadUsers();
    }
    loadUsers() {
        this.userService.loadUsers(this.param).subscribe((result) => {
            const resp = result;
            const status = resp.status;
            if (status !== null && status === 200 && resp.body) {
                const data = resp.body.data;
                this.totalNumberOfItems = data.count;
                this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_6__["MatTableDataSource"](data.students);
            }
        });
    }
};
ParentsViewComponent.ctorParameters = () => [
    { type: app_services_users__WEBPACK_IMPORTED_MODULE_7__["UserService"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogService"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_paginator__WEBPACK_IMPORTED_MODULE_4__["MatPaginator"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_paginator__WEBPACK_IMPORTED_MODULE_4__["MatPaginator"])
], ParentsViewComponent.prototype, "paginator", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_sort__WEBPACK_IMPORTED_MODULE_5__["MatSort"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_sort__WEBPACK_IMPORTED_MODULE_5__["MatSort"])
], ParentsViewComponent.prototype, "sort", void 0);
ParentsViewComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-parent-views',
        template: __webpack_require__(/*! raw-loader!./parents-view.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/users/parents/views/parents-view.component.html"),
        styles: [__webpack_require__(/*! ./parents-view.component.scss */ "./src/app/features/users/parents/views/parents-view.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_users__WEBPACK_IMPORTED_MODULE_7__["UserService"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogService"],
        _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"]])
], ParentsViewComponent);



/***/ })

}]);
//# sourceMappingURL=parents-parents-module.js.map