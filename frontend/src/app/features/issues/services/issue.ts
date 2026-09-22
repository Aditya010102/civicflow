import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

import { CreateIssueRequest } from '../../../core/models/create-issue-request';
import { IssueResponse } from '../../../core/models/issue-response';
import { UpdateIssueStatusRequest } from '../../../core/models/update-issue-status-request';
import { PageResponse } from '../../../core/models/page-response';

@Injectable({
  providedIn: 'root'
})
export class IssueService {

  constructor(
    private readonly http: HttpClient
  ) { }

}