import { Component } from '@angular/core';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);
@Component({
  selector: 'app-dashbaoard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {

  ngAfterViewInit(): void {
    this.loadAttendanceChart();
    this.loadDepartmentChart();
  }

  loadAttendanceChart() {
    new Chart('attendanceChart', {
      type: 'line',
      data: {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'],
        datasets: [{
          label: 'Present Employees',
          data: [90, 95, 92, 98, 96],
          borderColor: '#6f42c1',
          backgroundColor: 'rgba(111,66,193,0.1)',
          fill: true,
          tension: 0.4
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { display: false } }
      }
    });
  }

  loadDepartmentChart() {
    new Chart('departmentChart', {
      type: 'doughnut',
      data: {
        labels: ['IT', 'HR', 'Finance', 'Sales'],
        datasets: [{
          data: [45, 25, 30, 28],
          backgroundColor: [
            '#6f42c1',
            '#198754',
            '#0d6efd',
            '#ffc107'
          ]
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { position: 'bottom' } }
      }
    });
  }
}


