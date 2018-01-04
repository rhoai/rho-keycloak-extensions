package ai.rho.extensions.clientuser.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class ClientUserSpi implements Spi {

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "clientuser";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ClientUserService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ClientUserServiceProviderFactory.class;
    }
}
