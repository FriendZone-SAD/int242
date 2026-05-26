package sit.int224;

import sit.int224.models.Film;
import sit.int224.repositories.FilmRepository;

import java.util.List;
import java.util.Scanner;

public class TestFilm {
    public static void main(String[] args) {
        FilmRepository filmRepository = new FilmRepository();
        while (true) {
            System.out.println("--Main Menu----------");
            System.out.println("1) Create new film");
            System.out.println("2) Find film by id");
            System.out.println("3) Delete film by id");
            System.out.println("4) List all film");
            System.out.println("0) Exit");
            System.out.println("---------------------");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            Integer choice = scanner.nextInt();
            System.out.println("----------------");
            switch (choice) {
                case 0 -> System.exit(0);
                case 1 -> createNewFilm(filmRepository);
                case 2 -> findFilmById(filmRepository);
                case 3 -> deleteFilmById(filmRepository);
                case 4 -> listAllFilm(filmRepository);
                default -> System.err.println("Invalid choice");
            }
            System.out.println("\n\n");
        }
    }

    private static void listAllFilm(FilmRepository filmRepository) {
        List<Film> filmList = filmRepository.findAll();
        for (Film film : filmList) {
            System.out.println(film);
        }
    }

    private static void createNewFilm(FilmRepository filmRepository) {
        Film newFilm = new Film();
        newFilm.setTitle("Inception");
        newFilm.setReleaseYear(2010); // แก้ไขจาก "2010" เป็นตัวเลข 2010 (Integer)
        newFilm.setRating("PG-13");
        newFilm.setLanguageId("1");
        newFilm = filmRepository.save(newFilm);
        System.out.println(newFilm);
    }

    private static void deleteFilmById(FilmRepository filmRepository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter film id to delete: ");
        Integer id = scanner.nextInt();
        Film film = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Film id " + id + " not found"));
        filmRepository.delete(film);
        System.out.println("Film id: " + id + " was deleted");
    }

    private static void findFilmById(FilmRepository filmRepository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter film id : ");
        Integer id = scanner.nextInt();
        Film film = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Film id " + id + " not found"));
        System.out.println(film);
    }
}