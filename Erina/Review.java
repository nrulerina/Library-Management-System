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

   
    public static void addReview(Member member, Book book, String reviewID, int rating, String comment, Date reviewDate) {
        Review review = new Review(reviewID, book, member, rating, comment, reviewDate);
        reviews.add(review);
    }

   
    public static void viewReviewsForBook(Book book) {
        for (Review review : reviews) {
            if (review.book.equals(book)) {
                System.out.println("Review ID: " + review.reviewID);
                System.out.println("Member: " + review.member); // Assuming Member class has a proper toString method
                System.out.println("Rating: " + review.rating);
                System.out.println("Comment: " + review.comment);
                System.out.println("Review Date: " + review.reviewDate);
                System.out.println();
            }
        }
    }

    
    public static void editReview(String reviewID, int rating, String comment) {
        for (Review review : reviews) {
            if (review.reviewID.equals(reviewID)) {
                review.rating = rating;
                review.comment = comment;
                break;
            }
        }
    }

   
    public static void deleteReview(String reviewID) {
        reviews.removeIf(review -> review.reviewID.equals(reviewID));
    }
}