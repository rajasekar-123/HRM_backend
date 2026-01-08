import { Component, EventEmitter, Output, HostListener } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.html',
  imports: [CommonModule, DatePipe, RouterModule],
  styleUrl: './header.css',
})
export class Header {
  userName = 'Rajasekar';
  currentdate = new Date();
  isDropdownOpen = false;
  @Output() menuClick = new EventEmitter<void>();

  toggleDropdown(event: Event) {
    event.stopPropagation();
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  @HostListener('document:click', ['$event'])
  closeDropdown(event?: Event) {
    this.isDropdownOpen = false;
  }

  logout() {
    console.log('Logging out...');
    // Implement logout logic here
    this.isDropdownOpen = false;
  }
}
