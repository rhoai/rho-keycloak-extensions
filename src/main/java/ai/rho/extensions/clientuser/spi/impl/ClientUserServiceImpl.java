package ai.rho.extensions.clientuser.spi.impl;

import org.keycloak.connections.jpa.JpaConnectionProvider;
//import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.jpa.entities.RoleEntity;
import org.keycloak.models.jpa.UserAdapter;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ai.rho.extensions.clientuser.spi.ClientUserService;
//import ai.rho.extensions.clientuser.jpa.ClientUserEntityProvider;

import java.util.List;
import java.util.ArrayList;

public class ClientUserServiceImpl implements ClientUserService {

    private final KeycloakSession session;
    private final String USER_QUERY_FILTERS = "join UserRoleMappingEntity ur on ur.user = u.id " +
                                              "join RoleEntity r on r.id = ur.roleId " +
                                              "where r.name in (:roleNames) " +
                                              "and u.serviceAccountClientLink is null";

    public ClientUserServiceImpl(KeycloakSession session) {
        this.session = session;
    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    @Override
    public Integer countClientUsers(String client_name) {

        EntityManager em = getEntityManager();

        List<String> roles = getClientRoles(em, client_name);

        StringBuilder builder = new StringBuilder("select count(u) from UserEntity u ");
        builder.append(USER_QUERY_FILTERS);

        String q = builder.toString();
        TypedQuery<Long> query = em.createQuery(q, Long.class);
        query.setParameter("roleNames", roles);

        return Math.toIntExact(query.getSingleResult());
    }

    @Override
    public List<UserModel> listClientUsers(String client_name, Integer maxResults, Integer firstResult) {

        RealmModel realm = getRealm();
        EntityManager em = getEntityManager();

        List<String> roles = getClientRoles(em, client_name);

        StringBuilder builder = new StringBuilder("select u from UserEntity u ");
        builder.append(USER_QUERY_FILTERS);

        String q = builder.toString();

        TypedQuery<UserEntity> query = em.createQuery(q, UserEntity.class);
        query.setParameter("roleNames", roles);
        if (maxResults > -1) {
            query.setMaxResults(maxResults);
        }
        if (firstResult > -1) {
            query.setFirstResult(firstResult);
        }

        List<UserEntity> results = query.getResultList();
        List<UserModel> users = new ArrayList<UserModel>();

        for (UserEntity entity : results) {
            UserModel user = new UserAdapter(session, realm, em, entity);
            users.add(user);
        }
        return users;
    }

    private List<String> getClientRoles(EntityManager em, String client_name) {

        String q = "select re from RoleEntity re " +
                   "join ClientEntity ce on ce.id = re.client " +
                   "where re.clientRole is true " +
                   "and ce.clientId = :name";
        TypedQuery<RoleEntity> query = em.createQuery(q, RoleEntity.class);
        query.setParameter("name", client_name);

        List<RoleEntity> results = query.getResultList();
        List<String> roles = new ArrayList<String>();

        for (RoleEntity entity: results) {
            roles.add(entity.getName());
        }
        return roles;
    }

    @Override
    public void close() {
    }
}
