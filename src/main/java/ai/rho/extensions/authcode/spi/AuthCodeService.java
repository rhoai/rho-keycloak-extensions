package ai.rho.extensions.authcode.spi;

import org.keycloak.provider.Provider;
import org.keycloak.models.UserModel;

public interface AuthCodeService extends Provider {

    UserModel validateMobileResetCode(String code);

    UserModel validateRegistrationCode(String code);
}
