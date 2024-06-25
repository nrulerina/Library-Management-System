import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

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

    

    public String getReviewID() {
        return reviewID;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public static List<Review> getReviews() {
        return reviews;
    }

    public static void addReview(Member member, Book book, int rating, String comment) {
        String reviewID = "R-" + UUID.randomUUID().toString().substring(0, 4);
        Date reviewDate = new Date();
        Review review = new Review(reviewID, book, member, rating, comment, reviewDate);
        reviews.add(review);
    }

    public static void editReview(String reviewID, int newRating, String newComment) {
        Review review = findReviewByID(reviewID);
        if (review != null) {
            review.setRating(newRating);
            review.setComment(newComment);
            JOptionPane.showMessageDialog(null, "Review edited successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Review not found.");
        }
    }

    public static void deleteReview(String reviewID) {
        Review review = findReviewByID(reviewID);
        if (review != null) {
            reviews.remove(review);
            JOptionPane.showMessageDialog(null, "Review deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Review not found.");
        }
    }

    public static Review findReviewByID(String reviewID) {
        for (Review review : reviews) {
            if (review.getReviewID().equals(reviewID)) {
                return review;
            }
        }
        return null;
    }

    public static List<Review> getReviewsForMember(Member member) {
        List<Review> memberReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getMember().equals(member)) {
                memberReviews.add(review);
            }
        }
        return memberReviews;
    }
}
