import { Component, OnInit} from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection } from 'angularfire2/firestore';
import { Observable } from 'rxjs/Observable';
import { AuthService } from '../core/auth.service';

// custom user interface
interface User {
  uid: string;
  email: string;
  photoURL: string;
  displayName: string;
}

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})

export class ContactsComponent implements OnInit {
  private usersCollection: AngularFirestoreCollection<User>;
  users: Observable<User[]>;
  currentUser: User;
  selectedUser: User;

  constructor(private afs: AngularFirestore,
  private authService: AuthService) { 

    this.usersCollection = this.afs.collection<User>('users');
    this.users = this.usersCollection.valueChanges();

  }

  
  ngOnInit() {    
    this.authService.user.subscribe(user => this.currentUser = user);
  }

  addItem(user: User) {
    this.usersCollection.add(user);
  }

<<<<<<< Updated upstream
  selectChat(user: User) {
    console.log(user.photoURL);
    this.selectedUser = user;
=======
  getUser(email) {
    //nothing yet
>>>>>>> Stashed changes
  }

}
