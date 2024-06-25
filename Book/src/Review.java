import java.util.ArrayList;
import java.util.Date;  
import java.util.List;  
import java.util.UUID;
// Review class
public class Review {
    private String reviewID;
    private Book book;
    private Member member;
    private int rating;
    private String comment;
    private Date reviewDate;

    private static List<Review> reviews = new ArrayList<>();

    public Review(String reviewID, Book book, Member member, int rating, String comment, Date reviewDate) {
        this.reviewID = reviewID;
        this.book = book;
        this.member = member;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Add review for a specific book
    public static void addReview(Member member, Book book, int rating, String comment) {
        String reviewID = "R-" + UUID.randomUUID().toString().substring(0, 4); // Generate review ID
        Date reviewDate = new Date(); // Use current date/time for review date
        Review review = new Review(reviewID, book, member, rating, comment, reviewDate);
        reviews.add(review);
    }

    // View reviews for a specific book
    public static void viewReviewsForBook(Book book) {
        boolean found = false;
        for (Review review : reviews) {
            if (review.book.equals(book)) {
                System.out.println("Review ID: " + review.reviewID);
                System.out.println("Member: " + review.member.getName());
                System.out.println("Rating: " + review.rating);
                System.out.println("Comment: " + review.comment);
                System.out.println("Review Date: " + review.reviewDate);
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No reviews found for this book.");
        }
    }

    // Edit review based on review ID
    public static void editReview(String reviewID, int rating, String comment) {
        for (Review review : reviews) {
            if (review.reviewID.equals(reviewID)) {
                review.rating = rating;
                review.comment = comment;
                break;
            }
        }
    }

    // Delete review based on review ID
    public static void deleteReview(String reviewID) {
        reviews.removeIf(review -> review.reviewID.equals(reviewID));
    }
}