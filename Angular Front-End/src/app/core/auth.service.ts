import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { AngularFireAuth } from 'angularfire2/auth';
import * as firebase from 'firebase/app';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/switchMap'

@Injectable()
export class AuthService {

  public user: Observable<firebase.User>;

  constructor(private afAuth: AngularFireAuth, private router: Router) {
      this.user = this.afAuth.authState;
  }

  Register(email, password) {
    this.afAuth.auth.createUserWithEmailAndPassword(email, password);
  }

  Login(email, password) : Observable<any> {
    return Observable.fromPromise(
      this.afAuth.auth.signInWithEmailAndPassword(email, password)
    );
  }

  signOut() {
    this.afAuth.auth.signOut().then(() => {
        this.router.navigate(['/']);
    });
  }

}