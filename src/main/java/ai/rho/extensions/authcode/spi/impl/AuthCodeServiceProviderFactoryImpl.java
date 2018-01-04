package ai.rho.extensions.authcode.spi.impl;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import ai.rho.extensions.authcode.spi.AuthCodeService;
import ai.rho.extensions.authcode.spi.AuthCodeServiceProviderFactory;

public class AuthCodeServiceProviderFactoryImpl implements AuthCodeServiceProviderFactory {

    @Override
    public AuthCodeService create(KeycloakSession session) {
        return new AuthCodeServiceImpl(session);
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
        return "authCodeImpl";
    }
}
