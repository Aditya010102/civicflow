import {
    ChangeDetectorRef,
    Component,
    OnInit
} from '@angular/core';

import { RouterLink } from '@angular/router';

import { DashboardService } from './dashboard.service';

import { DashboardSummaryResponse } from '../../core/models/dashboard-summary-response';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        RouterLink
    ],
    templateUrl: './dashboard.html',
    styleUrl: './dashboard.scss'
})
export class DashboardComponent implements OnInit {

    summary: DashboardSummaryResponse | null = null;

    isLoading = false;

    errorMessage = '';

    constructor(
        private readonly dashboardService: DashboardService,
        private readonly changeDetectorRef: ChangeDetectorRef
    ) { }

    ngOnInit(): void {

        this.loadDashboard();
    }

    loadDashboard(): void {

        this.isLoading = true;

        this.errorMessage = '';

        this.dashboardService
            .getSummary()
            .subscribe({

                next: response => {

                    this.summary = response;

                    this.isLoading = false;

                    this.changeDetectorRef.detectChanges();
                },

                error: error => {

                    console.error(
                        'Failed to load dashboard:',
                        error
                    );

                    this.errorMessage =
                        'Unable to load dashboard data.';

                    this.isLoading = false;

                    this.changeDetectorRef.detectChanges();
                }

            });
    }
}