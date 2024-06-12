package pjwstk.tpo.tposervlet.models;

import java.util.List;

public class SearchResult {
    private Film film;
    private Category category;
    private List<Watcher> watcherList;
    private List<Actor> actorList;

    public SearchResult(List<Actor> actorList, Category category, Film film, List<Watcher> watcherList) {
        this.actorList = actorList;
        this.category = category;
        this.film = film;
        this.watcherList = watcherList;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Watcher> getWatcherList() {
        return watcherList;
    }

    public void setWatcherList(List<Watcher> watcherList) {
        this.watcherList = watcherList;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "actorList=" + actorList +
                ", film=" + film +
                ", category=" + category +
                ", watcherList=" + watcherList +
                '}';
    }
}
