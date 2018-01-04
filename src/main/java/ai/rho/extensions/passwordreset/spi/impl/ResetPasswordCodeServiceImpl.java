package ai.rho.extensions.passwordreset.spi.impl;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.jpa.UserAdapter;

import javax.persistence.*;
import java.lang.Long;

import ai.rho.extensions.passwordreset.spi.ResetPasswordCodeService;


public class ResetPasswordCodeServiceImpl implements ResetPasswordCodeService {

    private final KeycloakSession session;

    public ResetPasswordCodeServiceImpl(KeycloakSession session) {
        this.session = session;
    }

    public UserModel validateMobileResetCode(String code) {

        RealmModel realm = session.getContext().getRealm();
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();

        UserEntity user = getResetCodeUser(code, em);
        if (user != null) {
            deleteResetCode(user, em);
            return new UserAdapter(session, realm, em, user);
        } else {
            return null;
        }
    }

    private UserEntity getResetCodeUser(String code, EntityManager em) {
        String q = "select distinct u from UserEntity u " +
                "join UserAttributeEntity uae1 on uae1.user = u.id " +
                "join UserAttributeEntity uae2 on uae2.user = u.id " +
                "where uae1.name = 'reset_code' " +
                "and uae1.value = :code " +
                "and uae2.name = 'reset_code_expires' " +
                "and uae2.value >= :expires ";

        TypedQuery<UserEntity> query = em.createQuery(q, UserEntity.class);
        query.setParameter("code", code);

        long timestamp = System.currentTimeMillis() / 1000L;
        query.setParameter("expires", Long.toString(timestamp));

        try {
            return query.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    private void deleteResetCode(UserEntity user, EntityManager em) {
        String q = "delete from UserAttributeEntity uae " +
                   "where uae.user = :user " +
                   "and name = 'reset_code' or name = 'reset_code_expires'";

        Query query = em.createQuery(q);
        query.setParameter("user", user);
        query.executeUpdate();
    }

    @Override
    public void close() {
    }
}
