package sit.int224;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static sit.int224.repositories.DataRepository.emf;

public class EntityManagerFactory{
    private static String PERSISTENCE_UNIT_NAME = "default";
    private static final jakarta.persistence.EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    static {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/application.env")) {
            prop.load(input);
            PERSISTENCE_UNIT_NAME = prop.getProperty("PERSISTENCE_UNIT_NAME");
        } catch (IOException ex) {
                System.out.println("Cannot load application.env");
}
        System.out.println("Use Persistence Unit Name: " + PERSISTENCE_UNIT_NAME);
    }
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

