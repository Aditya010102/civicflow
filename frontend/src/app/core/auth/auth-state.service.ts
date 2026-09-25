import {
  Injectable,
  signal
} from '@angular/core';

import { AuthState } from './auth-state.model';
import { TokenStorageService } from './token-storage.service';

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

  constructor(
    private readonly tokenStorage: TokenStorageService
  ) { }

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

    this.tokenStorage.removeToken();

    this.state.set({
      authenticated: false,
      accessToken: null
    });
  }

  restoreSession(): void {

    const accessToken =
      this.tokenStorage.getToken();

    if (accessToken) {

      this.setAuthenticated(
        accessToken
      );

      return;
    }

    this.clearAuthentication();
  }
}