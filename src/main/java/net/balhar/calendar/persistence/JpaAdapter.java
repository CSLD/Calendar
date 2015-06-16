package net.balhar.calendar.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * One of the adapters which can be used by the Services to store the data. It can work with any type of the entity.
 * There is trick. T isn't necessarily annotated entity and I need to be able to build entity on top of the information.
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class JpaAdapter<T> implements Adapter<T> {
    private Class<T> entityClass;
    private EntityManager entityManager;

    @Autowired
    public JpaAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void store(T toStore) {
        entityManager.persist(toStore);
    }

    @Override
    public void store(Collection<T> toStore) {
        toStore.forEach(this::store);
    }

    @Override
    public Collection<T> retrieve() {
        return entityManager.createQuery("from " + entityClass.getName()).getResultList();
    }

    @Override
    public void remove(T toDelete) {
        entityManager.remove(toDelete);
    }

    @Override
    public void remove(Collection<T> toDelete) {
        toDelete.forEach(this::remove);
    }
}
