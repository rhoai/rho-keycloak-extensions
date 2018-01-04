package ai.rho.extensions.passwordreset;

import org.keycloak.models.*;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;

public class ResetPasswordCodeRestResource {

    private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

    public ResetPasswordCodeRestResource(KeycloakSession session) {
        this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());
    }

    @Path("clients")
    public ResetPasswordCodeResource getResetPasswordCodeResouce() {
        checkAdmin();
        return new ResetPasswordCodeResource(session);
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
