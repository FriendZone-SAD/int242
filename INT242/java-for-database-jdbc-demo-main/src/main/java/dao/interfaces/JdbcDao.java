package dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface JdbcDao<T, I> {
    // T (Type): แทน Class ของ Entity นั้นๆ เช่น Product หรือ User
    // I (Identifier): แทนประเภทของ Primary Key เช่น Integer, Long หรือ String

    Optional<T> findById(I id) throws Exception;
    List<T> findMany() throws Exception;
    boolean save(T entity) throws Exception;
    boolean update(I id, T entity) throws Exception;
    //void delete(I id);
}
