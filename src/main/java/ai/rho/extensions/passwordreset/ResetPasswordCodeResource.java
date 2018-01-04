package ai.rho.extensions.passwordreset;

import org.keycloak.models.*;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.ErrorResponse;
import org.keycloak.services.managers.ClientSessionCode;

import ai.rho.extensions.passwordreset.spi.ResetPasswordCodeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;

public class ResetPasswordCodeResource {

    private final KeycloakSession session;

    public ResetPasswordCodeResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Path("{id}/validate")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, UserRepresentation> validateResetPasswordCode(@PathParam("id") String client_id,
                                                        @QueryParam("code") String code) {

        HashMap<String, UserRepresentation> result = new HashMap<>();
        RealmModel realm = session.getContext().getRealm();

        AuthenticatedClientSessionModel clientSession = ClientSessionCode.getClientSession(code,
                session, realm, AuthenticatedClientSessionModel.class);

        try {
            boolean isValid = ClientSessionCode.verifyCode(code, clientSession);
            if (isValid) {
                UserSessionModel userSession = clientSession.getUserSession();
                UserModel user = userSession.getUser();
                result.put("userInfo", ModelToRepresentation.toRepresentation(session, realm, user));
                return result;
            } else {
                return result;
            }
        } catch (RuntimeException e) {
            return result;
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, String> getResetPasswordCode(@PathParam("id") String client_id,
                                                        @QueryParam("user_id") String user_id) {

        RealmModel realm = session.getContext().getRealm();
        ClientModel client = realm.getClientByClientId(client_id);

        AuthenticatedClientSessionModel clientSession = createClientSession(user_id, realm, client);

        ClientSessionCode<AuthenticatedClientSessionModel> accessCode;
        accessCode = new ClientSessionCode<>(session, realm, clientSession);
        String code = accessCode.getCode();

        HashMap<String, String> result = new HashMap<>();
        result.put("code", code);
        return result;
    }

    @GET
    @Path("{id}/validate-mobile")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, UserRepresentation> validateMobilePasswordCode(@PathParam("id") String client_id,
                                                                          @QueryParam("code") String code) {
        RealmModel realm = session.getContext().getRealm();

        UserModel user = session.getProvider(ResetPasswordCodeService.class).validateMobileResetCode(code);
        HashMap<String, UserRepresentation> result = new HashMap<>();

        if (user != null) {
            UserRepresentation rep = ModelToRepresentation.toRepresentation(session, realm, user);
            result.put("userInfo", rep);
        }
        return result;
    }

    private AuthenticatedClientSessionModel createClientSession(String user_id, RealmModel realm, ClientModel client) {
        UserModel user = session.users().getUserById(user_id, realm);

        if (!user.isEnabled()) {
            throw new WebApplicationException(ErrorResponse.error("User is disabled", Response.Status.BAD_REQUEST));
        }

        if (client == null || !client.isEnabled()) {
            throw new WebApplicationException(ErrorResponse.error("Client is disabled", Response.Status.BAD_REQUEST));
        }

        UserSessionModel userSession = session.sessions().createUserSession(
                KeycloakModelUtils.generateId(),
                realm,
                user,
                user.getUsername(),
                "",
                "form",
                false,
                null,
                null
        );
        userSession.setState(UserSessionModel.State.LOGGED_IN);

        return session.sessions().createClientSession(realm, client, userSession);
    }
}
