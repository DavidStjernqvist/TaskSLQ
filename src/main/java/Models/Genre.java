package Models;

public class Genre {

    private Integer GenreId;
    private String Name;

    public Genre(Integer genreId, String name) {
        GenreId = genreId;
        Name = name;
    }

    public Integer getGenreId() {
        return GenreId;
    }

    public String getName() {
        return Name;
    }
}
