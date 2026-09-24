import {
    ChangeDetectorRef,
    Component,
    OnInit
} from '@angular/core';

import { IssueService } from './services/issue';
import { IssueResponse } from '../../core/models/issue-response';

@Component({
    selector: 'app-issues',
    standalone: true,
    templateUrl: './issues.html',
    styleUrl: './issues.scss'
})
export class IssuesComponent implements OnInit {

    issues: IssueResponse[] = [];

    isLoading = false;

    errorMessage = '';

    constructor(
        private readonly issueService: IssueService,
        private readonly changeDetectorRef: ChangeDetectorRef
    ) { }

    ngOnInit(): void {
        this.loadIssues();
    }

    loadIssues(): void {

        this.isLoading = true;
        this.errorMessage = '';

        this.issueService
            .getIssues()
            .subscribe({

                next: response => {

                    this.issues = response.content;

                    this.isLoading = false;

                    this.changeDetectorRef.detectChanges();
                },

                error: error => {

                    console.error(
                        'Failed to load issues:',
                        error
                    );

                    this.errorMessage =
                        'Unable to load issues. Please try again.';

                    this.isLoading = false;

                    this.changeDetectorRef.detectChanges();
                }

            });
    }
}