package pjwstk.tpo.tposervlet.services;

import pjwstk.tpo.tposervlet.models.*;

import java.util.List;

public interface IDbService {

    List<String> getTitlesFromFilm();
    List<Category> getAllCategoriesNames();
    List<Film> getAllFilmsByCategories(int id);
    String getCategoryNameById(int id);
    int getSumOfWatcherByFilmId(int id);
    Film getAllAboutFilm(int id);

    List<SearchResult> getSearchedFilms(List<String> filters);
    int getCategoryIdByName(String catName);
    List<Watcher> getWatchersByFilmId(int id);
    List<Actor> getActorsByFilmId(int id);


}
