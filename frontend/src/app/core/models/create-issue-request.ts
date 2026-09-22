import { IssuePriority } from './issue-priority';

export interface CreateIssueRequest {
    title: string;
    description: string;
    priority: IssuePriority;
}