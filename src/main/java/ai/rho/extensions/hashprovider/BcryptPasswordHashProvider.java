package ai.rho.extensions.hashprovider;

import org.mindrot.jbcrypt.BCrypt;

import org.keycloak.credential.CredentialModel;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.credential.hash.PasswordHashProvider;

public class BcryptPasswordHashProvider implements PasswordHashProvider {

    private final String providerId;

    private final String BCryptAlgorithm;
    private int defaultIterations;

    public BcryptPasswordHashProvider(String providerId, String BCryptAlgorithm, int defaultIterations) {
        this.providerId = providerId;
        this.BCryptAlgorithm = BCryptAlgorithm;
        this.defaultIterations = defaultIterations;
    }

    @Override
    public boolean policyCheck(PasswordPolicy policy, CredentialModel credential) {
        int policyHashIterations = policy.getHashIterations();
        if (policyHashIterations == -1) {
            policyHashIterations = defaultIterations;
        }

        return credential.getHashIterations() == policyHashIterations && providerId.equals(credential.getAlgorithm());
    }

    @Override
    public void encode(String rawPassword, int iterations, CredentialModel credential) {
        if (iterations == -1) {
            iterations = defaultIterations;
        }

        String encodedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(iterations));

        credential.setAlgorithm(providerId);
        credential.setType(UserCredentialModel.PASSWORD);
        credential.setValue(encodedPassword);
    }

    @Override
    public boolean verify(String rawPassword, CredentialModel credential) {
        String hashed = credential.getValue();

        if (hashed.charAt(2) == 'b') {
            char[] chars = hashed.toCharArray();
            chars[2] = 'a';
            hashed = new String(chars);
        }
        return BCrypt.checkpw(rawPassword, hashed);
    }

    public void close() {

    }
}