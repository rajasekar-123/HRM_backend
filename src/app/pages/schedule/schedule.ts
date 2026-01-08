import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-schedule',
  imports: [CommonModule],
  templateUrl: './schedule.html',
  styleUrl: './schedule.css',
})
export class Schedule {

  schedules = [
    {
      title: 'Team Standup',
      type: 'Meeting',
      date: '08 Jan 2026',
      time: '10:00 AM - 10:30 AM',
      department: 'IT'
    },
    {
      title: 'HR Interview',
      type: 'Interview',
      date: '08 Jan 2026',
      time: '11:30 AM - 12:30 PM',
      department: 'HR'
    },
    {
      title: 'Payroll Review',
      type: 'Meeting',
      date: '09 Jan 2026',
      time: '02:00 PM - 03:00 PM',
      department: 'Finance'
    },
    {
      title: 'Sales Shift',
      type: 'Shift',
      date: '09 Jan 2026',
      time: '09:00 AM - 06:00 PM',
      department: 'Sales'
    }
  ];

}
