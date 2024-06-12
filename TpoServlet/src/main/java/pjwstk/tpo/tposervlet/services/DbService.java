package pjwstk.tpo.tposervlet.services;

import pjwstk.tpo.tposervlet.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbService implements IDbService {

    private static final String ConnectionURL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL driver", e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(ConnectionURL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e + "Problem with getting connection");
        }
    }

    @Override
    public List<String> getTitlesFromFilm() {
        String query = "SELECT title FROM Film";
        List<String> titles = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                titles.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titles;
    }

    @Override
    public List<Category> getAllCategoriesNames() {
        String query = "SELECT * FROM Category";
        List<Category> categories = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt("id"), resultSet.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Film> getAllFilmsByCategories(int id) {
        String query = "SELECT * FROM Film WHERE category_id = ?";
        List<Film> films = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                films.add(new Film(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("decription"),
                        id,
                        resultSet.getString("director")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public String getCategoryNameById(int id) {
        String query = "SELECT name FROM category WHERE id = ?";
        String resultName = "";
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                resultName = resultSet.getString("name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultName;
    }

    @Override
    public int getSumOfWatcherByFilmId(int id) {
        String query = "SELECT COUNT(*) FROM film_watcher WHERE film_id = ?";
        int result = 0;
        try(Connection connection = getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Film getAllAboutFilm(int id) {
        String query = "SELECT * FROM film WHERE id = ?";
        Film film = null;
        try(Connection connection = getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                film = new Film(id,
                        resultSet.getString("title"),
                        resultSet.getString("decription"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("director"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return film;
    }

    @Override
    public int getCategoryIdByName(String catName) {
        String query = "SELECT id FROM category WHERE name = ?";
        int result = -1;

        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, catName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                result = resultSet.getInt("id");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<SearchResult> getSearchedFilms(List<String> filters) {
        String searchTitle = filters.get(0);
        String searchCategories = filters.get(1);
        String searchDirector = filters.get(2);
        String searchMinWatchers = filters.get(3);

        int categoryId = searchCategories.equals("brak") ? -1 : getCategoryIdByName(searchCategories);
        boolean filterByDirector = !searchDirector.equals("brak");
        boolean filterByWatchers = !searchMinWatchers.isEmpty();

        String query = "SELECT * FROM film WHERE title LIKE ?";
        if (categoryId != -1) {
            query += " AND category_id = ?";
        }
        if (filterByDirector) {
            query += " AND director LIKE ?";
        }
        if (filterByWatchers) {
            query += " AND id IN (SELECT film_id FROM film_watcher GROUP BY film_id HAVING COUNT(*) >= ?)";
        }

        List<SearchResult> searchResults = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchTitle + "%");
            int index = 2;
            if (categoryId != -1) {
                preparedStatement.setInt(index++, categoryId);
            }
            if (filterByDirector) {
                preparedStatement.setString(index++, "%" + searchDirector + "%");
            }
            if (filterByWatchers) {
                preparedStatement.setInt(index, Integer.parseInt(searchMinWatchers));
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Film film = new Film(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("decription"),
                        resultSet.getInt("category_id"),
                        resultSet.getString("director")
                );
                Category category = new Category(resultSet.getInt("category_id"), getCategoryNameById(resultSet.getInt("category_id")));
                List<Watcher> watchers = getWatchersByFilmId(resultSet.getInt("id"));
                List<Actor> actors = getActorsByFilmId(resultSet.getInt("id"));

                searchResults.add(new SearchResult(actors, category, film, watchers));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    @Override
    public List<Watcher> getWatchersByFilmId(int id) {
        String query = "SELECT w.id, w.nickname FROM watcher w INNER JOIN film_watcher fw ON w.id = fw.watcher_id " +
                "WHERE fw.film_id = ?";
        List<Watcher> watchers = new ArrayList<>();
        try(Connection connection = getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                watchers.add(new Watcher(
                        resultSet.getInt("id"),
                        resultSet.getString("nickname")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return watchers;
    }

    @Override
    public List<Actor> getActorsByFilmId(int id) {
        String query = "SELECT a.id, a.first_name, a.last_name FROM actor a INNER JOIN film_actor fa ON a.id = fa.actor_id " +
                "WHERE fa.film_id = ?";
        List<Actor> actors = new ArrayList<>();
        try(Connection connection = getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                actors.add(new Actor(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return actors;
    }
}