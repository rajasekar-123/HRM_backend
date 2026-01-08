import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-recruitment',
  imports: [CommonModule],
  templateUrl: './recruitment.html',
  styleUrl: './recruitment.css',
})
export class Recruitment {

  openPositions = 5;

  jobs = [
    { role: 'Java Developer', department: 'IT', openings: 2, status: 'Open' },
    { role: 'HR Executive', department: 'HR', openings: 1, status: 'Open' },
    { role: 'Accountant', department: 'Finance', openings: 1, status: 'On Hold' },
    { role: 'Sales Executive', department: 'Sales', openings: 1, status: 'Open' }
  ];

  candidates = [
    { name: 'Rahul Sharma', role: 'Java Developer', stage: 'Interview' },
    { name: 'Anita Verma', role: 'HR Executive', stage: 'Screening' },
    { name: 'Karan Patel', role: 'Sales Executive', stage: 'Offer' },
    { name: 'Neha Singh', role: 'Java Developer', stage: 'Hired' }
  ];

  ngAfterViewInit(): void {
    this.loadPipelineChart();
  }

  loadPipelineChart() {
    new Chart('pipelineChart', {
      type: 'bar',
      data: {
        labels: ['Applied', 'Screening', 'Interview', 'Offer', 'Hired'],
        datasets: [{
          label: 'Candidates',
          data: [25, 18, 10, 5, 3],
          backgroundColor: '#6f42c1'
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { display: false } }
      }
    });
  }

}
