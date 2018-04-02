import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AngularFireAuth } from 'angularfire2/auth';
import { AngularFirestore, AngularFirestoreDocument } from 'angularfire2/firestore';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/switchMap';

// custom user interface
interface User {
  uid: string;
  email: string;
  photoURL: string;
  displayName: string;
}


@Injectable()
export class AuthService {
  user: Observable<User>;

  constructor(private afAuth: AngularFireAuth,
              private afs: AngularFirestore,
              private router: Router) {

      // Define the user observable
      this.user = this.afAuth.authState
        .switchMap(user => {
          if (user) {
            // logged in, get custom user from Firestore
            return this.afs.doc<User>(`users/${user.uid}`).valueChanges()
          } else {
            // logged out, null
            return Observable.of(null)
          }
        })
  }

  //// Email/Password Auth ////
  
  emailSignUp(email: string, password: string, imageUrl: string, displayName: string) {
    return this.afAuth.auth.createUserWithEmailAndPassword(email, password)
      .then(user => {
        console.log(user);
        this.router.navigate(['/']);
        if (imageUrl == "") {
          imageUrl = "http://lasenegalaise.com/forums/styles/canvas/theme/images/no_avatar.jpg"
        }
        return this.setUserDoc(user, imageUrl, displayName) // create initial user document
      })
      .catch(error => this.handleError(error));
  }

  emailLogin(email: string, password: string) : Observable<any> {
    return Observable.fromPromise(
      this.afAuth.auth.signInWithEmailAndPassword(email, password)
    );
  }

  isAuthenticated(): Observable<boolean> {
    return this.user.map(user => user && user.uid !== undefined);
  }

  signOut() {
    this.afAuth.auth.signOut().then(() => {
        this.router.navigate(['/']);
    });
  }

  // Update properties on the user document
  updateUser(user: User, data: any) { 
    return this.afs.doc(`users/${user.uid}`).update(data)
  }

  // If error, console log and notify user
  private handleError(error) {
    console.error(error)
    alert(error.message)
  }

  // Sets user data to firestore after succesful login
  private setUserDoc(user, imageUrl, displayName) {

    const userRef: AngularFirestoreDocument<User> = this.afs.doc(`users/${user.uid}`);

    const data: User = {
      uid: user.uid,
      email: user.email || null,
      photoURL: imageUrl,
      displayName: displayName
    }

    return userRef.set(data)

  }
}