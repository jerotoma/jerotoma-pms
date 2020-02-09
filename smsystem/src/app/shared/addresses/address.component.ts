import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import {AddressWrapper, Address } from 'app/models';


@Component({
  selector: 'app-address',
  templateUrl: 'address.component.html',
  styleUrls: ['address.component.scss'],
})
export class AddressComponent implements OnInit {

  @Input() isResetForm: boolean = false;
  @Input('address') address: Address = null;
  @Output() onChanges: EventEmitter<AddressWrapper> = new EventEmitter();

  addressForm: FormGroup;
  addressWrapper: AddressWrapper;

  constructor( private formBuilder: FormBuilder) {}

  ngOnInit() {
      this.loadAddressForm();
      if (this.address) {
        this.patchAddressValue(this.address);
      }
  }

  loadAddressForm() {
    this.addressForm = this.formBuilder.group({
      id: [null],
      street: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      state: ['', Validators.required],
      postalCode: ['', Validators.required],
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

  resetForm() {
    this.addressForm.reset();
  }
  patchAddressValue(address: Address) {
    this.addressForm.patchValue({
      id: address.id,
      street: address.street,
      city: address.city,
      country: address.country,
      state: address.state,
      postalCode: address.postalCode,
      unit: address.unit,
    });
  }
}
