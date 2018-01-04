package ai.rho.extensions.passwordreset.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class ResetPasswordCodeSpi implements Spi {

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "passwordreset";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ResetPasswordCodeService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ResetPasswordCodeServiceProviderFactory.class;
    }
}
