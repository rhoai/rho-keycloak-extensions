package ai.rho.extensions.groupuser.spi;

import org.keycloak.provider.Provider;
import org.keycloak.models.UserModel;

import java.util.List;

public interface GroupUserService extends Provider {

    List<UserModel> listGroupUsers(String group_name, Integer maxResults, Integer firstResult, String email);

    Integer countGroupUsers(String group_name);

    void addUserGroup(String groupName, String userID);
}
