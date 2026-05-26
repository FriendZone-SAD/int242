package sit.int224.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", columnDefinition = "smallint UNSIGNED not null")
    private Integer id;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "release_year")
    private Integer releaseYear;


    @Column(name = "language_id", nullable = false)
    private String languageId;

    @ColumnDefault("'G'")
    @Lob
    @Column(name = "rating")
    private String rating;

    public String toString() {
        return String.format(
                "%d %-25s %-10s %-10s %s",
                id, title, releaseYear, rating, languageId);
    }
}