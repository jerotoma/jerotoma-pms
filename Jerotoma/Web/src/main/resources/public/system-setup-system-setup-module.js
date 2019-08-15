(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["system-setup-system-setup-module"],{

/***/ "./src/app/features/system-setup/system-setup-routing.module.ts":
/*!**********************************************************************!*\
  !*** ./src/app/features/system-setup/system-setup-routing.module.ts ***!
  \**********************************************************************/
/*! exports provided: SystemSetupRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SystemSetupRoutingModule", function() { return SystemSetupRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var app_services_guards_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/services/guards/auth-guard.service */ "./src/app/services/guards/auth-guard.service.ts");
/* harmony import */ var _system_setup_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./system-setup.component */ "./src/app/features/system-setup/system-setup.component.ts");
/* harmony import */ var app_features_miscellaneous_not_found_not_found_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! app/features/miscellaneous/not-found/not-found.component */ "./src/app/features/miscellaneous/not-found/not-found.component.ts");






const routes = [{
        path: '',
        canActivateChild: [app_services_guards_auth_guard_service__WEBPACK_IMPORTED_MODULE_3__["AuthGuard"]],
        component: _system_setup_component__WEBPACK_IMPORTED_MODULE_4__["SystemSetupComponent"],
        children: [
            {
                path: 'positions',
                loadChildren: () => Promise.all(/*! import() | positions-positions-module */[__webpack_require__.e("default~academic-disciplines-academic-disciplines-module~positions-positions-module"), __webpack_require__.e("common"), __webpack_require__.e("positions-positions-module")]).then(__webpack_require__.bind(null, /*! ./positions/positions.module */ "./src/app/features/system-setup/positions/positions.module.ts"))
                    .then(m => m.PositionsModule),
            },
            {
                path: 'academic-disciplines',
                loadChildren: () => Promise.all(/*! import() | academic-disciplines-academic-disciplines-module */[__webpack_require__.e("default~academic-disciplines-academic-disciplines-module~positions-positions-module"), __webpack_require__.e("common"), __webpack_require__.e("academic-disciplines-academic-disciplines-module")]).then(__webpack_require__.bind(null, /*! ./academic-disciplines/academic-disciplines.module */ "./src/app/features/system-setup/academic-disciplines/academic-disciplines.module.ts"))
                    .then(m => m.AcademicDisciplinesModule),
            },
            {
                path: '',
                redirectTo: 'system-setup',
                pathMatch: 'full',
            },
            {
                path: '**',
                component: app_features_miscellaneous_not_found_not_found_component__WEBPACK_IMPORTED_MODULE_5__["NotFoundComponent"],
            },
        ],
    }];
let SystemSetupRoutingModule = class SystemSetupRoutingModule {
};
SystemSetupRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]],
    })
], SystemSetupRoutingModule);



/***/ }),

/***/ "./src/app/features/system-setup/system-setup.component.scss":
/*!*******************************************************************!*\
  !*** ./src/app/features/system-setup/system-setup.component.scss ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2ZlYXR1cmVzL3N5c3RlbS1zZXR1cC9zeXN0ZW0tc2V0dXAuY29tcG9uZW50LnNjc3MifQ== */"

/***/ }),

/***/ "./src/app/features/system-setup/system-setup.component.ts":
/*!*****************************************************************!*\
  !*** ./src/app/features/system-setup/system-setup.component.ts ***!
  \*****************************************************************/
/*! exports provided: SystemSetupComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SystemSetupComponent", function() { return SystemSetupComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");


let SystemSetupComponent = class SystemSetupComponent {
};
SystemSetupComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-positions',
        template: `<router-outlet></router-outlet>`,
        styles: [__webpack_require__(/*! ./system-setup.component.scss */ "./src/app/features/system-setup/system-setup.component.scss")]
    })
], SystemSetupComponent);



/***/ }),

/***/ "./src/app/features/system-setup/system-setup.module.ts":
/*!**************************************************************!*\
  !*** ./src/app/features/system-setup/system-setup.module.ts ***!
  \**************************************************************/
/*! exports provided: SystemSetupModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SystemSetupModule", function() { return SystemSetupModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var app_shared__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! app/shared */ "./src/app/shared/index.ts");
/* harmony import */ var app_features_miscellaneous_miscellaneous_module__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! app/features/miscellaneous/miscellaneous.module */ "./src/app/features/miscellaneous/miscellaneous.module.ts");
/* harmony import */ var _system_setup_routing_module__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./system-setup-routing.module */ "./src/app/features/system-setup/system-setup-routing.module.ts");
/* harmony import */ var _system_setup_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./system-setup.component */ "./src/app/features/system-setup/system-setup.component.ts");







const COMPONENTS = [
    _system_setup_component__WEBPACK_IMPORTED_MODULE_6__["SystemSetupComponent"],
    app_shared__WEBPACK_IMPORTED_MODULE_3__["TinyMCEComponent"],
];
const ENTRY_COMPONENTS = [];
const MODULES = [
    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormsModule"],
    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["ReactiveFormsModule"],
    _system_setup_routing_module__WEBPACK_IMPORTED_MODULE_5__["SystemSetupRoutingModule"],
    app_features_miscellaneous_miscellaneous_module__WEBPACK_IMPORTED_MODULE_4__["MiscellaneousModule"],
];
const SERVICES = [];
let SystemSetupModule = class SystemSetupModule {
};
SystemSetupModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
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
], SystemSetupModule);



/***/ })

}]);
//# sourceMappingURL=system-setup-system-setup-module.js.map