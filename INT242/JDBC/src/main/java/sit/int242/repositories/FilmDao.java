package sit.int242.repositories;

import sit.int242.doa.ConnectionFactory;
import sit.int242.doa.jdbcDao;
import sit.int242.models.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class FilmDao implements jdbcDao<Film, Integer> {
    private final static String SELECT_ALL = "SELECT * FROM film";
    private final static String INSERT_FILM = """
            INSERT INTO film (title, release_year, rating, language_id) VALUE (?,?,?,?)""";
    private final static String DELETE_FILM = """
            delete from film where film_id = ?""";
    private final static String UPDATE_FILM = """
            update film set title = ?, release_year = ?, rating = ?, language_id = ? where film_id = ?""";

    private Film mapRowToEntity(ResultSet rs) throws SQLException {
        return new Film(rs.getInt("film_id"), rs.getString("title"), rs.getString("release_year"), rs.getString("rating"), rs.getInt("language_id"));
    }

    @Override
    public void update(Film entity) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement preState = con.prepareStatement(UPDATE_FILM)) {

            preState.setString(1, entity.getTitle());
            preState.setString(2, entity.getReleaseYear());
            preState.setString(3, entity.getRating());
            preState.setInt(4, entity.getLanguageId());
            preState.setInt(5, entity.getId());

            preState.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (Connection con = ConnectionFactory.getConnection();
        PreparedStatement preState = con.prepareStatement(DELETE_FILM)){
            preState.setInt(1, id);
            preState.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Film entity) {
        try (Connection con = ConnectionFactory.getConnection();
        PreparedStatement preState = con.prepareStatement(INSERT_FILM)) {
            preState.setString(1, entity.getTitle());
            preState.setString(2, entity.getReleaseYear());
            preState.setString(3, entity.getRating());
            preState.setInt(4, entity.getLanguageId());

            preState.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Film> find(Integer id) {
        String statement = "select * from film where film_id = ?";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement preState = con.prepareStatement(statement);
            preState.setInt(1, id);

            try (ResultSet rs = preState.executeQuery()) {
                if (rs.next()) {
                    Film film = new Film();
                    film.setId(rs.getInt("film_id"));
                    film.setTitle(rs.getString("title"));
                    return Optional.of(film);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Film> getAll() {
        List<Film> film = new LinkedList<>();
        try {
            Connection con = ConnectionFactory.getConnection();
            ResultSet rs = con.createStatement().executeQuery(SELECT_ALL);
            while (rs.next()) {
                film.add(mapRowToEntity(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return film;
    }
}
