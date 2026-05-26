package sit.int224;

import sit.int224.models.Actor;
import sit.int224.repositories.ActorRepository;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class TestActor {
    public static void main(String[] args) {
        ActorRepository actorRepository = new ActorRepository();
        while (true) {
            System.out.println("--Main Menu----------");
            System.out.println("1) Create new actor");
            System.out.println("2) Find actor by id");
            System.out.println("3) Delete actor by id");
            System.out.println("4) List all actor");
            System.out.println("0) Exit");
            System.out.println("---------------------");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            Integer choice = scanner.nextInt();
            System.out.println("----------------");
            switch (choice) {
                case 0 -> System.exit(0);
                case 1 -> createNewActor(actorRepository);
                case 2 -> findActorById(actorRepository);
                case 3 -> deleteActorById(actorRepository);
                case 4 -> listAllActor(actorRepository);
                default -> System.err.println("Invalid choice");
            }
            System.out.println("\n\n");
        }
    }

    private static void listAllActor(ActorRepository actorRepository) {
        List<Actor> actorList = actorRepository.findAll();
        for (Actor actor : actorList) {
            System.out.println(actor);
        }
    }

    private static void createNewActor(ActorRepository actorRepository) {
        Actor newActor = new Actor();
        newActor.setFirstName("John");
        newActor.setLastName("Doe");
        newActor.setLastUpdate(Instant.now());
        newActor = actorRepository.save(newActor);
        System.out.println(newActor);
    }

    private static void deleteActorById(ActorRepository actorRepository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter actor id to delete: ");
        Integer id = scanner.nextInt();
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actor id " + id + " not found"));
        actorRepository.delete(actor);
        System.out.println("Actor id: " + id + " was deleted");
    }

    private static void findActorById(ActorRepository actorRepository) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter actor id : ");
        Integer id = scanner.nextInt();
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actor id " + id + " not found"));
        System.out.println(actor);
    }
}

