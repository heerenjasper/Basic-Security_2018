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
  fileId: number;

  postMessage() {
    this.messageService.encryptMessage(this.message, this.selectedFile)
    .subscribe(event => {
      if (event.type === HttpEventType.Response && event.body) {
        this.fileId = event.body['id'];
        this.addMessage();
      }
    });

  }

  selectSingleFile(event) {
    const fileList = event.target.files;
    this.selectedFile = fileList.item(0);
    console.log('File selected', this.selectedFile.name);
  }

  addMessage() {
    // Add a new document with a generated id.
    if (this.auth.isAuthenticated) {
      this.afs.collection("messages").add({
        fileId: this.fileId
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
