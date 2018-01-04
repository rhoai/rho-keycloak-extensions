package ai.rho.extensions.groupuser;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class GroupUserRealmResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public GroupUserRealmResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new GroupUserRestResource(session);
    }

    @Override
    public void close() {
    }
}
