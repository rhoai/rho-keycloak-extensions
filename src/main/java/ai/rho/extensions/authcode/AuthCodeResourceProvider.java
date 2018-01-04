package ai.rho.extensions.authcode;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class AuthCodeResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public AuthCodeResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new AuthCodeRestResource(session);
    }

    @Override
    public void close() {
    }
}
