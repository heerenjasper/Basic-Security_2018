import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  public isLoggedIn;

  constructor(private auth : AuthService) { 

    this.auth.isAuthenticated()
      .subscribe(
        result => this.isLoggedIn = result
      )

  }

  logout() {
    this.auth.signOut()
  }

  ngOnInit() {
  }

}
