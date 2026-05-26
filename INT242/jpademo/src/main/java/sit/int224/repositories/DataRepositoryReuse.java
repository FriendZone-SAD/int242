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
    // 2. findAll แบบเพิ่มฟีเจอร์ Paging (ดึงแบบแบ่งหน้าตามลอจิกที่พี่ส่งมา)
    default List<E> findAll(int startPosition, int maxRecords) {
        EntityManager em = getEntityManager();
        try {
            // สร้าง Dynamic Query จากชื่อเอนทิตีคลาส
            var query = em.createQuery(
                    "select e from " + getEntityClass().getSimpleName() + " e",
                    getEntityClass()
            );

            // นำลอจิกจากสไลด์เรื่อง Paging มาผูกเข้ากับเมธอดดึงข้อมูล
            query.setFirstResult(startPosition); // กำหนดจุดเริ่มต้นดึงข้อมูล (Offset) [cite: 415]
            query.setMaxResults(maxRecords);     // กำหนดจำนวนแถวสูงสุดในหน้านั้นๆ (Limit) [cite: 414]

            return query.getResultList();
        } finally {
            em.close(); // ปิดเซสชันเพื่อป้องกันปัญหาหน่วยความจำรั่วไหล (Memory Leak)
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
