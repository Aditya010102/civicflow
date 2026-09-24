import {
  ApplicationConfig,
  inject,
  provideAppInitializer,
  provideBrowserGlobalErrorListeners
} from '@angular/core';

import { provideRouter } from '@angular/router';

import { routes } from './app.routes';

import {
  provideHttpClient,
  withInterceptors
} from '@angular/common/http';

import { authInterceptor } from './core/interceptors/auth.interceptor';

import { AuthStateService } from './core/auth/auth-state.service';

export const appConfig: ApplicationConfig = {

  providers: [

    provideBrowserGlobalErrorListeners(),

    provideRouter(routes),

    provideHttpClient(
      withInterceptors([
        authInterceptor
      ])
    ),

    provideAppInitializer(() => {

      const authState =
        inject(AuthStateService);

      authState.restoreSession();

    })

  ]

};