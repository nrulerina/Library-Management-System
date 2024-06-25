import java.time.*;
import java.util.*;

public class Reminder {
    private Member member;
    private Book book;
    private ArrayList<BorrowRecord> brList;
    private ArrayList<ReservationRecord> rrList;
    private ZonedDateTime reminderDate;

    public Reminder (Member m) {
        this.member= m;
        brList=new ArrayList<>();
        rrList=new ArrayList<>();
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

    public void addBorrowRecord(BorrowRecord br) {
        brList.add(br);
    }

    public void removeBorrowRecord(BorrowRecord br) {
        brList.remove(br);
    }

    public ArrayList<BorrowRecord> getBorrowRecords() {
        return brList;
    }

    public void addReservationRecord(ReservationRecord rr) {
        rrList.add(rr);
    }
}


