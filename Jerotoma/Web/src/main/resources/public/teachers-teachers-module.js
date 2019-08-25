(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["teachers-teachers-module"],{

/***/ "./node_modules/raw-loader/index.js!./src/app/features/users/teachers/create/teacher-create.component.html":
/*!********************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/users/teachers/create/teacher-create.component.html ***!
  \********************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class='row'>\n    <div class=\"col-md-12 col-lg-12 col-xxxl-12\">\n        <form  autocomplete=\"off\" [formGroup]=\"teacherForm\" (ngSubmit)=\"onSubmit()\">\n            <nb-card size=\"giant\" status=\"primary\">\n              <nb-card-header>{{ title }}</nb-card-header>\n              <nb-card-body>\n                <div class='row'>\n                    <div class='col-md-12'>\n                        <nb-alert *ngIf=\"showMessage.error\" outline=\"danger\" role=\"alert\">\n                            <p class=\"alert-title\"><b>Sorry!</b></p>\n                            <ul class=\"alert-message-list\">\n                                <li class=\"alert-message\">{{ showMessage.message }}</li>\n                              </ul>\n                          </nb-alert>\n                          <nb-alert *ngIf=\"showMessage.success\" outline=\"success\" role=\"alert\">\n                            <p class=\"alert-title\"><b>Success!</b></p>\n                            <ul class=\"alert-message-list\">\n                              <li class=\"alert-message\">{{ showMessage.message }}</li>\n                            </ul>\n                          </nb-alert>\n                    </div>\n                </div>\n                  <div class=\"row\">\n                      <div class=\"col-md-6\">\n                          <div class=\"form-control-group\">\n                              <label for=\"teacher-full-name\" class=\"label\">Login Credential</label>\n                              <input\n                                  type=\"text\"\n                                  nbInput fullWidth\n                                  autocomplete=\"new-password\"\n                                  required\n                                  id=\"teacher-full-name\"\n                                  formControlName=\"fullName\"\n                                  placeholder=\"Login Credential\">\n                            </div>\n                            <nb-card [ngStyle]=\"{'display': listDisplay}\">\n                              <nb-list>\n                                <nb-list-item *ngFor=\"let user of users\">\n                                  <a href=\"#\" class=\"user-list\" (click)=\"pickUser($event, user)\" >\n                                      <nb-user\n                                          [name]=\"user.firstName + '' + user.lastName\"\n                                          [title]=\"user.username\">\n                                      </nb-user>\n                                  </a>\n                                </nb-list-item>\n                              </nb-list>\n                            </nb-card>\n                      </div>\n                      <div class=\"col-md-6\">\n                          <div class=\"form-control-group\">\n                            <label for=\"teacher-employment-code\" class=\"label\">Employment Code</label>\n                            <input\n                                type=\"text\"\n                                nbInput fullWidth\n                                autocomplete=\"off\"\n                                required\n                                id=\"teacher-employment-code\"\n                                formControlName=\"employmentCode\"\n                                placeholder=\"Employment Code\">\n                          </div>\n                      </div>\n                  </div>\n                  <div class=\"form-control-group\">\n                    <label for=\"teacher-first-name\" class=\"label\">First Name</label>\n                    <input\n                          type=\"text\"\n                          nbInput fullWidth\n                          autocomplete=\"new-password\"\n                          required\n                          id=\"teacher-first-name\"\n                          formControlName=\"firstName\"\n                          placeholder=\"First Name\">\n                    <ng-container *ngIf=\"teacherForm.controls['firstName'].invalid && teacherForm.controls['firstName'].touched\">\n                      <p class=\"error-message\">\n                        First Name is required!\n                      </p>\n                    </ng-container>\n                  </div>\n                  <div class=\"form-control-group\">\n                    <label for=\"teacher-last-name\" class=\"label\">Last Name</label>\n                    <input\n                          required\n                          type=\"text\"\n                          autocomplete=\"new-password\"\n                          nbInput fullWidth\n                          id=\"teacher-last-name\"\n                          formControlName=\"lastName\"\n                          placeholder=\"Last Name\">\n                    <ng-container *ngIf=\"teacherForm.controls['lastName'].invalid && teacherForm.controls['lastName'].touched\">\n                      <p class=\"error-message\">\n                        Last Name is required!\n                      </p>\n                    </ng-container>\n                  </div>\n                  <div class=\"row\">\n                      <div class=\"col-md-12\">\n                        <div class=\"form-control-group\">\n                          <label for=\"student-middle-names\" class=\"label\">Other Names</label>\n                          <input\n                                type=\"text\"\n                                nbInput fullWidth\n                                autocomplete=\"new-password\"\n                                required\n                                id=\"student-middle-names\"\n                                formControlName=\"middleNames\"\n                                placeholder=\"Other  Names\">\n                        </div>\n                      </div>\n                  </div>\n                  <div class=\"row\">\n                      <div class=\"col-md-6\">\n                          <div class=\"form-control-group\">\n                              <label for=\"teacher-position\" class=\"label\">Position</label>\n                              <nb-select\n                                fullWidth\n                                placeholder=\"Position\"\n                                formControlName=\"position\"\n                                id=\"teacher-position\">\n                                <nb-option\n                                    *ngFor='let pos of positions' [value]=\"pos.id\">\n                                    {{pos.name}}\n                                </nb-option>\n                              </nb-select>\n                          </div>\n                      </div>\n                      <div class=\"col-md-6\">\n                          <div class=\"form-control-group\">\n                              <label for=\"teacher-academicDiscipline\" class=\"label\">Field of Study</label>\n                              <nb-select\n                                fullWidth\n                                placeholder=\"Field of Study\"\n                                formControlName=\"academicDiscipline\"\n                                id=\"teacher-academicDiscipline\">\n                                <nb-option\n                                    *ngFor='let acad of academicDisciplines'\n                                    [value]=\"acad.id\">\n                                    {{acad.name}}\n                                </nb-option>\n                              </nb-select>\n                          </div>\n                      </div>\n                  </div>\n                  <div class=\"row\">\n                      <div class=\"col-md-6\">\n                          <div class=\"form-control-group\">\n                              <label for=\"teacher-position\" class=\"label\">Gender</label>\n                              <nb-radio-group formControlName=\"gender\">\n                                <nb-radio [value]=\"'male'\">Male</nb-radio>\n                                <nb-radio [value]=\"'female'\">Female</nb-radio>\n                              </nb-radio-group>\n                          </div>\n                      </div>\n                      <div class=\"col-md-6\">\n                          <div class=\"form-control-group\">\n                            <label for=\"teacher-birthDate\" class=\"label\">Date of Birth (yyyy/MM/dd)</label>\n                            <input\n                                [nbDatepicker]=\"birthDate\"\n                                nbInput fullWidth\n                                id=\"teacher-birthDate\"\n                                formControlName=\"birthDate\"\n                                placeholder=\"Birth Date\">\n                            <nb-datepicker #birthDate format=\"yyyy/MM/dd\"></nb-datepicker>\n                          </div>\n                      </div>\n                  </div>\n                  <div class=\"row\">\n                      <div class=\"col-md-6\">\n                        <div class=\"form-control-group\">\n                          <label for=\"student-email-address\" class=\"label\">Email Address</label>\n                          <input\n                                type=\"text\"\n                                nbInput fullWidth\n                                autocomplete=\"new-password\"\n                                required\n                                id=\"student-email-address\"\n                                formControlName=\"emailAddress\"\n                                placeholder=\"Email Address\">\n                        </div>\n                      </div>\n                      <div class=\"col-md-6\">\n                        <div class=\"form-control-group\">\n                          <label for=\"student-phone-number\" class=\"label\">Phone Number</label>\n                          <input\n                                type=\"text\"\n                                nbInput fullWidth\n                                autocomplete=\"new-password\"\n                                required\n                                id=\"student-phone-number\"\n                                formControlName=\"phoneNumber\"\n                                placeholder=\"Emergency Phone Number\">\n                        </div>\n                      </div>\n                    </div>\n                    <app-address (onChanges)=\"onAddressChange($event)\" ></app-address>\n              </nb-card-body>\n              <nb-card-footer>\n                <button class='push-right' [disabled]=\"!teacherForm.valid\" type=\"submit\" nbButton hero status=\"success\">Submit</button>\n                <button class='push-right' nbButton hero status=\"danger\" (click)=\"dismiss()\">Cancel</button>\n              </nb-card-footer>\n            </nb-card>\n        </form>\n    </div>\n</div>\n\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/features/users/teachers/views/teachers-view.component.html":
/*!******************************************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/features/users/teachers/views/teachers-view.component.html ***!
  \******************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nb-card class=\"col-md-12 col-lg-12 col-xxxl-12\">\n  <nb-card-header> {{title}} <button class=\"push-right\" nbButton hero status='success' (click)=\"open()\">Add New Teacher</button></nb-card-header>\n  <nb-card-body>\n      <div class=\"mat-elevation-z0\">\n          <table mat-table [dataSource]=\"dataSource\" matSort>\n            <!-- id Column -->\n            <ng-container matColumnDef=\"id\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> No. </th>\n              <td mat-cell *matCellDef=\"let element; let i=index;\"> {{i + 1}} </td>\n            </ng-container>\n            <!-- Full Column -->\n            <ng-container matColumnDef=\"fullName\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> Full Name </th>\n              <td mat-cell *matCellDef=\"let element\"> {{element.fullName}} </td>\n            </ng-container>\n             <!-- Gender Column -->\n            <ng-container matColumnDef=\"gender\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> Gender </th>\n              <td mat-cell *matCellDef=\"let element\"> {{element.gender}} </td>\n            </ng-container>\n            <!-- Academic Discipline Column -->\n            <ng-container matColumnDef=\"academicDiscipline\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> Academic Discipline </th>\n              <td mat-cell *matCellDef=\"let element\"> {{element.academicDiscipline.name}} </td>\n            </ng-container>\n            <!-- Academic Discipline Column -->\n            <ng-container matColumnDef=\"position\">\n              <th mat-header-cell *matHeaderCellDef mat-sort-header> Position</th>\n              <td mat-cell *matCellDef=\"let element\"> {{element.position.name}} </td>\n            </ng-container>\n             <!-- action Column -->\n            <ng-container matColumnDef=\"action\">\n              <th mat-header-cell *matHeaderCellDef>Action</th>\n              <td mat-cell *matCellDef=\"let element\">\n                  <button mat-icon-button [matMenuTriggerFor]=\"menu\">\n                    <mat-icon>more_vert</mat-icon>\n                  </button>\n                  <mat-menu #menu=\"matMenu\" xPosition=\"before\">\n                    <button mat-menu-item (click)=\"edit(element)\"><mat-icon>edit</mat-icon> Edit</button>\n                    <button mat-menu-item (click)=\"delete(element)\"><mat-icon>delete</mat-icon> Delete</button>\n                  </mat-menu>\n              </td>\n            </ng-container>\n            <tr mat-header-row *matHeaderRowDef=\"displayedColumns\"></tr>\n            <tr mat-row *matRowDef=\"let row; columns: displayedColumns;\"></tr>\n          </table>\n          <mat-paginator\n                [pageSize]='param.pageSize'\n                [length]='totalNumberOfItems'\n                [hidePageSize]='hidePageSize'\n                [pageIndex]=0\n                (page)='onPageChange($event)'\n                [pageSizeOptions]=\"pageSizeOptions\"\n                showFirstLastButtons>\n          </mat-paginator>\n        </div>\n  </nb-card-body>\n</nb-card>\n\n"

/***/ }),

/***/ "./src/app/features/users/teachers/create/teacher-create.component.scss":
/*!******************************************************************************!*\
  !*** ./src/app/features/users/teachers/create/teacher-create.component.scss ***!
  \******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "nb-checkbox {\n  margin-bottom: 1rem; }\n\n.form-inline [fullWidth] {\n  flex: 1; }\n\n.form-inline > * {\n  margin: 0 1.5rem 1.5rem 0; }\n\nnb-card.inline-form-card nb-card-body {\n  padding-bottom: 0; }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy91c2Vycy90ZWFjaGVycy9jcmVhdGUvdGVhY2hlci1jcmVhdGUuY29tcG9uZW50LnNjc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxtQkFBbUIsRUFBQTs7QUFHckI7RUFDRSxPQUFPLEVBQUE7O0FBR1Q7RUFDRSx5QkFBeUIsRUFBQTs7QUFHM0I7RUFDRSxpQkFBaUIsRUFBQSIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3VzZXJzL3RlYWNoZXJzL2NyZWF0ZS90ZWFjaGVyLWNyZWF0ZS5jb21wb25lbnQuc2NzcyIsInNvdXJjZXNDb250ZW50IjpbIm5iLWNoZWNrYm94IHtcbiAgbWFyZ2luLWJvdHRvbTogMXJlbTtcbn1cblxuLmZvcm0taW5saW5lIFtmdWxsV2lkdGhdIHtcbiAgZmxleDogMTtcbn1cblxuLmZvcm0taW5saW5lID4gKiB7XG4gIG1hcmdpbjogMCAxLjVyZW0gMS41cmVtIDA7XG59XG5cbm5iLWNhcmQuaW5saW5lLWZvcm0tY2FyZCBuYi1jYXJkLWJvZHkge1xuICBwYWRkaW5nLWJvdHRvbTogMDtcbn1cblxuIl19 */"

/***/ }),

/***/ "./src/app/features/users/teachers/create/teacher-create.component.ts":
/*!****************************************************************************!*\
  !*** ./src/app/features/users/teachers/create/teacher-create.component.ts ***!
  \****************************************************************************/
/*! exports provided: TeacherCreateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TeacherCreateComponent", function() { return TeacherCreateComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var app_services_users__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! app/services/users */ "./src/app/services/users/index.ts");
/* harmony import */ var app_services_positions__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! app/services/positions */ "./src/app/services/positions/index.ts");
/* harmony import */ var app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! app/services/academic-disciplines */ "./src/app/services/academic-disciplines/index.ts");
/* harmony import */ var app_utils__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! app/utils */ "./src/app/utils/index.ts");








let TeacherCreateComponent = class TeacherCreateComponent {
    constructor(positionService, academicDisciplineService, dateService, userService, formBuilder, ref) {
        this.positionService = positionService;
        this.academicDisciplineService = academicDisciplineService;
        this.dateService = dateService;
        this.userService = userService;
        this.formBuilder = formBuilder;
        this.ref = ref;
        this.onUserCreationSuccess = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
        this.action = 'create';
        this.showMessage = {
            error: false,
            success: false,
            message: '',
        };
        this.users = [];
        this.positions = [];
        this.academicDisciplines = [];
        this.listDisplay = 'none';
    }
    ngOnInit() {
        this.loadPositionList();
        this.loadAcademicDisciplineList();
        this.loadForm();
        this.onCredentialInputChanges();
    }
    ngAfterViewInit() {
        if (this.action === 'edit') {
            this.loadTeacher(parseInt(this.teacherId, 10));
        }
    }
    dismiss() {
        this.ref.close();
    }
    onCredentialInputChanges() {
        this.teacherForm.get('fullName').valueChanges.subscribe(value => {
            this.search(value);
        });
    }
    onSubmit() {
        const dob = this.teacherForm.get('birthDate');
        const academicDiscipline = this.teacherForm.get('academicDiscipline');
        const position = this.teacherForm.get('position');
        if (dob && dob.valid) {
            this.teacherForm.patchValue({
                birthDate: Object(app_utils__WEBPACK_IMPORTED_MODULE_7__["DateFormatter"])(dob.value).format('YYYY/MM/DD'),
                academicDiscipline: parseInt(academicDiscipline.value, 10),
                position: parseInt(position.value, 10),
            });
        }
        this.teacher = this.teacherForm.value;
        this.showMessage.success = false;
        this.showMessage.error = false;
        if (this.action === 'edit') {
            this.updateTeacher();
        }
        else {
            this.userService.addUser(this.teacher).subscribe((result) => {
                const resp = result;
                const status = resp.status;
                if (status !== null && status === 200) {
                    this.showMessage.success = true;
                    this.teacherForm.reset();
                    this.ref.close();
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
    }
    updateTeacher() {
        this.userService.updateUser(this.teacher).subscribe((result) => {
            const resp = result;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.showMessage.success = true;
                this.teacherForm.reset();
                this.ref.close();
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
    search(value) {
        const param = this.getParam();
        param.search = value;
        this.userService.search(param).subscribe((result) => {
            this.users = [];
            if (result && result.success) {
                this.users = result.data;
                this.listDisplay = 'block';
            }
        });
    }
    loadForm() {
        this.teacherForm = this.formBuilder.group({
            id: [null],
            firstName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            lastName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            position: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            occupation: ['Teacher'],
            employmentCode: [''],
            gender: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            picture: [''],
            middleNames: [''],
            phoneNumber: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            emailAddress: [null],
            userId: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            birthDate: ['', Object(app_utils__WEBPACK_IMPORTED_MODULE_7__["DateValidator"])('yyyy/MM/dd')],
            userType: ['teacher'],
            academicDiscipline: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            fullName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            address: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
        });
    }
    pickUser(event, user) {
        event.preventDefault();
        const firstName = this.teacherForm.get('firstName');
        const lastName = this.teacherForm.get('lastName');
        this.listDisplay = 'none';
        this.teacherForm.patchValue({
            userId: user.id,
            fullName: user.firstName + ' ' + user.lastName,
        });
        if (firstName && !firstName.value) {
            this.teacherForm.patchValue({
                firstName: user.firstName,
            });
        }
        if (lastName && !lastName.value) {
            this.teacherForm.patchValue({
                lastName: user.lastName,
            });
        }
    }
    getParam() {
        return {
            page: 1,
            pageSize: 10,
            orderby: 'DESC',
            status: '',
            search: '',
            fieldName: '',
            userType: 'teacher',
        };
    }
    loadTeacher(teacherId) {
        this.userService.loadUser(teacherId, 'teacher').subscribe((result) => {
            const resp = result;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.teacher = resp.body.data;
                this.position = this.teacher.position.id;
                this.academicDiscipline = this.teacher.academicDiscipline.id;
                this.teacherForm.patchValue({
                    id: this.teacher.id,
                    firstName: this.teacher.firstName,
                    lastName: this.teacher.lastName,
                    position: this.teacher.position.id,
                    occupation: this.teacher.occupation,
                    employmentCode: this.teacher.teacherCode,
                    gender: this.teacher.gender,
                    picture: this.teacher.picture,
                    userId: this.teacher.userId,
                    middleNames: this.teacher.middleNames,
                    phoneNumber: this.teacher.phoneNumber,
                    emailAddress: this.teacher.emailAddress,
                    birthDate: Object(app_utils__WEBPACK_IMPORTED_MODULE_7__["DateFormatter"])(this.teacher.birthDate, 'YYYY/MM/DD', false),
                    userType: 'teacher',
                    academicDiscipline: this.teacher.academicDiscipline.id,
                    fullName: this.teacher.fullName,
                    address: this.teacher.addressVO,
                });
            }
        }, error => {
            this.showMessage.error = true;
            this.showMessage.success = false;
            this.showMessage.message = error ? error.error.message : '';
        });
    }
    loadPositionList() {
        this.positionService.loadPositionList(this.getParam()).subscribe((result) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.showMessage.error = false;
                this.positions = data.data;
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
    loadAcademicDisciplineList() {
        this.academicDisciplineService.loadAcademicDisciplineList(this.getParam()).subscribe((result) => {
            const resp = result;
            const data = resp.body;
            const status = resp.status;
            if (status !== null && status === 200) {
                this.showMessage.error = false;
                this.academicDisciplines = data.data;
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
    onAddressChange(addressWrapper) {
        if (!addressWrapper.isValid) {
            this.teacherForm.controls['address'].setErrors({ invalidAddress: true });
        }
        else {
            this.teacherForm.controls['address'].setErrors(null);
            this.teacherForm.patchValue({ address: addressWrapper.address });
        }
    }
};
TeacherCreateComponent.ctorParameters = () => [
    { type: app_services_positions__WEBPACK_IMPORTED_MODULE_5__["PositionService"] },
    { type: app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_6__["AcademicDisciplineService"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDateService"] },
    { type: app_services_users__WEBPACK_IMPORTED_MODULE_4__["UserService"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogRef"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", String)
], TeacherCreateComponent.prototype, "title", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
], TeacherCreateComponent.prototype, "onUserCreationSuccess", void 0);
TeacherCreateComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-teacher-create',
        template: __webpack_require__(/*! raw-loader!./teacher-create.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/users/teachers/create/teacher-create.component.html"),
        styles: [__webpack_require__(/*! ./teacher-create.component.scss */ "./src/app/features/users/teachers/create/teacher-create.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_positions__WEBPACK_IMPORTED_MODULE_5__["PositionService"],
        app_services_academic_disciplines__WEBPACK_IMPORTED_MODULE_6__["AcademicDisciplineService"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDateService"],
        app_services_users__WEBPACK_IMPORTED_MODULE_4__["UserService"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_3__["NbDialogRef"]])
], TeacherCreateComponent);



/***/ }),

/***/ "./src/app/features/users/teachers/teachers-routing.module.ts":
/*!********************************************************************!*\
  !*** ./src/app/features/users/teachers/teachers-routing.module.ts ***!
  \********************************************************************/
/*! exports provided: TeachersRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TeachersRoutingModule", function() { return TeachersRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var app_services_guards_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/services/guards/auth-guard.service */ "./src/app/services/guards/auth-guard.service.ts");
/* harmony import */ var _teachers_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./teachers.component */ "./src/app/features/users/teachers/teachers.component.ts");
/* harmony import */ var _create_teacher_create_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./create/teacher-create.component */ "./src/app/features/users/teachers/create/teacher-create.component.ts");
/* harmony import */ var _views_teachers_view_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./views/teachers-view.component */ "./src/app/features/users/teachers/views/teachers-view.component.ts");

// Modules


// Service

// Components



// Utilities
const routes = [
    {
        path: '',
        canActivateChild: [app_services_guards_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__["AuthGuard"]],
        component: _teachers_component__WEBPACK_IMPORTED_MODULE_4__["TeachersComponent"],
        children: [
            {
                path: '',
                component: _views_teachers_view_component__WEBPACK_IMPORTED_MODULE_6__["TeachersViewComponent"],
            },
            {
                path: 'create',
                component: _create_teacher_create_component__WEBPACK_IMPORTED_MODULE_5__["TeacherCreateComponent"],
            },
        ],
    },
];
let TeachersRoutingModule = class TeachersRoutingModule {
};
TeachersRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forChild(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]],
    })
], TeachersRoutingModule);



/***/ }),

/***/ "./src/app/features/users/teachers/teachers.component.ts":
/*!***************************************************************!*\
  !*** ./src/app/features/users/teachers/teachers.component.ts ***!
  \***************************************************************/
/*! exports provided: TeachersComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TeachersComponent", function() { return TeachersComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");


let TeachersComponent = class TeachersComponent {
};
TeachersComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-teachers',
        template: `
    <router-outlet></router-outlet>
  `,
    })
], TeachersComponent);



/***/ }),

/***/ "./src/app/features/users/teachers/teachers.module.ts":
/*!************************************************************!*\
  !*** ./src/app/features/users/teachers/teachers.module.ts ***!
  \************************************************************/
/*! exports provided: TeachersModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TeachersModule", function() { return TeachersModule; });
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
/* harmony import */ var _teachers_routing_module__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./teachers-routing.module */ "./src/app/features/users/teachers/teachers-routing.module.ts");
/* harmony import */ var _teachers_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./teachers.component */ "./src/app/features/users/teachers/teachers.component.ts");
/* harmony import */ var _create_teacher_create_component__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./create/teacher-create.component */ "./src/app/features/users/teachers/create/teacher-create.component.ts");
/* harmony import */ var _views_teachers_view_component__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./views/teachers-view.component */ "./src/app/features/users/teachers/views/teachers-view.component.ts");















const COMPONENTS = [
    _teachers_component__WEBPACK_IMPORTED_MODULE_12__["TeachersComponent"],
    _views_teachers_view_component__WEBPACK_IMPORTED_MODULE_14__["TeachersViewComponent"],
    _create_teacher_create_component__WEBPACK_IMPORTED_MODULE_13__["TeacherCreateComponent"],
];
const ENTRY_COMPONENTS = [
    _create_teacher_create_component__WEBPACK_IMPORTED_MODULE_13__["TeacherCreateComponent"],
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
    _teachers_routing_module__WEBPACK_IMPORTED_MODULE_11__["TeachersRoutingModule"],
];
const SERVICES = [];
let TeachersModule = class TeachersModule {
};
TeachersModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
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
], TeachersModule);



/***/ }),

/***/ "./src/app/features/users/teachers/views/teachers-view.component.scss":
/*!****************************************************************************!*\
  !*** ./src/app/features/users/teachers/views/teachers-view.component.scss ***!
  \****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "table {\n  width: 100%; }\n\nbutton {\n  cursor: pointer;\n  margin: 0;\n  border: none; }\n\nbutton:focus {\n  outline: none; }\n\nbutton:hover {\n  background-color: rgba(0, 0, 0, 0.03); }\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIi9Vc2Vycy9vbmtvbWFueWEvcHJvamVjdHMvSmVyb3RvbWEvc21zeXN0ZW0vc3JjL2FwcC9mZWF0dXJlcy91c2Vycy90ZWFjaGVycy92aWV3cy90ZWFjaGVycy12aWV3LmNvbXBvbmVudC5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsV0FBVyxFQUFBOztBQUViO0VBQ0UsZUFBZTtFQUNmLFNBQVM7RUFDVCxZQUFZLEVBQUE7O0FBR2Q7RUFDRSxhQUFhLEVBQUE7O0FBRWY7RUFDRSxxQ0FBcUMsRUFBQSIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3VzZXJzL3RlYWNoZXJzL3ZpZXdzL3RlYWNoZXJzLXZpZXcuY29tcG9uZW50LnNjc3MiLCJzb3VyY2VzQ29udGVudCI6WyJ0YWJsZSB7XG4gIHdpZHRoOiAxMDAlO1xufVxuYnV0dG9uIHtcbiAgY3Vyc29yOiBwb2ludGVyO1xuICBtYXJnaW46IDA7XG4gIGJvcmRlcjogbm9uZTtcbn1cblxuYnV0dG9uOmZvY3VzIHtcbiAgb3V0bGluZTogbm9uZTtcbn1cbmJ1dHRvbjpob3ZlciB7XG4gIGJhY2tncm91bmQtY29sb3I6IHJnYmEoMCwgMCwgMCwgMC4wMyk7O1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/features/users/teachers/views/teachers-view.component.ts":
/*!**************************************************************************!*\
  !*** ./src/app/features/users/teachers/views/teachers-view.component.ts ***!
  \**************************************************************************/
/*! exports provided: TeachersViewComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TeachersViewComponent", function() { return TeachersViewComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _nebular_theme__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @nebular/theme */ "./node_modules/@nebular/theme/fesm2015/index.js");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm2015/paginator.js");
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/sort */ "./node_modules/@angular/material/esm2015/sort.js");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm2015/table.js");
/* harmony import */ var app_services_users__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! app/services/users */ "./src/app/services/users/index.ts");
/* harmony import */ var _create_teacher_create_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../create/teacher-create.component */ "./src/app/features/users/teachers/create/teacher-create.component.ts");
/* harmony import */ var app_features_users_user_delete_user_delete_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! app/features/users/user-delete/user-delete.component */ "./src/app/features/users/user-delete/user-delete.component.ts");









let TeachersViewComponent = class TeachersViewComponent {
    constructor(userService, dialogService) {
        this.userService = userService;
        this.dialogService = dialogService;
        this.title = 'Teacher\'s List';
        this.hidePageSize = false;
        this.totalNumberOfItems = 20;
        this.pageSizeOptions = [10, 20, 30, 50, 70, 100];
        this.displayedColumns = ['id', 'fullName', 'gender', 'academicDiscipline', 'position', 'action'];
        this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_5__["MatTableDataSource"]();
        this.param = {
            page: 1,
            pageSize: 10,
            orderby: 'DESC',
            status: '',
            search: '',
            fieldName: '',
            userType: 'teacher',
        };
        this.teachers = [];
    }
    ngOnInit() {
        this.loadUsers();
    }
    open() {
        this.dialogService.open(_create_teacher_create_component__WEBPACK_IMPORTED_MODULE_7__["TeacherCreateComponent"], {
            context: {
                title: 'Add New Teacher',
            },
        }).onClose.subscribe(data => {
            this.loadUsers();
        });
    }
    edit(teacher) {
        this.dialogService.open(_create_teacher_create_component__WEBPACK_IMPORTED_MODULE_7__["TeacherCreateComponent"], {
            context: {
                title: 'Edit Teacher',
                action: 'edit',
                teacherId: teacher.id.toString(),
            },
        }).onClose.subscribe(_data => {
            this.loadUsers();
        });
    }
    delete(teacher) {
        this.dialogService.open(app_features_users_user_delete_user_delete_component__WEBPACK_IMPORTED_MODULE_8__["UserDeleteComponent"], {
            context: {
                title: 'Delete Teacher',
                action: 'delete',
                userType: 'teacher',
                userId: teacher.id.toString(),
                name: teacher.fullName,
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
                this.dataSource = new _angular_material_table__WEBPACK_IMPORTED_MODULE_5__["MatTableDataSource"](data.teachers);
            }
        });
    }
};
TeachersViewComponent.ctorParameters = () => [
    { type: app_services_users__WEBPACK_IMPORTED_MODULE_6__["UserService"] },
    { type: _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogService"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_paginator__WEBPACK_IMPORTED_MODULE_3__["MatPaginator"])
], TeachersViewComponent.prototype, "paginator", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_angular_material_sort__WEBPACK_IMPORTED_MODULE_4__["MatSort"], { static: true }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_material_sort__WEBPACK_IMPORTED_MODULE_4__["MatSort"])
], TeachersViewComponent.prototype, "sort", void 0);
TeachersViewComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-teachers-view',
        template: __webpack_require__(/*! raw-loader!./teachers-view.component.html */ "./node_modules/raw-loader/index.js!./src/app/features/users/teachers/views/teachers-view.component.html"),
        styles: [__webpack_require__(/*! ./teachers-view.component.scss */ "./src/app/features/users/teachers/views/teachers-view.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [app_services_users__WEBPACK_IMPORTED_MODULE_6__["UserService"],
        _nebular_theme__WEBPACK_IMPORTED_MODULE_2__["NbDialogService"]])
], TeachersViewComponent);



/***/ })

}]);
//# sourceMappingURL=teachers-teachers-module.js.map