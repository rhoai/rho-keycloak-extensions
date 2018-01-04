package ai.rho.extensions.groupuser.spi.impl;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import ai.rho.extensions.groupuser.spi.GroupUserService;
import ai.rho.extensions.groupuser.spi.GroupUserServiceProviderFactory;


public class GroupUserServiceProviderFactoryImpl implements GroupUserServiceProviderFactory {

    @Override
    public GroupUserService create(KeycloakSession session) {
        return new GroupUserServiceImpl(session);
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
        return "groupUserServiceImpl";
    }
}
