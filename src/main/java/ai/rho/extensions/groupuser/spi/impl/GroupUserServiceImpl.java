package ai.rho.extensions.groupuser.spi.impl;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.jpa.entities.GroupEntity;
import org.keycloak.models.jpa.entities.UserGroupMembershipEntity;
import org.keycloak.models.jpa.UserAdapter;

//import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ai.rho.extensions.groupuser.spi.GroupUserService;

import java.util.List;
import java.util.ArrayList;

public class GroupUserServiceImpl implements GroupUserService {

    private final KeycloakSession session;
    private final String USER_QUERY_FILTERS = "join UserGroupMembershipEntity ug on ug.user = u.id " +
                                              "join GroupEntity g on ug.groupId = g.id " +
                                              "where g.name = :groupName";

    public GroupUserServiceImpl(KeycloakSession session) {
        this.session = session;
    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    @Override
    public Integer countGroupUsers(String group_name) {

        EntityManager em = getEntityManager();

        StringBuilder builder = new StringBuilder("select count(u) from UserEntity u ");
        builder.append(USER_QUERY_FILTERS);

        String q = builder.toString();
        TypedQuery<Long> query = em.createQuery(q, Long.class);
        query.setParameter("groupName", group_name);

        return Math.toIntExact(query.getSingleResult());
    }

    @Override
    public List<UserModel> listGroupUsers(String group_name, Integer maxResults, Integer firstResult, String email) {

        RealmModel realm = getRealm();
        EntityManager em = getEntityManager();

        StringBuilder builder = new StringBuilder("select u from UserEntity u ");
        builder.append(USER_QUERY_FILTERS);

        if (email != null) {
            builder.append(" and u.email = :email");
        }

        String q = builder.toString();

        TypedQuery<UserEntity> query = em.createQuery(q, UserEntity.class);
        query.setParameter("groupName", group_name);
        if (maxResults > -1) {
            query.setMaxResults(maxResults);
        }
        if (firstResult > -1) {
            query.setFirstResult(firstResult);
        }
        if (email != null) {
            query.setParameter("email", email);
        }

        List<UserEntity> results = query.getResultList();
        List<UserModel> users = new ArrayList<UserModel>();

        for (UserEntity entity : results) {
            UserModel user = new UserAdapter(session, realm, em, entity);
            users.add(user);
        }
        return users;
    }

    @Override
    public void addUserGroup(String groupName, String userID) {

        EntityManager em = getEntityManager();

        // select group
        String group_q = "select g from GroupEntity g where g.name = :groupName";
        TypedQuery<GroupEntity> group_query = em.createQuery(group_q, GroupEntity.class);
        group_query.setParameter("groupName", groupName);
        GroupEntity group = group_query.getSingleResult();

        // select user
        String user_q = "select u from UserEntity u where u.id = :userID";
        TypedQuery<UserEntity> user_query = em.createQuery(user_q, UserEntity.class);
        user_query.setParameter("userID", userID);
        UserEntity user = user_query.getSingleResult();

        // check if user is already a member of the group
        List<UserGroupMembershipEntity> members = em.createNamedQuery("userMemberOf")
                .setParameter("user", user)
                .setParameter("groupId", group.getId())
                .getResultList();

        if (members.isEmpty()) {
            // create user group membership record
            UserGroupMembershipEntity userGroup = new UserGroupMembershipEntity();
            userGroup.setGroupId(group.getId());
            userGroup.setUser(user);

            em.persist(userGroup);
        }
    }

    @Override
    public void close() {
    }
}
