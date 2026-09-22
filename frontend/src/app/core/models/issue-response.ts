import { IssuePriority } from './issue-priority';
import { IssueStatus } from './issue-status';

export interface IssueResponse {
    id: number;
    title: string;
    description: string;
    priority: IssuePriority;
    status: IssueStatus;
    createdAt: string;
    updatedAt: string;
}