package sit.int224.repositories;

import jakarta.persistence.EntityManager;
import sit.int224.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.NonNull;
import org.apache.commons.collections.Factory;

import java.util.List;
import java.util.Optional;

public interface DataRepositoryReuse<E, T> {

    Class<E> getEntityClass();
    default EntityManager getEntityManager() {
        return EntityManagerFactory.getEntityManager();
    }
    default E save(E entity) {
        EntityManager em= getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        E managed = em.merge(entity);
        em.close();
        return managed;
    }
    default List<E> findAll() {
        EntityManager em= getEntityManager();
        try {
            return em.createQuery(
                    "select e from " + getEntityClass().getSimpleName() + " e",
                    getEntityClass()
            ).getResultList();
        } finally {
            em.close();
        }
    }
    default Optional<E> findById(T id) {
        EntityManager em= getEntityManager();
        try {
            return Optional.ofNullable(em.find(getEntityClass(), id));
        } finally {
            em.close();
        }
    }
    default void delete(@NonNull E entity) {
        EntityManager em= getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
    }
    default void deleteById(T id) {
        EntityManager em= getEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(getEntityClass(), id));
        em.getTransaction().commit();
        em.close();
    }
}
