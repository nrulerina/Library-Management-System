import java.time.ZonedDateTime;

public class ReservationRecord {
    private Member member;
    private Book book;
    private ZonedDateTime reserveDateTime;

    public ReservationRecord(Member m, Book b, ZonedDateTime resDT) {
        this.member = m;
        this.book = b;
        this.reserveDateTime = resDT;
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

    public ZonedDateTime getReserveDateTime() {
        return reserveDateTime;
    }

    public void setBorrowDateTime(ZonedDateTime resDT) {
        reserveDateTime = resDT;
    }


    public String generateReserveID() {
        int i=0;
        return "r"+i;
    }
}
