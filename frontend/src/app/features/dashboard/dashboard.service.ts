import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { DashboardSummaryResponse } from '../../core/models/dashboard-summary-response';

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    private readonly apiUrl =
        'http://localhost:8080/api/dashboard';

    constructor(
        private readonly http: HttpClient
    ) { }

    getSummary(): Observable<DashboardSummaryResponse> {

        return this.http.get<DashboardSummaryResponse>(
            `${this.apiUrl}/summary`
        );
    }
}