package ai.rho.extensions.clientuser;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class ClientUserRealmResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public ClientUserRealmResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new ClientUserRestResource(session);
    }

    @Override
    public void close() {
    }
}
