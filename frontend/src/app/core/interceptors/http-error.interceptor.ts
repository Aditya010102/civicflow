import {
    HttpErrorResponse,
    HttpInterceptorFn
} from '@angular/common/http';

import { inject } from '@angular/core';

import { Router } from '@angular/router';

import { catchError } from 'rxjs/operators';

import { throwError } from 'rxjs';

import { AuthStateService } from '../auth/auth-state.service';

export const httpErrorInterceptor: HttpInterceptorFn =
    (req, next) => {

        const authState =
            inject(AuthStateService);

        const router =
            inject(Router);

        return next(req).pipe(

            catchError(
                (error: HttpErrorResponse) => {

                    switch (error.status) {

                        case 401:

                            authState.clearAuthentication();

                            router.navigate(['/login']);

                            break;

                        case 403:

                            console.error(
                                'Access denied:',
                                req.url
                            );

                            break;

                        case 404:

                            console.error(
                                'Resource not found:',
                                req.url
                            );

                            break;

                        case 409:

                            console.error(
                                'Request conflict:',
                                req.url
                            );

                            break;

                        case 500:

                            console.error(
                                'Internal server error:',
                                req.url
                            );

                            break;

                        default:

                            console.error(
                                'HTTP request failed:',
                                error
                            );
                    }

                    return throwError(
                        () => error
                    );
                }
            )
        );
    };