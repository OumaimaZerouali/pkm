import {ApplicationConfig, mergeApplicationConfig, provideZoneChangeDetection, SecurityContext} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import {provideServerRendering} from '@angular/platform-server';
import {HttpClient, HttpClientModule, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {MarkdownModule, provideMarkdown} from 'ngx-markdown';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(),
    provideHttpClient(withInterceptorsFromDi()),
    provideMarkdown({ loader: HttpClient, sanitize: SecurityContext.NONE })
  ],
};
