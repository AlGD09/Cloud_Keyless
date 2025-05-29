import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom, LOCALE_ID } from '@angular/core';
import { HttpClientModule, provideHttpClient } from '@angular/common/http';
import { appConfig } from './app/app.config';
import localeDe from '@angular/common/locales/de';
import { registerLocaleData } from '@angular/common';

registerLocaleData(localeDe);

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    ...(appConfig.providers || []),
    importProvidersFrom(HttpClientModule),
    provideHttpClient(),
    { provide: LOCALE_ID, useValue: 'de' } //Set German locale globally
  ]
}).catch(err => console.error(err));
