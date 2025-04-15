import {ApplicationConfig, LOCALE_ID, provideZoneChangeDetection, SecurityContext} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideClientHydration} from '@angular/platform-browser';
import {HttpClient, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {provideMarkdown} from 'ngx-markdown';
import {Configuration, ConfigurationParameters} from '../../libs/api';
import {registerLocaleData} from '@angular/common';
import localeNl from '@angular/common/locales/nl';

registerLocaleData(localeNl);

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(),
    provideHttpClient(withInterceptorsFromDi()),
    provideMarkdown({ loader: HttpClient, sanitize: SecurityContext.NONE }),
    {
      provide: Configuration,
      useFactory: (): Configuration => {
        const params: ConfigurationParameters = {
          basePath: "http://localhost:8080"
        };
        return new Configuration(params);
      }
    },
    { provide: LOCALE_ID, useValue: 'nl' }
  ],
};
