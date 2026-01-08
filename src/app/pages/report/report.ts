import { CommonModule } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-report',
  imports: [CommonModule],
  templateUrl: './report.html',
  styleUrl: './report.css',
})
export class Report implements AfterViewInit{


  reports = [
    { name: 'Attendance Report - Jan', type: 'Attendance', status: 'Ready' },
    { name: 'Payroll Report - Jan', type: 'Payroll', status: 'Ready' },
    { name: 'Recruitment Summary', type: 'Recruitment', status: 'Generating' }
  ];

  ngAfterViewInit(): void {
    this.loadAttendanceChart();
    this.loadPayrollChart();
  }

  loadAttendanceChart() {
    new Chart('attendanceAnalytics', {
      type: 'line',
      data: {
        labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
        datasets: [{
          label: 'Attendance %',
          data: [90, 92, 94, 93],
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

  loadPayrollChart() {
    new Chart('payrollAnalytics', {
      type: 'bar',
      data: {
        labels: ['IT', 'HR', 'Finance', 'Sales'],
        datasets: [{
          label: 'Payroll (₹)',
          data: [200000, 120000, 100000, 80000],
          backgroundColor: '#198754'
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { display: false } }
      }
    });
  }

}
