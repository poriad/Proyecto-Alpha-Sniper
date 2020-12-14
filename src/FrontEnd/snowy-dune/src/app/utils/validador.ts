import { DatePipe } from '@angular/common';
import { FormControl, FormGroup } from '@angular/forms';
import { AbstractControl, ValidatorFn } from '@angular/forms';

export function MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
        const control = formGroup.controls[controlName];
        const matchingControl = formGroup.controls[matchingControlName];

        if (matchingControl.errors && !matchingControl.errors.mustMatch) {
            return;
        }

        if (control.value !== matchingControl.value) {
            matchingControl.setErrors({ mustMatch: true });
        } else {
            matchingControl.setErrors(null);
        }
    }
}


export class customValidationService {
    static checkLimit(min: number, max: number): ValidatorFn {
        return (c: AbstractControl): { [key: string]: boolean } | null => {
            if (c.value && (isNaN(c.value) || c.value < min || c.value > max)) {
                return { 'range': true };
            }
            return null;
        };
    }
}

export function requiredFileType(type: string) {

    return function (control: FormControl) {
        const file = control.value;

        if (file) {
            const extension = file.name.split('.')[1].toLowerCase();

            if (type.toLowerCase() !== extension.toLowerCase()) {
                return {
                    requiredFileType: true
                };
            }

            return null;
        }

        return null;
    };

}

export function requiredDate() {

    return function (control: FormControl) {
        var d = new Date();
        var f = new Date(control.value.entryDate)

        if (f <= d) {
            return {

                'requiredDate': true
            };

        }

        return null;

    };

}