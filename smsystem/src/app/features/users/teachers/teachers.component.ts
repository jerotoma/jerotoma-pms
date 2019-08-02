import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Teacher } from './../../../models/users';

@Component({
  selector: 'app-stepper',
  templateUrl: 'teachers.component.html',
  styleUrls: ['teachers.component.scss'],
})
export class TeachersComponent implements OnInit {
  title: string = 'Teacher\'s List';
  firstForm: FormGroup;
  secondForm: FormGroup;
  thirdForm: FormGroup;

  columns: string[] = [
    'ID',
    'Full Name',
    'Gender',
    'Occupation',
    'Position',
    '# Of Courses',
    'Action',
  ];

  teachers: Array<Teacher> = [
    {
      id: 1,
      firstName: 'John',
      lastName: 'Moris',
      age: 23,
      gender: 'male',
      occupation: 'Teacher',
      birthDate: new Date(),
      fullName: 'John Moris',
      picture: '/resources/users/moris-john.jpg',
      numberOfCourses: 90,
      position: 'Head Teacher',
    },
    {
      id: 2,
      firstName: 'Hardson',
      lastName: 'Miller',
      age: 45,
      gender: 'male',
      occupation: 'Teacher',
      birthDate: new Date(),
      fullName: 'Hardson Miller',
      picture: '/resources/users/miller-hardson.jpg',
      numberOfCourses: 90,
      position: 'Assistant Head Teacher',
    },
    {
      id: 3,
      firstName: 'Carson',
      lastName: 'Jody',
      age: 23,
      gender: 'female',
      occupation: 'Teacher',
      birthDate: new Date(),
      fullName: 'Carson Jody',
      picture: '/resources/users/carson-jody.jpg',
      numberOfCourses: 90,
      position: 'Normal',
    },
    {
      id: 4,
      firstName: 'Titus',
      lastName: 'Harrison',
      age: 23,
      gender: 'male',
      occupation: 'Teacher',
      birthDate: new Date(),
      fullName: 'Titus Harrison',
      picture: '/resources/users/titus-harrison.jpg',
      numberOfCourses: 90,
      position: 'Normal',
    },
  ];


  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.firstForm = this.fb.group({
      firstCtrl: ['', Validators.required],
    });

    this.secondForm = this.fb.group({
      secondCtrl: ['', Validators.required],
    });

    this.thirdForm = this.fb.group({
      thirdCtrl: ['', Validators.required],
    });
  }

  onFirstSubmit() {
    this.firstForm.markAsDirty();
  }

  onSecondSubmit() {
    this.secondForm.markAsDirty();
  }

  onThirdSubmit() {
    this.thirdForm.markAsDirty();
  }
}
