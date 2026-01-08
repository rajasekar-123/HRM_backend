import { Component, EventEmitter, Output } from '@angular/core';
import { RouterLink, RouterModule } from "@angular/router";

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink,RouterModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {


  @Output() linkClick = new EventEmitter<void>();

  closeMobile() {
    this.linkClick.emit();
  }
}
