package ai.rho.extensions.passwordreset;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class ResetPasswordCodeResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public ResetPasswordCodeResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new ResetPasswordCodeRestResource(session);
    }

    @Override
    public void close() {

    }
}
