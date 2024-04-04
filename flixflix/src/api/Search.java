package api;
import java.util.ArrayList;
public class Search extends Content {
    /**
     * Η μέθοδος υλοποιεί την αναζήτηση.
     *
     * @return μία λίστα με τα αποτελέσματα της αναζήτησης
     */
    public static ArrayList<Content> search(String title,String type,Category category,String protagonist,String isOver18, int minRating){
        ArrayList<Content> searchResults = new ArrayList<>();
        for(Content content: DataManager.contentList){
            if(     (title.equals("")             || content.getTitle().equalsIgnoreCase(title))
                    && (type.equals("")           || ((content instanceof Movie && type.equals("movie")) || (content instanceof Series && type.equalsIgnoreCase("series"))))
                    && (category == null          || content.getCategory()==category)
                    && (minRating == -1           || content.getAverageRating()>=minRating)
                    && (protagonist.equals("")    || content.getProtagonists().contains(protagonist))
                    && (isOver18.equals("")       || (isOver18.equalsIgnoreCase("no") && content.isOverEighteen())
                    || (isOver18.equalsIgnoreCase("yes") && !content.isOverEighteen()))){
                searchResults.add(content);
            }
        }
        return searchResults;
    }
}
