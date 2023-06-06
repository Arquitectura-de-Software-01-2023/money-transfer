import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClarityModule } from '@clr/angular';
import { CdsModule } from '@cds/angular';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { initializeKeycloak } from './init/keycloak-init.factory';
import { AccountsComponent } from './pages/accounts/accounts.component';
import { TransfersComponent } from './pages/transfers/transfers.component';

import '@cds/core/icon/register.js';
import { ClarityIcons, plusIcon, copyIcon, popOutIcon, downloadIcon } from '@cds/core/icon';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { TransferByAccountComponent } from './pages/transfer-by-account/transfer-by-account.component';

ClarityIcons.addIcons(plusIcon, copyIcon, popOutIcon, downloadIcon);


@NgModule({
  declarations: [
    AppComponent,
    AccountsComponent,
    TransfersComponent,
    TransferByAccountComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ClarityModule,
    CdsModule,
    KeycloakAngularModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
