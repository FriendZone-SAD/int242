package sit.int242.doa;

import java.util.List;
import java.util.Optional;

public interface jdbcDao<T, I> {
    Optional<T>find(I id);
    List<T> getAll();
    void save(T entity);
    void update(T entity);
    void delete(I id);
}
