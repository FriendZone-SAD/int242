package sit.int224;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import sit.int224.models.Actor;
import sit.int224.models.Film;
import sit.int224.repositories.ActorRepository;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ActorRepository actorRepository = new ActorRepository();
        Actor actor = actorRepository.findById(2).orElseThrow(
                () -> new RuntimeException("Actor Not Found")
        );
        System.out.println(actor);

//        EntityManagerFactory emf =
//                Persistence.createEntityManagerFactory("default");
//        EntityManager em = emf.createEntityManager();
//        Film film = em.find(Film.class, 10);
//        System.out.println(film);
//        Query query = em.createQuery("select f from Film f");
//        List<Film> films ;
//        int start = 0;
//        while (true) {
//            query.setMaxResults(20);
//            query.setFirstResult(start);
//            films = query.getResultList();
//            if (films.isEmpty()) {
//                break;
//            }
//            start = start + 20;
//            for (Film f : films) {
//                System.out.println(f);
//            }
//            System.out.println("------------------------------");
//            em.clear();
//        }
//        em.close();
//        emf.close();
    }
}