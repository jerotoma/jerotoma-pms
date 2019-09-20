import { FormGroup } from '@angular/forms';

// custom validator to check that two fields match
export function CheckBoxValidator(controlName: string) {
    return (formGroup: FormGroup) => {
       const checkBoxControl = formGroup.controls[controlName];
        if (checkBoxControl.errors) {
            // return if another validator has already found an error on the matchingControl
            return;
        }
        if (!checkBoxControl.value) {
          formGroup.controls[controlName].setErrors({mustBeChecked: true});
        } else {
          formGroup.controls[controlName].setErrors(null);
        }
    };
}
