package sit.int242.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private int id;
    private String title;
    private String releaseYear;
    private String rating;
    private int languageId;
}

