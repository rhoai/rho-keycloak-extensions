package ai.rho.extensions.safecustomattributes.spi.impl;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import ai.rho.extensions.safecustomattributes.spi.SafeCustomAttributesService;
import ai.rho.extensions.safecustomattributes.spi.SafeCustomAttributesServiceProviderFactory;

public class SafeCustomAttributesServiceProviderFactoryImpl implements SafeCustomAttributesServiceProviderFactory {

    @Override
    public SafeCustomAttributesService create(KeycloakSession session) {
        return new SafeCustomAttributesServiceImpl(session);
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
        return "safeCustomAttributesServiceImpl";
    }
}
