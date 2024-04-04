package api;

import java.util.Stack;

/**
 * Αυτή η κλάση δείχνει τα χαρακτηριστικά της ταινίας και της σειράς
 */
public class Show {
    public static String showMovie(Movie movie){
        String movieString;
        boolean suitability = movie.isOverEighteen();
        String suitable;
        StringBuilder alike = new StringBuilder();
        StringBuilder ratings = new StringBuilder();
        if(suitability){
            suitable = "18+";
        }
        else {
            suitable = "suitable for kids";
        }
        if(movie.getAlike().isEmpty()){
            alike.append("No similar movies");
        }
        else {
            for(Content m : movie.getAlike()){
                alike.append(m.getTitle()).append("\n");
            }
        }
        for(Rating r : movie.getRatings()){
            ratings.append("#").append("By: ").append(r.getAuthor().getName()).append(" ").append(r.getAuthor().getSurname()).append("\n").append(r.getRatingDocument()).append("\n")
                    .append("overall rating: ").append(r.getRating()).append("/5").append("\n");
        }

        movieString = movie.getTitle() + "\n"
                + movie.getYear() + "\n"+ "\n"
                + suitable + "\n"
                + "Description: "
                + movie.getDescription() + "\n"+ "\n"
                + "Starring: " + movie.getProtagonists() + "\n"
                + "Running time: " + movie.getDurationInMinutes() + " minutes" + "\n"
                + "category: " + movie.getCategory().toString().toLowerCase() + "\n"+ "\n"
                + "similar: " + alike + "\n"
                + "number of ratings: " + movie.getRatings().size() + "\n"+ "\n"
                + "average rating: " + movie.getAverageRating()+ "/5" + "\n"+ "\n"
                + ratings;

        return movieString;
    }

    public static String showSeries(Series series){
        String seriesString;
        boolean suitability = series.isOverEighteen();
        String suitable;
        StringBuilder alike = new StringBuilder();
        StringBuilder ratings = new StringBuilder();
        if(suitability){
            suitable = "18+";
        }
        else {
            suitable = "suitable for kids";
        }
        if(series.getAlike().isEmpty()){
            alike.append("No similar series");
        }
        else {
            for(Content s : series.getAlike()){
                alike.append(s.getTitle()).append("\n");
            }
        }
        for(Rating r : series.getRatings()){
            ratings.append("By: ").append(r.getAuthor().getName()).append(" ").append(r.getAuthor().getSurname()).append("\n").append(r.getRatingDocument()).append("\n")
                    .append("overall rating: ").append(r.getRating()).append("/5").append("\n");
        }
        StringBuilder showSeasons = new StringBuilder();

        for(Season s : series.getSeasons()){
            int i=1;
            StringBuilder episodes = new StringBuilder();
            Stack<Episode> episodesReversed= new Stack<>();
            for(Episode episode: s.getEpisodes()){
                episodesReversed.push(episode);
               // episodes.append(episode).append(s.getEpisodes().get(episode)).;
            }
            for(Episode episode: episodesReversed){
                episodes.append("~").append(i).append(" ").append(episode).append("\n");
                i++;
            }
            showSeasons.append("# ").append("Season").append(s.getSeasonNumber()).append(" ").append(s.getYear()).append("\n").append(episodes);
        }
        seriesString = series.getTitle() + "\n"+""+"\n"
                + series.getDescription() + "\n"+""+"\n"
                + "Starring: " + series.getProtagonists() + "\n"+""+"\n"
                + suitable + "\n"+""+"\n"
                + "category: " + series.getCategory().toString().toLowerCase() + "\n"+""+"\n"
                + "seasons:" + series.getSeasons().size() + "\n"
                + showSeasons+""+"\n"
                + "similar: " + alike + "\n"+""+"\n"
                + "number of ratings: " + series.getRatings().size() + "\n"+""+"\n"
                + "average rating: " + series.getAverageRating()+ "/5" + "\n"+""+"\n"
                + ratings;
        return seriesString;
    }
}
