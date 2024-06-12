package pjwstk.tpo.tposervlet.models;

public class Watcher {
    private int id;
    private String nickname;

    public Watcher(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Watcher{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
