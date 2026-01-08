import { Routes } from '@angular/router';
import { Attendance } from './pages/attendance/attendance';
import { Payroll } from './pages/payroll/payroll';
import { Dashboard } from './pages/dashboard/dashboard';
import { Employee } from './pages/employee/employee';
import { Helps } from './pages/helps/helps';
import { Recruitment } from './pages/recruitment/recruitment';
import { Report } from './pages/report/report';
import { Schedule } from './pages/schedule/schedule';
import { Message } from './pages/message/message';
import { Settings } from './pages/settings/settings';
import { Notification } from './pages/notification/notification';
import { Profile } from './pages/profile/profile';

export const routes: Routes = [

    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
    },

  
    {
        path: 'dashboard',
        component: Dashboard
    },
    {
        path: 'attendance',
        component: Attendance
    },
    {
        path: 'payroll',
        component: Payroll
    },
    {
        path: 'employees',
        component: Employee
    },

    {
        path: 'helps',
        component: Helps
    },

    {
        path: 'recruitment',
        component: Recruitment
    },

    {
        path: 'report',
        component: Report
    },

    {
        path: 'schedule',
        component: Schedule
    },

    {
        path: 'messages',
        component: Message
    },


    {
        path: 'settings',
        component: Settings
    },


    {
        path: 'notification',
        component: Notification
    },

    {
        path: 'profile',
        component:Profile
    },


    {
        path: '**',
        redirectTo: 'dashboard'
    }
];


