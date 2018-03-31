import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  public isLoggedIn;

  ngOnInit(): void {
  }

  constructor(private auth: AuthService, private router: Router) {
    this.auth.isAuthenticated()
      .subscribe(
        success => this.isLoggedIn = success
      );
  }

  signOut() {
    this.auth.signOut();
    this.router.navigate(['/']);
  }

  goChat() {
    this.router.navigate(['/chat']);
  }

}
