package ai.rho.extensions.hashprovider;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.credential.hash.PasswordHashProviderFactory;

public class BcryptPasswordHashProviderFactory implements PasswordHashProviderFactory {

    public static final String ID = "bcrypt";
    public static final String BCRYPT_ALGORITHM = "BCrypt";
    public static final int DEFAULT_ITERATIONS = 13;

    @Override
    public BcryptPasswordHashProvider create(KeycloakSession session) {
        return new BcryptPasswordHashProvider(ID, BCRYPT_ALGORITHM, DEFAULT_ITERATIONS);
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void close() {

    }
}