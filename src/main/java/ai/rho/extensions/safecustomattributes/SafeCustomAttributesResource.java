package ai.rho.extensions.safecustomattributes;

import org.keycloak.models.*;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import ai.rho.extensions.safecustomattributes.spi.SafeCustomAttributesService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class SafeCustomAttributesResource {

    private final KeycloakSession session;

    public SafeCustomAttributesResource(KeycloakSession session) {
        this.session = session;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserCustomAttributes(@PathParam("id") String user_id,
                                             Map<String, List<String>> attributes) {

        session.getProvider(SafeCustomAttributesService.class).addAttributes(user_id, attributes);
        return Response.noContent().build();
    }

    @POST
    @Path("search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, UserRepresentation> searchUserCustomAttributes(Map<String, String> attributes) {
        RealmModel realm = session.getContext().getRealm();

        UserModel user = session.getProvider(SafeCustomAttributesService.class).searchAttributes(attributes);
        HashMap<String, UserRepresentation> result = new HashMap<>();

        if (user != null) {
            UserRepresentation rep = ModelToRepresentation.toRepresentation(session, realm, user);
            result.put("userInfo", rep);
        }
        return result;
    }
}
