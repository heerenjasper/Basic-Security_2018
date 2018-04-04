import { Component, OnInit } from '@angular/core';
import { AngularFirestore } from 'angularfire2/firestore';
import { AuthService } from '../core/auth.service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {

  constructor(private afs: AngularFirestore, private auth: AuthService) { }

  ngOnInit() {
  }

  message = "";

  addMessage(zenderEmail: string) {
    // Add a new document with a generated id.
    if (this.auth.isAuthenticated) {
      this.afs.collection("messages").add({
        message: this.message,
        ontvangerEmail: "TEST",
        zenderEmail: zenderEmail
      })
      .then(function(docRef) {
        console.log("Document written with ID: ", docRef.id);
      })
      .catch(function(error) {
        console.error("Error adding document: ", error);
      });
    }
  }

}
