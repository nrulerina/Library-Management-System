import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationRecord {
    private String reserveID;
    private Member member;
    private Book book;
    private ZonedDateTime reserveDateTime;

    public ReservationRecord(Member m, Book b, ZonedDateTime resDT) {
        this.member = m;
        this.book = b;
        this.reserveDateTime = resDT;
        this.reserveID = generateReserveID(resDT);
    }

    private String generateReserveID(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "R-"+member.getMemberID()+ book.getIsbn() + dateTime.format(formatter);
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

    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String currentDateTimeFormatted = ZonedDateTime.now().format(formatter);

         return "\nReservation Details:\n" +
               "\nReserveID: " + reserveID +
               "\nMemberID: " + member.getMemberID() +
               "\nBookID: " + book.getIsbn() +
               "\nReserve Date & Time: " + reserveDateTime.format(formatter) +
               "\nCurrent Date & Time: " + currentDateTimeFormatted ;
    }

}
