import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth.service';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(public fb: FormBuilder, public auth: AuthService, private router: Router) { }

  ngOnInit(): void {

    this.loginForm = this.fb.group({
      'email': ['', [
        Validators.required,
        Validators.email
        ]
      ],
      'password': ['', [
        Validators.required
        ]
      ],
    });

  }

  get email() { return this.loginForm.get('email') }

  get password() { return this.loginForm.get('password') }

  login() {
      console.log("TEST");
      this.auth.emailLogin(this.email.value, this.password.value)
      .subscribe(
        _success => alert("successfully logged in."),
        error => alert(error)
      );
  }

  goToChat() {
    this.router.navigate(['/chat']);
  }

}