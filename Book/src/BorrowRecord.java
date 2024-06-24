import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class BorrowRecord {
    private Member member;
    private Book book;
    private Date borrowDate;
    private Date returnDate;

    public BorrowRecord(Member member, Book book, Date borrowDate, Date returnDate) {
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}

class ReservationQueue {
    private Member member;
    private Book book;

    public ReservationQueue(Member member, Book book) {
        this.member = member;
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }
}