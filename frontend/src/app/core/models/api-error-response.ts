export interface ApiErrorResponse {
    timestamp: string;
    status: number;
    error: string;
    code?: string;
    message: string;
    path: string;
    correlationId?: string;
    fieldErrors?: Record<string, string>;
}