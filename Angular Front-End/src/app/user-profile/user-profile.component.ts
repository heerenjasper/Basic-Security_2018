import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
//import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  signupForm: FormGroup;
  detailForm: FormGroup;

  constructor(public fb: FormBuilder, public auth: AuthService) { }

  ngOnInit() {

    // First Step
    this.signupForm = this.fb.group({
      'email': ['', [
        Validators.required,
        Validators.email
        ]
      ],
      'password': ['', [
        Validators.required
        ]
      ],
      'image': ['', [
        ]
      ],
      'displayName': ['', [
        Validators.required
        ]
      ],
    });
    
  }

  // Using getters will make your code look pretty
  get email() { return this.signupForm.get('email') }
  get password() { return this.signupForm.get('password') }
  get image() { return this.signupForm.get('image') }
  get displayName() { return this.signupForm.get('displayName') }

  // Step 1
  signup() {
    return this.auth.emailSignUp(this.email.value, this.password.value, this.image.value, this.displayName.value);
  }
}