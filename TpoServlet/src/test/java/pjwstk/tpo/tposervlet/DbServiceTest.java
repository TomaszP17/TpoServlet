package pjwstk.tpo.tposervlet;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pjwstk.tpo.tposervlet.models.SearchResult;
import pjwstk.tpo.tposervlet.services.DbService;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DbServiceTest {

    DbService dbService;

    @BeforeEach
    void setupBeforeEach(){
        dbService = new DbService();
        System.out.println("@Before Each executes before the execution of each test method");
    }

    @Test
    @DisplayName("Test Connection Success")
    @Order(1)
    void testConnectionSuccess() {
        assertDoesNotThrow(() -> {
            Connection connection = dbService.getConnection();
            assertNotNull(connection, "Connection should not be null");
        });
    }

    @Test
    @DisplayName("Is films title empty")
    @Order(2)
    void testEmptyTitles(){
        assertNotNull(dbService.getTitlesFromFilm(), "In films should be titles");
    }

    @Test
    @DisplayName("Number of film watchers")
    @Order(3)
    void testIfFilmHasWatchers(){
        assertEquals(2, dbService.getSumOfWatcherByFilmId(1), "Number of watchers should be 2");
    }

    @Test
    @DisplayName("Not Null Categories")
    @Order(4)
    void testNotNullCategories(){
        assertNotNull(dbService.getAllCategoriesNames(), "Categories should not be null");
    }

    @Test
    @DisplayName("Equals Get Category Id By Name")
    @Order(5)
    void testEqualsCategoryIdByName(){
        assertEquals(1, dbService.getCategoryIdByName("Informatyka"), "CatName should be equals!");
    }

    @Test
    @DisplayName("Watchers By FilmId")
    @Order(6)
    void testWatchersByFilmId(){
        System.out.println(dbService.getWatchersByFilmId(1));
    }

    @Test
    @DisplayName("Actors By FilmId")
    @Order(7)
    void testActorsByFilmId(){
        System.out.println(dbService.getActorsByFilmId(1));
    }

    @Test
    @DisplayName("SearchResult By Filters")
    @Order(8)
    void testSearchResultByFilters(){
        List<String> filters = Arrays.asList("Avengers", "brak", "brak", "");
        List<SearchResult> results = dbService.getSearchedFilms(filters);
        for (SearchResult result : results) {
            System.out.println(result);
        }
    }
}
