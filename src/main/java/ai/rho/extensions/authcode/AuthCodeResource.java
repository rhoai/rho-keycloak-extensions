package ai.rho.extensions.authcode;

import org.keycloak.models.*;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import ai.rho.extensions.authcode.spi.AuthCodeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.HashMap;

public class AuthCodeResource {

    private final KeycloakSession session;

    public AuthCodeResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Path("validate")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, UserRepresentation> validate(@QueryParam("code") String code,
                                                        @QueryParam("code-type") String codeType) {

        RealmModel realm = session.getContext().getRealm();

        UserModel user;
        user = null;
        if (codeType.equals("reset_password")) {
            user = session.getProvider(AuthCodeService.class).validateMobileResetCode(code);
        } else if (codeType.equals("registration")) {
            user = session.getProvider(AuthCodeService.class).validateRegistrationCode(code);
        }
        HashMap<String, UserRepresentation> result = new HashMap<>();

        if (user != null) {
            UserRepresentation rep = ModelToRepresentation.toRepresentation(session, realm, user);
            result.put("userInfo", rep);
        }
        return result;
    }
}
