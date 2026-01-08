import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile {
  user = {
    name: 'Rajasekar',
    role: 'Senior Human Resources Manager',
    email: 'rajasekar.anderson@company.com',
    phone: '+91 9585594009',
    location: 'Chennai, Tamilnadu',
    joinDate: 'Jan 15, 2023',
    department: 'Human Resources',
    bio: 'Experienced HR professional with over 10 years of experience in talent acquisition, employee relations, and organizational development.',
    avatar: 'https://i.pravatar.cc/150?u=barbara',
    cover: 'https://images.unsplash.com/photo-1497366216548-37526070297c?auto=format&fit=crop&q=80&w=1200'
  };

  recentActivity = [
    { type: 'Update', description: 'Updated employee handbook section 4.', date: '2 hours ago' },
    { type: 'Meeting', description: 'Conducted interviews for Junior Designer position.', date: 'Yesterday' },
    { type: 'Task', description: 'Completed performance reviews for the HR team.', date: '2 days ago' }
  ];
}
