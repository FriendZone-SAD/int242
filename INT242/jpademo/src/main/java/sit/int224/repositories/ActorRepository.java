package sit.int224.repositories;

import jakarta.persistence.EntityManager;
import sit.int224.models.Actor;

import java.util.List;
import java.util.Optional;

public class ActorRepository implements DataRepository<Actor, Integer> {

    @Override
    public Actor save(Actor entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        Actor managed = em.merge(entity);
        em.close();
        return managed;
    }

    @Override
    public List<Actor> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("select a from Actor a", Actor.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Actor> findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return Optional.ofNullable(em.find(Actor.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Actor entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Integer id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Actor.class, id));
        em.getTransaction().commit();
        em.close();
    }
}
