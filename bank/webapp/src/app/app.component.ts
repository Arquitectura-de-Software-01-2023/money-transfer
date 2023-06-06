import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import {Router} from "@angular/router"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  username = '';

  constructor(
    private keycloakService: KeycloakService,
    private router: Router
  ) {
    this.keycloakService.loadUserProfile().then((user) => {
      this.username = user.firstName + ' ' + user.lastName;
    });
  }

  logout() {
    this.router.navigate(['/']).then(() => { this.keycloakService.logout(); });
  }

  login() {
    this.keycloakService.login();
  }
}
