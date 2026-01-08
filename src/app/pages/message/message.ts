import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
interface MessageComponent {
  id: number;
  sender: string;
  subject: string;
  content: string;
  time: string;
  read: boolean;
}
@Component({
  selector: 'app-message',
  imports: [CommonModule],
  templateUrl: './message.html',
  styleUrl: './message.css',
})
export class Message {

  messages: MessageComponent[] = [
    {
      id: 1,
      sender: 'HR Admin',
      subject: 'Welcome',
      content: 'Welcome to the HRM system.',
      time: '10:30 AM',
      read: false
    },
    {
      id: 2,
      sender: 'Manager',
      subject: 'Meeting',
      content: 'Team meeting at 4 PM.',
      time: '09:15 AM',
      read: true
    }
  ];

  selectedMessage?: MessageComponent;

  openMessage(message: MessageComponent) {
    this.selectedMessage = message;
    message.read = true;
  }
}


