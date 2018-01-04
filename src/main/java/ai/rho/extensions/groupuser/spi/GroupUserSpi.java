package ai.rho.extensions.groupuser.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class GroupUserSpi implements Spi {

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "groupuser";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return GroupUserService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return GroupUserServiceProviderFactory.class;
    }
}
