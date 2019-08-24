(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["default~editors-editors-module~system-setup-system-setup-module~users-users-module"],{

/***/ "./node_modules/raw-loader/index.js!./src/app/shared/addresses/address.component.html":
/*!***********************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/shared/addresses/address.component.html ***!
  \***********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<form [formGroup]=\"addressForm\">\n  <div class=\"row\">\n      <div class=\"col-md-6\">\n          <div class=\"form-control-group\">\n              <label for=\"address-street\" class=\"label\">Street</label>\n              <input\n                    type=\"text\"\n                    nbInput fullWidth\n                    autocomplete=\"new-password\"\n                    required\n                    id=\"address-street\"\n                    [status]=\"addressForm.controls['street'].invalid && addressForm.controls['street'].touched ? 'danger' : ''\"\n                    formControlName=\"street\"\n                    placeholder=\"Street\">\n              <ng-container *ngIf=\"addressForm.controls['street'].invalid && addressForm.controls['street'].touched\">\n                <p class=\"error-message\">\n                  Street is required!\n                </p>\n              </ng-container>\n          </div>\n      </div>\n      <div class=\"col-md-6\">\n          <div class=\"form-control-group\">\n              <label for=\"address-unit\" class=\"label\">Unit </label>\n              <input\n                    required\n                    type=\"text\"\n                    autocomplete=\"new-password\"\n                    nbInput fullWidth\n                    id=\"address-unit\"\n                    [status]=\"addressForm.controls['unit'].invalid && addressForm.controls['unit'].touched ? 'danger' : ''\"\n                    formControlName=\"unit\"\n                    placeholder=\"Unit/Apartment/Suite\">\n              <ng-container *ngIf=\"addressForm.controls['unit'].invalid && addressForm.controls['unit'].touched\">\n                <p class=\"error-message\">\n                  Unit is required!\n                </p>\n              </ng-container>\n          </div>\n      </div>\n  </div>\n  <div class=\"form-control-group\">\n    <label for=\"address-city\" class=\"label\">City</label>\n    <input\n          required\n          type=\"text\"\n          autocomplete=\"new-password\"\n          nbInput fullWidth\n          id=\"address-city\"\n          [status]=\"addressForm.controls['city'].invalid && addressForm.controls['city'].touched ? 'danger' : ''\"\n          formControlName=\"city\"\n          placeholder=\"City\">\n    <ng-container *ngIf=\"addressForm.controls['city'].invalid && addressForm.controls['city'].touched\">\n      <p class=\"error-message\">\n        City is required!\n      </p>\n    </ng-container>\n  </div>\n  <div class=\"row\">\n      <div class=\"col-md-6\">\n          <div class=\"form-control-group\">\n              <label for=\"address-state\" class=\"label\">State</label>\n              <input\n                    required\n                    type=\"text\"\n                    autocomplete=\"new-password\"\n                    nbInput fullWidth\n                    id=\"address-state\"\n                    [status]=\"addressForm.controls['state'].invalid && addressForm.controls['state'].touched ? 'danger' : ''\"\n                    formControlName=\"state\"\n                    placeholder=\"State\">\n              <ng-container *ngIf=\"addressForm.controls['state'].invalid && addressForm.controls['state'].touched\">\n                <p class=\"error-message\">\n                  State is required!\n                </p>\n              </ng-container>\n          </div>\n      </div>\n      <div class=\"col-md-6\">\n          <div class=\"form-control-group\">\n              <label for=\"address-state\" class=\"label\">Postal Code</label>\n              <input\n                    required\n                    type=\"text\"\n                    autocomplete=\"new-password\"\n                    nbInput fullWidth\n                    id=\"address-postalCode\"\n                    [status]=\"addressForm.controls['postalCode'].invalid && addressForm.controls['postalCode'].touched ? 'danger' : ''\"\n                    formControlName=\"postalCode\"\n                    placeholder=\"Postal Code\">\n              <ng-container *ngIf=\"addressForm.controls['postalCode'].invalid && addressForm.controls['postalCode'].touched\">\n                <p class=\"error-message\">\n                  State is required!\n                </p>\n              </ng-container>\n          </div>\n      </div>\n  </div>\n  <div class=\"form-control-group\">\n    <label for=\"address-country\" class=\"label\">Country</label>\n    <input\n          type=\"text\"\n          nbInput fullWidth\n          autocomplete=\"new-password\"\n          required\n          id=\"address-country\"\n          [status]=\"addressForm.controls['country'].invalid && addressForm.controls['country'].touched ? 'danger' : ''\"\n          formControlName=\"country\"\n          placeholder=\"Country\">\n    <ng-container *ngIf=\"addressForm.controls['country'].invalid && addressForm.controls['country'].touched\">\n      <p class=\"error-message\">\n        Country is required!\n      </p>\n    </ng-container>\n  </div>\n</form>\n"

/***/ }),

/***/ "./node_modules/raw-loader/index.js!./src/app/shared/tables/user-table.component.html":
/*!***********************************************************************************!*\
  !*** ./node_modules/raw-loader!./src/app/shared/tables/user-table.component.html ***!
  \***********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<table class=\"table\" *ngIf=\"tableColumns.length && tableRows.length\">\n    <thead>\n      <tr>\n        <th scope=\"col\" *ngFor=\"let columnName of tableColumns\">{{columnName}}</th>\n      </tr>\n    </thead>\n    <tbody>\n      <tr *ngFor=\"let rowItem of tableRows\">\n        <th scope=\"row\">{{rowItem.id}}</th>\n        <td>{{rowItem.fullName}}</td>\n        <td>{{rowItem.gender}}</td>\n        <td>{{rowItem.academicDiscipline.name}}</td>\n        <td>{{rowItem.position.name}}</td>\n        <td>{{rowItem.numberOfCourses}}</td>\n        <td>\n            <nb-icon icon=\"star\" [options]=\"{ animation: { type: 'zoom' } }\"></nb-icon>\n        </td>\n      </tr>\n    </tbody>\n  </table>\n"

/***/ }),

/***/ "./src/app/shared/addresses/address.component.scss":
/*!*********************************************************!*\
  !*** ./src/app/shared/addresses/address.component.scss ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3NoYXJlZC9hZGRyZXNzZXMvYWRkcmVzcy5jb21wb25lbnQuc2NzcyJ9 */"

/***/ }),

/***/ "./src/app/shared/addresses/address.component.ts":
/*!*******************************************************!*\
  !*** ./src/app/shared/addresses/address.component.ts ***!
  \*******************************************************/
/*! exports provided: AddressComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddressComponent", function() { return AddressComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");



let AddressComponent = class AddressComponent {
    constructor(formBuilder) {
        this.formBuilder = formBuilder;
        this.onChanges = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
    }
    ngOnInit() {
        this.loadAddressForm();
    }
    loadAddressForm() {
        this.addressForm = this.formBuilder.group({
            id: [null],
            street: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            city: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            country: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            state: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            postalCode: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            unit: [null],
        });
        this.processFormChange();
    }
    processFormChange() {
        this.addressForm.valueChanges.subscribe(value => {
            this.addressWrapper = { address: value, isValid: this.addressForm.valid };
            this.onChanges.emit(this.addressWrapper);
        });
    }
};
AddressComponent.ctorParameters = () => [
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"])
], AddressComponent.prototype, "onChanges", void 0);
AddressComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-address',
        template: __webpack_require__(/*! raw-loader!./address.component.html */ "./node_modules/raw-loader/index.js!./src/app/shared/addresses/address.component.html"),
        styles: [__webpack_require__(/*! ./address.component.scss */ "./src/app/shared/addresses/address.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"]])
], AddressComponent);



/***/ }),

/***/ "./src/app/shared/editors/tiny-mce.component.ts":
/*!******************************************************!*\
  !*** ./src/app/shared/editors/tiny-mce.component.ts ***!
  \******************************************************/
/*! exports provided: TinyMCEComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TinyMCEComponent", function() { return TinyMCEComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! environments/environment */ "./src/environments/environment.ts");



let TinyMCEComponent = class TinyMCEComponent {
    constructor(host) {
        this.host = host;
        this.editorKeyup = new _angular_core__WEBPACK_IMPORTED_MODULE_1__["EventEmitter"]();
    }
    ngAfterViewInit() {
        tinymce.init({
            target: this.host.nativeElement,
            plugins: ['link', 'paste', 'table'],
            skin_url: environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].END_POINTS.base + '/assets/skins/lightgray',
            setup: (editor) => {
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
};
TinyMCEComponent.ctorParameters = () => [
    { type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["ElementRef"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Output"])(),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
], TinyMCEComponent.prototype, "editorKeyup", void 0);
TinyMCEComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-mce-editor',
        template: '',
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [_angular_core__WEBPACK_IMPORTED_MODULE_1__["ElementRef"]])
], TinyMCEComponent);



/***/ }),

/***/ "./src/app/shared/index.ts":
/*!*********************************!*\
  !*** ./src/app/shared/index.ts ***!
  \*********************************/
/*! exports provided: UserTableComponent, TinyMCEComponent, AddressComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _tables_user_table_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./tables/user-table.component */ "./src/app/shared/tables/user-table.component.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "UserTableComponent", function() { return _tables_user_table_component__WEBPACK_IMPORTED_MODULE_0__["UserTableComponent"]; });

/* harmony import */ var _editors_tiny_mce_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./editors/tiny-mce.component */ "./src/app/shared/editors/tiny-mce.component.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "TinyMCEComponent", function() { return _editors_tiny_mce_component__WEBPACK_IMPORTED_MODULE_1__["TinyMCEComponent"]; });

/* harmony import */ var _addresses_address_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./addresses/address.component */ "./src/app/shared/addresses/address.component.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "AddressComponent", function() { return _addresses_address_component__WEBPACK_IMPORTED_MODULE_2__["AddressComponent"]; });






/***/ }),

/***/ "./src/app/shared/tables/user-table.component.scss":
/*!*********************************************************!*\
  !*** ./src/app/shared/tables/user-table.component.scss ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3NoYXJlZC90YWJsZXMvdXNlci10YWJsZS5jb21wb25lbnQuc2NzcyJ9 */"

/***/ }),

/***/ "./src/app/shared/tables/user-table.component.ts":
/*!*******************************************************!*\
  !*** ./src/app/shared/tables/user-table.component.ts ***!
  \*******************************************************/
/*! exports provided: UserTableComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserTableComponent", function() { return UserTableComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");


let UserTableComponent = class UserTableComponent {
    constructor() {
        this.tableColumns = [];
        this.tableRows = [];
    }
    ngOnDestroy() { }
};
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])('columns'),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Array)
], UserTableComponent.prototype, "tableColumns", void 0);
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])('rows'),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:type", Object)
], UserTableComponent.prototype, "tableRows", void 0);
UserTableComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-user-table',
        template: __webpack_require__(/*! raw-loader!./user-table.component.html */ "./node_modules/raw-loader/index.js!./src/app/shared/tables/user-table.component.html"),
        styles: [__webpack_require__(/*! ./user-table.component.scss */ "./src/app/shared/tables/user-table.component.scss")]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__metadata"]("design:paramtypes", [])
], UserTableComponent);



/***/ })

}]);
//# sourceMappingURL=default~editors-editors-module~system-setup-system-setup-module~users-users-module.js.map