export interface DashboardSummaryResponse {

    total: number;

    reported: number;

    acknowledged: number;

    assigned: number;

    inProgress: number;

    resolved: number;

    closed: number;

    critical: number;
}