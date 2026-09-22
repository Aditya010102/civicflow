import {
  Injectable,
  signal
} from '@angular/core';

import { AuthState } from './auth-state.model';

@Injectable({
  providedIn: 'root'
})
export class AuthStateService {

  private readonly state =
    signal<AuthState>({
      authenticated: false,
      accessToken: null
    });

  readonly authState =
    this.state.asReadonly();

  isAuthenticated(): boolean {
    return this.state().authenticated;
  }

  getAccessToken(): string | null {
    return this.state().accessToken;
  }

  setAuthenticated(
    accessToken: string
  ): void {

    this.state.set({
      authenticated: true,
      accessToken
    });
  }

  clearAuthentication(): void {

    this.state.set({
      authenticated: false,
      accessToken: null
    });
  }
}