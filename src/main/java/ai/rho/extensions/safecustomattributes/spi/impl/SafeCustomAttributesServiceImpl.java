package ai.rho.extensions.safecustomattributes.spi.impl;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.UserCache;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.jpa.UserAdapter;

import javax.persistence.*;

import java.util.Map;
import java.util.List;

import ai.rho.extensions.safecustomattributes.spi.SafeCustomAttributesService;


public class SafeCustomAttributesServiceImpl implements SafeCustomAttributesService {

    private final KeycloakSession session;

    public SafeCustomAttributesServiceImpl(KeycloakSession session) {
        this.session = session;
    }

    public void addAttributes(String user_id, Map<String, List<String>> attributes) {

        RealmModel realm = session.getContext().getRealm();
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();

        // get user
        UserEntity userEntity = em.createNamedQuery("getRealmUserById", UserEntity.class)
                .setParameter("id", user_id)
                .setParameter("realmId", realm.getId())
                .getSingleResult();
        UserModel user = new UserAdapter(session, realm, em, userEntity);

        // evict user data fro system cache
        UserCache userCache = session.userCache();
        System.out.println(userCache);
        if (userCache != null) {
            userCache.evict(realm, user);
        }

        // add or update user custom attributes
        for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
            user.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public UserModel searchAttributes(Map<String, String> attributes) {

        RealmModel realm = session.getContext().getRealm();
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();

        //CriteriaBuilder cb = em.getCriteriaBuilder();
        //CriteriaQuery<UserEntity> queryBuilder = cb.createQuery(UserEntity.class);

        //Metamodel m = em.getMetamodel();
        //EntityType<UserEntity> UserEntity_ = m.entity(UserEntity.class);
        //Root<UserEntity> root = queryBuilder.from(UserEntity_);

        //for (Map.Entry<String, String> entry : attributes.entrySet()) {
        //    Join<UserEntity, UserAttributeEntity> uae = root.join(UserEntity_.attributes);
        //}

        StringBuilder builder = new StringBuilder("select u from UserEntity u ");

        int index = 0;
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String join = String.format("join UserAttributeEntity uae%d on uae%d.user = u.id ", index, index);
            builder.append(join);

            if (index > 0) {
                builder.append(String.format("and uae%d.name = '%s' ", index, entry.getKey()));
            } else {
                builder.append(String.format("where uae%d.name = '%s' ", index, entry.getKey()));
            }
            builder.append(String.format("and uae%d.value = :%s ", index, entry.getKey()));

            index++;
        }

        String q = builder.toString();

        TypedQuery<UserEntity> query = em.createQuery(q, UserEntity.class);
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        UserEntity entity = query.getSingleResult();
        return new UserAdapter(session, realm, em, entity);
    }

    @Override
    public void close() {
    }
}
