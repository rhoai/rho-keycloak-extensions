package ai.rho.extensions.clientuser.jpa;

import org.keycloak.Config.Scope;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class ClientUserEntityProviderFactory implements JpaEntityProviderFactory {

    protected static final String ID = "client-user-entity-provider";

    @Override
    public JpaEntityProvider create(KeycloakSession session) {
        return new ClientUserEntityProvider();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void init(Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }
}
