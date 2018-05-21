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

  @Input() selectedUser: User;
  @Input() selectedUserMessages: Object[];
  currentUser: User;
  message = "";
  selectedFile: File;
  fileId: string;

  constructor(private afs: AngularFirestore,
      private auth: AuthService,
      private messageService: MessageService) { }

  ngOnInit() {
    this.auth.user.subscribe(user => {
      this.currentUser = user;
    });
  }

  postMessage() {
    this.messageService.encryptMessage(this.message, this.selectedFile)
    .subscribe(event => {
      if (event.type === HttpEventType.Response && event.body) {
        this.fileId = JSON.parse(event.body.toString()).id;
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
      console.log('fileID2 = ', this.fileId);
      this.afs.collection("messages").add({
        fileId: this.fileId,
        senderId: this.currentUser.uid,
        receiverId: this.selectedUser.uid
      })
      .then(function(docRef) {
        console.log("Document written with ID: ", docRef.id);
      })
      .catch(function(error) {
        console.error("Error adding document: ", error);
      });
    }
  }

  getImagePath(object: Object): String {
    return 'http://localhost:8081/api/files/image/' + object['fileId'];
  }

}
