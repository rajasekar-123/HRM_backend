import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables)
@Component({
  selector: 'app-payroll',
  imports: [CommonModule],
  templateUrl: './payroll.html',
  styleUrl: './payroll.css',
})
export class Payroll {


  totalPayroll = 500000;
  paidAmount = 485000;
  pendingAmount = 15000;

  payrollList = [
    { name: 'John Smith', department: 'IT', salary: 75000, status: 'Paid' },
    { name: 'Amy Wilson', department: 'HR', salary: 55000, status: 'Paid' },
    { name: 'Robert Lee', department: 'Finance', salary: 65000, status: 'Pending' },
    { name: 'Sara Khan', department: 'Sales', salary: 60000, status: 'Paid' }
  ];

  ngAfterViewInit(): void {
    this.loadPayrollChart();
  }

  loadPayrollChart() {
    new Chart('payrollChart', {
      type: 'doughnut',
      data: {
        labels: ['Paid', 'Pending'],
        datasets: [{
          data: [this.paidAmount, this.pendingAmount],
          backgroundColor: ['#198754', '#ffc107']
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { position: 'bottom' }
        }
      }
    });
  }
}
