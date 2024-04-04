package api;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class SearchTest {
    @Test
    public void searchTest(){
        DataManager.loadContentsFromFile();
        ArrayList<Content> s = Search.search(DataManager.contentList.get(2).getTitle(),"",null,"","",-1);
        assertTrue(s.contains(DataManager.contentList.get(2)));
        assertEquals(1,s.size());

        ArrayList<Content> s2 = Search.search("","movie",null,"","",-1);
        int sumOfMovies=0;
        for(Content c : DataManager.contentList){
            if(c instanceof Movie){
                sumOfMovies++;
            }
        }
        assertEquals(sumOfMovies, s2.size());
        ArrayList<Content> s3 = Search.search("","",null,"","",-1);
        assertEquals(s3.size(), DataManager.contentList.size());

        ArrayList<Content> s4 = Search.search("","series",null,"","",-1);
        int sumOfSeries=0;
        for(Content c : DataManager.contentList){
            if(c instanceof Series){
                sumOfSeries++;
            }
        }
        assertEquals(sumOfSeries, s4.size());

        ArrayList<Content> s5 = Search.search("","",Category.SCIFI,"","",-1);
        int size = 0;
        for(Content c : DataManager.contentList){
            if(c.getCategory().equals(Category.SCIFI)){
                size++;
            }
        }
        assertEquals(size,s5.size());

        ArrayList<Content> s6 = Search.search("","",null,"Bradley Cooper","",-1);
        assertEquals(2,s6.size());

        ArrayList<Content>s7 = Search.search("","",null,"","",2);
        int sum=0;
        for(Content c: DataManager.contentList){
            if(c.getAverageRating()>=2){
                sum++;
            }
        }
        assertEquals(sum,s7.size());
    }


}
