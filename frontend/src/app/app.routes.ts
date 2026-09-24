import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: () =>
            import('./features/auth/login/login')
                .then(m => m.LoginComponent)
    },

    {
        path: 'issues',
        canActivate: [authGuard],
        loadComponent: () =>
            import('./features/issues/issues')
                .then(m => m.IssuesComponent)
    },
    {
        path: 'issues/:id',
        canActivate: [authGuard],
        loadComponent: () =>
            import('./features/issues/issue-details/issue-details')
                .then(m => m.IssueDetailsComponent)
    },
];