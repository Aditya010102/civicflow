import { inject } from '@angular/core';
import {
    HttpErrorResponse,
    HttpInterceptorFn
} from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthStateService } from '../auth/auth-state.service';
import { TokenStorageService } from '../auth/token-storage.service';



export const authInterceptor: HttpInterceptorFn =
    (req, next) => {

        const tokenStorage =
            inject(TokenStorageService);

        const authState =
            inject(AuthStateService);

        const router =
            inject(Router);

        const token =
            tokenStorage.getToken();

        let authReq = req;

        if (token) {
            authReq = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`
                }
            });
        }

        return next(authReq).pipe(

            catchError((error: HttpErrorResponse) => {

                if (error.status === 401) {

                    tokenStorage.removeToken();

                    authState.clearAuthentication();

                    router.navigate(['/login'], {
                        queryParams: {
                            returnUrl: router.url
                        }
                    });
                }

                return throwError(() => error);
            })

        );
    };