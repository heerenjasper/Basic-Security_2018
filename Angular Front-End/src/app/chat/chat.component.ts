import { Component, OnInit } from '@angular/core';

/* interface Message {
  fileUrl: string;
}
*/

interface User {
  uid: string;
  email: string;
  photoURL: string;
  displayName: string;
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})

export class ChatComponent implements OnInit {

  //private messages: Message[];

  selectedUser: User;

  constructor() { }

  ngOnInit() {}

  receiveUser($event: User) {
    this.selectedUser = $event
  }

}
