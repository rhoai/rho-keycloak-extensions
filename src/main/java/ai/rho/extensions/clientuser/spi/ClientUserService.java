package ai.rho.extensions.clientuser.spi;

import org.keycloak.provider.Provider;
import org.keycloak.models.UserModel;

import java.util.List;

public interface ClientUserService extends Provider {

    List<UserModel> listClientUsers(String client_name, Integer maxResults, Integer firstResult);

    Integer countClientUsers(String client_name);
}
