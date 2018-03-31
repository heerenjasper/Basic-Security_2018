import { Component} from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection } from 'angularfire2/firestore';
import { Observable } from 'rxjs/Observable';

// custom user interface
interface User {
  uid: string;
  email: string;
  photoURL: string;
}

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})

export class ContactsComponent {
  private usersCollection: AngularFirestoreCollection<User>;
  users: Observable<User[]>;

  constructor(private afs: AngularFirestore) { 

    this.usersCollection = this.afs.collection<User>('users');
    this.users = this.usersCollection.valueChanges();

  }

  addItem(user: User) {
    this.usersCollection.add(user);
  }

}
