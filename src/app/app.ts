import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './layout/header/header';
import { Sidebar } from './layout/sidebar/sidebar';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Header,Sidebar,CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  
  mobile = false;


  togglesidebar()
  {
    this.mobile = !this.mobile;
  }

  closesidebar()
  {
    this.mobile = false
  }
}
