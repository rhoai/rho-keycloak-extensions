package ai.rho.extensions.clientuser;

import org.keycloak.models.*;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import ai.rho.extensions.clientuser.spi.ClientUserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

public class ClientUserResource {

    private final KeycloakSession session;

    public ClientUserResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Path("{id}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer getClientUsersCount(@PathParam("id") String client_id) {
        return session.getProvider(ClientUserService.class).countClientUsers(client_id);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserRepresentation> getClientUsers(@PathParam("id") String client_id,
                                                   @QueryParam("first") Integer firstResult,
                                                   @QueryParam("max") Integer maxResults) {

        RealmModel realm = session.getContext().getRealm();

        firstResult = firstResult != null ? firstResult : -1;
        maxResults = maxResults != null ? maxResults : Constants.DEFAULT_MAX_RESULTS;

        List<UserModel> users = session.getProvider(ClientUserService.class).listClientUsers(client_id, maxResults, firstResult);
        List<UserRepresentation> results = new ArrayList<UserRepresentation>();

        for (UserModel user : users) {
            UserRepresentation rep = ModelToRepresentation.toRepresentation(session, realm, user);
            results.add(rep);
        }
        return results;
    }


}
