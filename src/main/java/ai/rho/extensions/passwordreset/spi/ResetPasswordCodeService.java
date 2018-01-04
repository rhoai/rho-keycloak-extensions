package ai.rho.extensions.passwordreset.spi;

import org.keycloak.provider.Provider;
import org.keycloak.models.UserModel;

public interface ResetPasswordCodeService extends Provider {

    UserModel validateMobileResetCode(String code);
}
