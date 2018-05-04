import { Component, OnInit, Input } from '@angular/core';
import { AngularFirestore } from 'angularfire2/firestore';
import { AuthService } from '../core/auth.service';
import { MessageService } from '../services/message.service';
import { HttpEventType } from '@angular/common/http';

interface User {
  uid: string;
  email: string;
  photoURL: string;
  displayName: string;
}

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {

  constructor(private afs: AngularFirestore, private auth: AuthService, private messageService: MessageService) { }

  ngOnInit() {
  }

  @Input() selectedUser: User;
  message = "";
  selectedFile: File;
  fileURL = "";
  date = new Date();

  postMessage() {
    this.messageService.postMessage(this.message, this.selectedFile)
    .subscribe(event => {
      if (event.type === HttpEventType.Response && event.body) {
        this.fileURL = 'via.placeholder.com/350x150';
        this.addMessage();
      }
    });

  }

  selectSingleFile(event) {
    const fileList = event.target.files;
    this.selectedFile = fileList.item(0);
  }

  addMessage() {
    // Add a new document with a generated id.
    if (this.auth.isAuthenticated) {
      this.afs.collection("messages").add({
        fileURL: this.fileURL,
        time: this.date.getTime
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
