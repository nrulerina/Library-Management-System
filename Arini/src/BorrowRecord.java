import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class BorrowRecord {
    private Member member;
    private Book book;
    private ZonedDateTime borrowDateTime;
    private ZonedDateTime returnDateTime;

    public BorrowRecord(Member m, Book b, ZonedDateTime bDT, ZonedDateTime rDT) {
        this.member = m;
        this.book = b;
        this.borrowDateTime = bDT;
        this.returnDateTime = rDT;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book b) {
        book = b;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member m) {
        member = m; 
    }

    public ZonedDateTime getBorrowDateTime() {
        return borrowDateTime;
    }

    public void setBorrowDateTime(ZonedDateTime bDT) {
        borrowDateTime = bDT;
    }

    public ZonedDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(ZonedDateTime rDT) {
        returnDateTime = rDT;
    }

    public String generateBorrowID() {
        int i=0;
        return "b"+i;
    }

    public double calculateFine() {
        long daysBetween = ChronoUnit.DAYS.between(borrowDateTime, returnDateTime);
        if (daysBetween > 7) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
