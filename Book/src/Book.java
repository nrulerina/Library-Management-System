import java.util.ArrayList;
import java.util.Date;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private Publisher publisher;
    private Date publishedDate;
    private int copiesAvailable;
    // private ArrayList<Review> reviews;
    private Genre genre;

    public Book(String title, String author, String isbn, Publisher publisher, Date publishedDate, int copiesAvailable, Genre genre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.copiesAvailable = copiesAvailable;
        // this.reviews = new ArrayList<>();
        this.genre = genre;
        // if (genre != null) {
        //     genre.addBook(this);
        // }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    // public void addReview(Review review) {
    //     reviews.add(review);
    // }

    // public void removeReview(Review review) {
    //     reviews.remove(review);
    // }

    // public ArrayList<Review> viewReviews() {
    //     return reviews;
    // }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
