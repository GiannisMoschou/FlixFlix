package api;

import java.io.Serializable;
import java.util.Objects;

/**
 * Αυτή η κλάση αναπαριστά την αξιολόγηση από ένα Subscriber.
 * Κάθε αξιολόησηση έχει τρία πεδία, το κείμενο, την βαθμολογία και
 *  τον συγγραφέα.
 */

public class Rating implements Serializable {
    private String ratingDocument;
    private double rating;
    private Subscriber author;

    public Rating(){}

    public void setAuthor(Subscriber author) {this.author = author;}
    public Subscriber getAuthor() {return author;}

    public void setRatingDocument(String ratingDocument) {
        this.ratingDocument = ratingDocument;
    }
    public String getRatingDocument() {
        return ratingDocument;
    }

    public void setRating(double rating) {this.rating = rating;}
    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return Double.compare(rating1.rating, rating) == 0 && Objects.equals(ratingDocument, rating1.ratingDocument) && Objects.equals(author, rating1.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingDocument, rating, author);
    }
}
