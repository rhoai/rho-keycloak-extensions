package ai.rho.extensions.groupuser;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;


public class GroupUserRestResource {

    private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

    public GroupUserRestResource(KeycloakSession session) {
        this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session,
                session.getContext().getRealm());
    }

    @Path("groups")
    public GroupUserResource getGroupUserResource() {
        checkRealmAdmin();
        return new GroupUserResource(session);
    }

    private void checkRealmAdmin() {
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (!auth.getUser().getUsername().equals("service-account-admin-cli")) {
            String user = auth.getUser().getUsername();
            throw new ForbiddenException(user);
        }
    }
}
