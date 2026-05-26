package sit.int242;

import sit.int242.models.Film;
import sit.int242.repositories.FilmDao;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        FilmDao filmDao = new FilmDao();
        List<Film> films = filmDao.getAll();
        for (Film f : films) {
            System.out.println(f);
        }
        System.out.println("--------------------------");
        filmDao.find(5).ifPresent(film -> System.out.println(film));
        System.out.println("--------------------------");
        Film newData = new Film();
        newData.setTitle("John Wick");
        newData.setReleaseYear("2026");
        newData.setRating("R");
        newData.setLanguageId(1);
        filmDao.save(newData);
        filmDao.find(1001).ifPresentOrElse(
                film -> System.out.println(film),
                () -> System.out.println("Not Found")
        );
        System.out.println("--------------------------");
        filmDao.find(1001).ifPresent(film -> {
            film.setTitle("John Wick: Chapter 2"); // เปลี่ยนชื่อเรื่องใหม่
            film.setRating("NC-17");               // เปลี่ยนเรทติ้งใหม่
            film.setLanguageId(1);
            filmDao.update(film);                  // สั่งบันทึกการอัปเดตลง DB
        });
        System.out.println("--------------------------");
        filmDao.delete(1001);
        filmDao.find(1001).ifPresentOrElse(
                film -> System.out.println(film),
                () -> System.out.println("Not Found")
        );
    }
}
