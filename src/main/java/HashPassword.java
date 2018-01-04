//import org.mindrot.jbcrypt.BCrypt;
//import org.keycloak.credential.CredentialModel;
//import ai.rho.extensions.hashprovider.BcryptPasswordHashProvider;
import ai.rho.extensions.clientuser.spi.ClientUserService;

class HashPassword {
    public static void main(String[] args) {
        System.out.println(ClientUserService.class);
        //String password = "password";
        //int iterations = 12;
        //CredentialModel credentials = new CredentialModel();

        //String providerId = "bcrypt";
        //String algorithm = "BCrypt";


        //BcryptPasswordHashProvider provider = new BcryptPasswordHashProvider(providerId, algorithm, iterations);
        //String py_hashed = "$2b$12$jv8G1Zbx/RDhGHCJ9i/FnekOK1xA44gScbvmaQWdPGR8Z.2cPV7zm";
        //credentials.setValue(py_hashed);
        //provider.encode(password, iterations, credentials);

        //System.out.println(credentials.getAlgorithm());
        //System.out.println(credentials.getType());
        //System.out.println(credentials.getValue());

        //boolean result = provider.verify(password, credentials);
        //System.out.println("provider recognizes hash python produced");
        //System.out.println(result);

        //String hashed = BCrypt.hashpw(password, BCrypt.gensalt(iterations));
        //System.out.println(hashed);
        //String py_hashed = "$2a$12$jv8G1Zbx/RDhGHCJ9i/FnekOK1xA44gScbvmaQWdPGR8Z.2cPV7zm";

        //boolean result = BCrypt.checkpw(password, credentials.getValue());
        //System.out.println("provider produces same hash");
        //System.out.println(result);
    }
}