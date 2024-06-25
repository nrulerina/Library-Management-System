import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Reminder {
    private Member member;
    private Book book;
    private BorrowRecord br;
    private ReservationRecord rr;
    private ZonedDateTime reminderDate;

    public Reminder (Book b, Member m, ZonedDateTime rD) {
        this.book = b;
        this.member= m;
        this.reminderDate = rD;
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

    public ZonedDateTime getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(ZonedDateTime remD) {
        reminderDate = remD;
    }
}


