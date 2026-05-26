package sit.int224.repositories;

import sit.int224.models.Film;

import java.util.List;
import java.util.Optional;

public class FilmRepository implements DataRepositoryReuse<Film, Integer>{
    @Override
    public Class<Film> getEntityClass() {
        return Film.class;
    }
}
