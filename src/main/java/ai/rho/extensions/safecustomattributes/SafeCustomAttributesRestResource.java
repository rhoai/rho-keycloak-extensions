package ai.rho.extensions.safecustomattributes;

import org.keycloak.models.*;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;


public class SafeCustomAttributesRestResource {

    private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

    public SafeCustomAttributesRestResource(KeycloakSession session) {
        this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());
    }

    @Path("users")
    public SafeCustomAttributesResource getSafeCustomAttributesResource() {
        checkAdmin();
        return new SafeCustomAttributesResource(session);
    }

    private void checkAdmin() {
        if (auth == null) {
            throw new NotAuthorizedException(("Bearer"));
        } else if (!auth.getUser().getUsername().equals("service-account-admin-cli")) {
            String user = auth.getUser().getUsername();
            throw new ForbiddenException(user);
        }
    }
}
