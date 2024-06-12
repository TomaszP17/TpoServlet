package pjwstk.tpo.tposervlet.models;

public class Film {
    private final int id;
    private final String title;
    private final String description;
    private final int categoryId;
    private final String director;

    public Film(int id, String title, String description, int categoryId, String director) {
        this.categoryId = categoryId;
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Film{" +
                "categoryId=" + categoryId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", director='" + director + '\'' +
                '}';
    }
}
