package ai.rho.extensions.clientuser.jpa;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.RealmModel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;
import java.util.Collections;
//import java.util.ArrayList;

public class ClientUserEntityProvider implements JpaEntityProvider {

    //protected KeycloakSession session;

    //public ClientUserEntityProvider(KeycloakSession session) {
    //    this.session = session;
    //}

    public List<UserEntity> getClientUsers(RealmModel realm, EntityManager em, List<String> roles) {

        //RealmModel realm = session.getContext().getRealm();
        //EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();

        String q = "select u from UserEntity u " +
                   "join UserRoleMappingEntity ur on ur.user_id = u.id " +
                   "join RoleEntity r on r.id = ur.role_id where r.name in (:roleNames)";
        TypedQuery<UserEntity> query = em.createNamedQuery(q, UserEntity.class);
        query.setParameter("roleNames", roles);

        List<UserEntity> results = query.getResultList();
        //List<UserEntity> users = new ArrayList<UserEntity>();

        //for (UserEntity entity : results) {
        //    users.add(new UserAdapter(session, realm, em, entity));
        //}
        return results;
    }

    @Override
    public List<Class<?>> getEntities() {
        return Collections.<Class<?>>singletonList(UserEntity.class);
    }

    @Override
    public String getChangelogLocation() {
        return "META-INF/example-changelog.xml";
    }

    @Override
    public void close() {
    }

    @Override
    public String getFactoryId() {
        return ClientUserEntityProviderFactory.ID;
    }
}
