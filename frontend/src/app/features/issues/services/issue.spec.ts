import { TestBed } from '@angular/core/testing';

import {
  HttpTestingController,
  provideHttpClientTesting
} from '@angular/common/http/testing';

import {
  provideHttpClient
} from '@angular/common/http';

import { IssueService } from './issue';

import { environment } from '../../../../environments/environment';

import { IssueResponse } from '../../../core/models/issue-response';

import { PageResponse } from '../../../core/models/page-response';

import { IssuePriority } from '../../../core/models/issue-priority';

import { IssueStatus } from '../../../core/models/issue-status';


describe('IssueService', () => {

  let service: IssueService;

  let httpTestingController: HttpTestingController;


  beforeEach(() => {

    TestBed.configureTestingModule({

      providers: [

        IssueService,

        provideHttpClient(),

        provideHttpClientTesting()

      ]

    });


    service =
      TestBed.inject(IssueService);


    httpTestingController =
      TestBed.inject(
        HttpTestingController
      );

  });


  afterEach(() => {

    httpTestingController.verify();

  });


  it('should be created', () => {

    expect(service).toBeTruthy();

  });


  it('should get issues', () => {

    const mockIssue: IssueResponse = {

      id: 4,

      title: 'Broken streetlight',

      description:
        'Streetlight near the community park is not working.',

      priority:
        'HIGH' as IssuePriority,

      status:
        'REPORTED' as IssueStatus,

      createdAt:
        '2026-09-11T19:47:13.460027Z',

      updatedAt:
        '2026-09-11T19:47:13.460027Z'

    };


    const mockResponse:
      PageResponse<IssueResponse> = {

      content: [
        mockIssue
      ],

      totalElements: 1,

      totalPages: 1,

      size: 20,

      number: 0,

      first: true,

      last: true,

      numberOfElements: 1,

      empty: false

    };


    service
      .getIssues()
      .subscribe(response => {

        expect(response)
          .toEqual(mockResponse);

      });


    const request =
      httpTestingController.expectOne(

        req =>

          req.url ===
          `${environment.apiBaseUrl}/issues`

          &&

          req.params.get('page') === '0'

          &&

          req.params.get('size') === '20'

          &&

          req.params.get('sort') ===
          'createdAt,desc'

      );


    expect(
      request.request.method
    ).toBe('GET');


    request.flush(
      mockResponse
    );

  });


  it('should get an issue by id', () => {

    const mockIssue: IssueResponse = {

      id: 4,

      title: 'Broken streetlight',

      description:
        'Streetlight near the community park is not working.',

      priority:
        'HIGH' as IssuePriority,

      status:
        'REPORTED' as IssueStatus,

      createdAt:
        '2026-09-11T19:47:13.460027Z',

      updatedAt:
        '2026-09-11T19:47:13.460027Z'

    };


    service
      .getIssue(4)
      .subscribe(issue => {

        expect(issue)
          .toEqual(mockIssue);

      });


    const request =
      httpTestingController.expectOne(

        `${environment.apiBaseUrl}/issues/4`

      );


    expect(
      request.request.method
    ).toBe('GET');


    request.flush(
      mockIssue
    );

  });

  it('should create an issue', () => {

    const createRequest = {

      title: 'New pothole',

      description:
        'Large pothole near the main road.',

      priority:
        'HIGH' as IssuePriority

    };


    const mockResponse: IssueResponse = {

      id: 5,

      title: 'New pothole',

      description:
        'Large pothole near the main road.',

      priority:
        'HIGH' as IssuePriority,

      status:
        'REPORTED' as IssueStatus,

      createdAt:
        '2026-09-24T10:00:00Z',

      updatedAt:
        '2026-09-24T10:00:00Z'

    };


    service
      .createIssue(createRequest)
      .subscribe(issue => {

        expect(issue)
          .toEqual(mockResponse);

      });


    const request =
      httpTestingController.expectOne(

        `${environment.apiBaseUrl}/issues`

      );


    expect(
      request.request.method
    ).toBe('POST');


    expect(
      request.request.body
    ).toEqual(createRequest);


    request.flush(
      mockResponse
    );

  });

  it('should update issue status', () => {

    const updateRequest = {

      status:
        'IN_PROGRESS' as IssueStatus

    };


    const mockResponse: IssueResponse = {

      id: 4,

      title: 'Broken streetlight',

      description:
        'Streetlight near the community park is not working.',

      priority:
        'HIGH' as IssuePriority,

      status:
        'IN_PROGRESS' as IssueStatus,

      createdAt:
        '2026-09-11T19:47:13.460027Z',

      updatedAt:
        '2026-09-24T10:30:00Z'

    };


    service
      .updateStatus(4, updateRequest)
      .subscribe(issue => {

        expect(issue)
          .toEqual(mockResponse);

      });


    const request =
      httpTestingController.expectOne(

        `${environment.apiBaseUrl}/issues/4/status`

      );


    expect(
      request.request.method
    ).toBe('PUT');


    expect(
      request.request.body
    ).toEqual(updateRequest);


    request.flush(
      mockResponse
    );

  });


});
