import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';

import { CreateIssueRequest } from '../../../core/models/create-issue-request';
import { IssuePriority } from '../../../core/models/issue-priority';
import { IssueResponse } from '../../../core/models/issue-response';
import { IssueStatus } from '../../../core/models/issue-status';
import { PageResponse } from '../../../core/models/page-response';
import { UpdateIssueStatusRequest } from '../../../core/models/update-issue-status-request';

@Injectable({
  providedIn: 'root'
})
export class IssueService {

  private readonly apiUrl =
    `${environment.apiBaseUrl}/issues`;

  constructor(
    private readonly http: HttpClient
  ) { }

  getIssues(
    page = 0,
    size = 20,
    status?: IssueStatus,
    priority?: IssuePriority,
    departmentId?: number,
    search?: string
  ): Observable<PageResponse<IssueResponse>> {

    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', 'createdAt,desc');

    if (status) {
      params = params.set('status', status);
    }

    if (priority) {
      params = params.set('priority', priority);
    }

    if (departmentId !== undefined) {
      params = params.set(
        'departmentId',
        departmentId
      );
    }

    if (search?.trim()) {
      params = params.set(
        'search',
        search.trim()
      );
    }

    return this.http.get<
      PageResponse<IssueResponse>
    >(
      this.apiUrl,
      { params }
    );
  }

  getIssue(
    id: number
  ): Observable<IssueResponse> {

    return this.http.get<IssueResponse>(
      `${this.apiUrl}/${id}`
    );
  }

  createIssue(
    request: CreateIssueRequest
  ): Observable<IssueResponse> {

    return this.http.post<IssueResponse>(
      this.apiUrl,
      request
    );
  }

  updateStatus(
    id: number,
    request: UpdateIssueStatusRequest
  ): Observable<IssueResponse> {

    return this.http.put<IssueResponse>(
      `${this.apiUrl}/${id}/status`,
      request
    );
  }

  deleteIssue(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );
  }
}