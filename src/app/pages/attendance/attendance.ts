import { CommonModule } from '@angular/common';
import { Component, AfterViewInit } from '@angular/core';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-attendance',
  imports:[CommonModule],
  templateUrl: '../attendance/attendance.html',
  styleUrls: ['../attendance/attendance.css']
})
export class Attendance implements AfterViewInit {

  todayAttendance = 92;

  attendanceList = [
    { name: 'John Smith', department: 'IT', status: 'Present' },
    { name: 'Amy Wilson', department: 'HR', status: 'Leave' },
    { name: 'Robert Lee', department: 'Finance', status: 'Present' },
    { name: 'Sara Khan', department: 'Sales', status: 'Absent' }
  ];

  ngAfterViewInit(): void {
    this.loadAttendanceChart();
  }

  loadAttendanceChart() {
    new Chart('attendanceTrend', {
      type: 'bar',
      data: {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'],
        datasets: [{
          label: 'Attendance %',
          data: [90, 94, 92, 96, 92],
          backgroundColor: '#968aacff'
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { display: false } }
      }
    });
  }
}
