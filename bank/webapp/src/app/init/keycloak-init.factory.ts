import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak(
        keycloak: KeycloakService
    ){
    return () =>
        keycloak.init({
            config: {
                url: 'http://10.218.40.103:8080',
                realm: 'moneytransfer',
                clientId: 'mt-webapp',
            },
            initOptions: {
                checkLoginIframe: false
            }
        });
}