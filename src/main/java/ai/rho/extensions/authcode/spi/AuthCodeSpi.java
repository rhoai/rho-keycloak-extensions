package ai.rho.extensions.authcode.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class AuthCodeSpi implements Spi {

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "authcode";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return AuthCodeService.class;
    }

    @Override
    @SuppressWarnings("rawTypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return AuthCodeServiceProviderFactory.class;
    }
}
