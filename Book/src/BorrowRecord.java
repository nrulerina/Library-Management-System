import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
//import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecord {
    private static int borrowCount;
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
        this.borrowID = "B"+ borrowCount++;
    }

    public String getBorrowID() {
        return borrowID;
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

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("borrowrecords.txt", true))) {
            writer.write(toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(0, 5) : book.getIsbn();
        return getBorrowID() + "," +
                member.getMemberID() + "," +
                isbnPart + "," +
                borrowDateTime.format(formatter) + "," +
                returnDateTime.format(formatter) + "," +
                getFine();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String currentDateTimeFormatted = ZonedDateTime.now().format(formatter);
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(0, 5) : book.getIsbn();
        borrowID = "B"+borrowCount;

        return "\nBorrowing Details:\n" +
                "\nBorrowID: " + getBorrowID() +
                "\nMemberID: " + member.getMemberID() +
                "\nBook ID: " + isbnPart +
                "\nBorrow Date & Time: " + borrowDateTime.format(formatter) +
                "\nReturn Date & Time: " + returnDateTime.format(formatter) +
                "\nCurrent Date & Time: " + currentDateTimeFormatted +
                "\nFine: " + getFine();
    }


    public void removeRecord(String borrowID) {
        try {
            Path path = Paths.get("borrowrecords.txt");
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (!line.contains(borrowID)) {
                    updatedLines.add(line);
                }
            }

            Files.write(path, updatedLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
