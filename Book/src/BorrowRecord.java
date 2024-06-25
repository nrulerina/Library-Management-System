import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BorrowRecord {
    private String borrowID;
    private Member member;
    private Book book;
    private ZonedDateTime borrowDateTime;
    private ZonedDateTime returnDateTime;
    //private ArrayList<BorrowRecord> brList;

    public BorrowRecord(Member m, Book b, ZonedDateTime bDT) {
        this.member = m;
        this.book = b;
        this.borrowDateTime = bDT;
        this.returnDateTime = bDT.plusDays(7);
        this.borrowID = generateBorrowID(bDT);
        //brList = new ArrayList<>();
    }

    private String generateBorrowID(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = dateTime.format(formatter);
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(0, 5) : book.getIsbn();
        return "B-" + member.getMemberID() + isbnPart + formattedDate;
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

    public double getFine() {
        ZonedDateTime now = ZonedDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(borrowDateTime, now);
        if (daysBetween > 7) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String currentDateTimeFormatted = ZonedDateTime.now().format(formatter);
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(0, 5) : book.getIsbn();

        return "\nBorrowing Details:\n" +
                "\nBorrowID: " + borrowID +
                "\nMemberID: " + member.getMemberID() +
                "\nBook ID: " + isbnPart +
                "\nBorrow Date & Time: " + borrowDateTime.format(formatter) +
                "\nReturn Date & Time: " + returnDateTime.format(formatter) +
                "\nCurrent Date & Time: " + currentDateTimeFormatted +
                "\nFine: " + getFine();
    }

    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(0, 5) : book.getIsbn();
        return borrowID + "," +
                member.getMemberID() + "," +
                isbnPart + "," +
                borrowDateTime.format(formatter) + "," +
                returnDateTime.format(formatter) + "," +
                getFine();
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving borrow record: " + e.getMessage());
        }
    }
}
