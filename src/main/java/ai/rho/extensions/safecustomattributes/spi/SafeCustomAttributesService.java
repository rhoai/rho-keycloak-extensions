package ai.rho.extensions.safecustomattributes.spi;

import org.keycloak.provider.Provider;
import org.keycloak.models.UserModel;

import java.util.Map;
import java.util.List;

public interface SafeCustomAttributesService extends Provider {

    void addAttributes(String user_id, Map<String, List<String>> attributes);

    UserModel searchAttributes(Map<String, String> attributes);
}
