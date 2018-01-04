package ai.rho.extensions.groupuser;

import org.keycloak.models.*;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import ai.rho.extensions.groupuser.spi.GroupUserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

public class GroupUserResource {

    private final KeycloakSession session;

    public GroupUserResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Path("{name}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer getGroupUserCount(@PathParam("name") String group_name) {
        return session.getProvider(GroupUserService.class).countGroupUsers(group_name);
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserRepresentation> getGroupUsers(@PathParam("name") String group_name,
                                                  @QueryParam("first") Integer firstResult,
                                                  @QueryParam("max") Integer maxResults,
                                                  @QueryParam("email") String email) {

        RealmModel realm = session.getContext().getRealm();

        firstResult = firstResult != null ? firstResult : -1;
        maxResults = maxResults != null ? maxResults : Constants.DEFAULT_MAX_RESULTS;

        List<UserModel> users = session.getProvider(GroupUserService.class)
                .listGroupUsers(group_name, maxResults, firstResult, email);
        List<UserRepresentation> results = new ArrayList<UserRepresentation>();

        for (UserModel user : users) {
            UserRepresentation rep = ModelToRepresentation.toRepresentation(session, realm, user);
            results.add(rep);
        }
        return results;
    }

    @POST
    @Path("{name}/user/{user_id}")
    public void addGroupUser(@PathParam("name") String group_name,
                             @PathParam("user_id") String user_id) {
        try {
            session.getProvider(GroupUserService.class).addUserGroup(group_name, user_id);
        } catch (ModelDuplicateException e) {
        }
    }

}
