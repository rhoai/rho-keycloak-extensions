package ai.rho.extensions.clientuser.spi.impl;

import org.keycloak.Config.Scope;
import ai.rho.extensions.clientuser.spi.ClientUserService;
import ai.rho.extensions.clientuser.spi.ClientUserServiceProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class ClientUserServiceProviderFactoryImpl implements ClientUserServiceProviderFactory {

    @Override
    public ClientUserService create(KeycloakSession session) {
        return new ClientUserServiceImpl(session);
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

    @Override
    public String getId() {
        return "clientUserServiceImpl";
    }
}
