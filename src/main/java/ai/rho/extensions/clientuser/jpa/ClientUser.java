package ai.rho.extensions.clientuser.jpa;

import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name="usersByRole", query="select u from UserEntity u join UserRoleMappingEntity ur on ur.user_id = u.id join RoleEntity r on r.id = ur.role_id where r.name in :roleNames")
})
public class ClientUser extends UserEntity {
}
