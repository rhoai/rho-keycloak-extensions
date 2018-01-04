package ai.rho.extensions.passwordreset.spi.impl;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import ai.rho.extensions.passwordreset.spi.ResetPasswordCodeService;
import ai.rho.extensions.passwordreset.spi.ResetPasswordCodeServiceProviderFactory;

public class ResetPasswordCodeServiceProviderFactoryImpl implements ResetPasswordCodeServiceProviderFactory {

    @Override
    public ResetPasswordCodeService create(KeycloakSession session) {
        return new ResetPasswordCodeServiceImpl(session);
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
        return "resetPasswordCodeImpl";
    }
}
