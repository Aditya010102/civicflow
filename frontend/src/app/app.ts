
import { Component, signal } from '@angular/core';
import {
  Router,
  RouterLink,
  RouterOutlet
} from '@angular/router';

import { AuthStateService } from './core/auth/auth-state.service';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    RouterLink
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {

  protected readonly title =
    signal('frontend');

  constructor(
    public readonly authState: AuthStateService,
    private readonly router: Router
  ) { }

  logout(): void {

    this.authState.clearAuthentication();

    this.router.navigate(['/login']);
  }
}
