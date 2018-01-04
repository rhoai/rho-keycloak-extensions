package ai.rho.extensions.safecustomattributes;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class SafeCustomAttributesResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public SafeCustomAttributesResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new SafeCustomAttributesRestResource(session);
    }

    @Override
    public void close() {
    }

}
