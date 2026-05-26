package sit.int224.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public interface DataRepository<E, T> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    E save(E entity);

    List<E> findAll();

    Optional<E> findById(T id);

    void delete(E entity);

    void deleteById(T id);

    default EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
