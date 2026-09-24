import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

import { environment } from '../../../environments/environment';

import {
  LoginRequest,
  LoginResponse
} from './auth.models';

import { TokenStorageService } from './token-storage.service';
import { AuthStateService } from './auth-state.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private readonly http: HttpClient,
    private readonly tokenStorage: TokenStorageService,
    private readonly authState: AuthStateService
  ) { }

  login(
    request: LoginRequest
  ): Observable<LoginResponse> {

    return this.http
      .post<LoginResponse>(
        `${environment.apiBaseUrl}/auth/login`,
        request
      )
      .pipe(
        tap(response => {

          this.tokenStorage.saveToken(
            response.accessToken
          );

          this.authState.setAuthenticated(
            response.accessToken
          );

        })
      );
  }

  logout(): void {

    this.tokenStorage.removeToken();

    this.authState.clearAuthentication();
  }
}