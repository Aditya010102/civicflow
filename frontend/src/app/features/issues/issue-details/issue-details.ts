import {
    ChangeDetectorRef,
    Component,
    OnInit
} from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { IssueService } from '../services/issue';
import { IssueResponse } from '../../../core/models/issue-response';

@Component({
    selector: 'app-issue-details',
    standalone: true,
    templateUrl: './issue-details.html',
    styleUrl: './issue-details.scss'
})
export class IssueDetailsComponent implements OnInit {

    issue: IssueResponse | null = null;

    isLoading = false;

    errorMessage = '';

    constructor(
        private readonly route: ActivatedRoute,
        private readonly router: Router,
        private readonly issueService: IssueService,
        private readonly changeDetectorRef: ChangeDetectorRef
    ) { }

    ngOnInit(): void {
        this.loadIssue();
    }

    loadIssue(): void {

        const idParam =
            this.route.snapshot.paramMap.get('id');

        if (!idParam) {

            this.errorMessage =
                'Issue ID is missing.';

            this.changeDetectorRef.detectChanges();

            return;
        }

        const issueId =
            Number(idParam);

        if (!Number.isInteger(issueId) || issueId <= 0) {

            this.errorMessage =
                'Invalid issue ID.';

            this.changeDetectorRef.detectChanges();

            return;
        }

        this.isLoading = true;
        this.errorMessage = '';

        this.issueService
            .getIssue(issueId)
            .subscribe({

                next: response => {

                    this.issue = response;

                    this.isLoading = false;

                    this.changeDetectorRef.detectChanges();
                },

                error: error => {

                    console.error(
                        'Failed to load issue:',
                        error
                    );

                    this.errorMessage =
                        'Unable to load the issue. Please try again.';

                    this.isLoading = false;

                    this.changeDetectorRef.detectChanges();
                }

            });
    }

    goBack(): void {

        this.router.navigate(['/issues']);
    }
}