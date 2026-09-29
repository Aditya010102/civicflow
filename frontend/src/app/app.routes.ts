
import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { AppShellComponent } from './layout/app-shell/app-shell';

export const routes: Routes = [

    // =========================
    // AUTH
    // =========================

    {
        path: 'login',
        loadComponent: () =>
            import('./features/auth/login/login')
                .then(m => m.LoginComponent)
    },


    // =========================
    // PROTECTED APPLICATION
    // =========================

    {
        path: '',
        component: AppShellComponent,
        canActivate: [authGuard],

        children: [

            // Dashboard
            {
                path: 'dashboard',
                loadComponent: () =>
                    import('./features/dashboard/dashboard')
                        .then(m => m.DashboardComponent)
            },

            // Issues
            {
                path: 'issues',
                loadComponent: () =>
                    import('./features/issues/issues')
                        .then(m => m.IssuesComponent)
            },

            // Issue Details
            {
                path: 'issues/:id',
                loadComponent: () =>
                    import('./features/issues/issue-details/issue-details')
                        .then(m => m.IssueDetailsComponent)
            },

            // Default protected page
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'dashboard'
            }
        ]
    },


    // =========================
    // UNKNOWN ROUTES
    // =========================

    {
        path: '**',
        redirectTo: 'dashboard'
    }
];
