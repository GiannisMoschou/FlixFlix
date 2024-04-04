package api;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class ShowTest {
    @Test
    public void showTest(){
        Movie movie = new Movie("a movie","..",true,Category.ACTION," ",2000,100);
        String s = Show.showMovie(movie);
        assertTrue(s.contains("a movie"));
        assertTrue(s.contains("0"));
        assertTrue(s.contains(Category.ACTION.toString().toLowerCase()));

        Series series = new Series();
        series.setCategory(Category.HORROR);
        series.setTitle("A series");
        series.setDescription("..");
        series.setOverEighteen(false);
        series.setProtagonists(" ");

        Season season = new Season(1,2005);
        series.addSeason(season);
        String a =Show.showSeries(series);
        assertTrue(a.contains("A series"));
        assertTrue(a.contains("1"));
        assertTrue(a.contains("2005"));
        assertTrue(a.contains(Category.HORROR.toString().toLowerCase()));

    }

}
