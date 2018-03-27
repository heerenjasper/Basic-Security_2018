import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {


  ngOnInit(): void {
    throw new Error("Method not implemented.");
  }

  public signinForm : FormGroup;
  constructor(private formBuilder: FormBuilder, public auth: AuthService, private router: Router) {

    this.signinForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  login() {
    const inputValue = this.signinForm.value();
    this.auth.Login(inputValue.email, inputValue.password)
      .subscribe(
        success => this.router.navigate(['/chat']),
        error => alert(error)
      );
  }

}