import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
        ArrayList<BorrowRecord> brList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId defaultZoneId = ZoneId.systemDefault();
    
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowrecords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String borrowID = parts[0];
                    String memberID = parts[1];
                    String isbnPart = parts[2];
                    LocalDateTime borrowDateTime = LocalDateTime.parse(parts[3], formatter);
                    LocalDateTime returnDateTime = LocalDateTime.parse(parts[4], formatter);
                    ZonedDateTime borrowZonedDateTime = borrowDateTime.atZone(defaultZoneId);
                    ZonedDateTime returnZonedDateTime = returnDateTime.atZone(defaultZoneId);
                    double fine = Double.parseDouble(parts[5]);
    
                    Library library = new Library();
                    Member member = library.findMemberById(memberID);
                    Book book = library.findBookByIsbnPart(isbnPart);
    
                    if (member != null && book != null) {
                        BorrowRecord br = new BorrowRecord(member, book, borrowZonedDateTime);
                        br.setReturnDateTime(returnZonedDateTime);
                        brList.add(br);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return brList;
    }

     public static ArrayList<ReservationRecord> getReserveRecords() {
        ArrayList<ReservationRecord> rrList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("reserverecords.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rrList.add(new ReservationRecord(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rrList;
    }

}


