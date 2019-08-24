import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import {AddressWrapper } from 'app/models/addresses';


@Component({
  selector: 'app-address',
  templateUrl: 'address.component.html',
  styleUrls: ['address.component.scss'],
})
export class AddressComponent implements OnInit {
  @Output() onChanges: EventEmitter<AddressWrapper> = new EventEmitter();

  addressForm: FormGroup;
  addressWrapper: AddressWrapper;

  constructor( private formBuilder: FormBuilder) {}

  ngOnInit() {
      this.loadAddressForm();
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
}
