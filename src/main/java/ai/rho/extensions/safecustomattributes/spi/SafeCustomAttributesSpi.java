package ai.rho.extensions.safecustomattributes.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class SafeCustomAttributesSpi implements Spi {

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "safecustomattributes";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return SafeCustomAttributesService.class;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return SafeCustomAttributesServiceProviderFactory.class;
    }
}
