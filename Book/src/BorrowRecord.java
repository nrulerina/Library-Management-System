import java.io.*;
import java.nio.file.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.swing.JOptionPane;

public class BorrowRecord {
    private static int borrowCount;
    private String borrowID;
    private Member member;
    private Book book;
    private ZonedDateTime borrowDateTime;
    private ZonedDateTime returnDateTime;

    public BorrowRecord(Member m, Book b, ZonedDateTime bDT) {
        this.member = m;
        this.book = b;
        this.borrowDateTime = bDT;
        this.returnDateTime = bDT.plusDays(7);
        this.borrowID = "B"+ ++borrowCount;
    }

    public BorrowRecord(String line) {
        String[] parts = line.split(",");
        this.borrowID = parts[0];
        String memberID = parts[1]; 
        String bookID = parts[2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.borrowDateTime = ZonedDateTime.parse(parts[3], formatter);
        this.returnDateTime = ZonedDateTime.parse(parts[4], formatter);
        double fine = Double.parseDouble(parts[5]);
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
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(book.getIsbn().length() - 5) : book.getIsbn();

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
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(book.getIsbn().length() - 5) : book.getIsbn();
    
        return "\nBorrowing Details:\n" +
                "\nBorrowID: " + getBorrowID() +
                "\nMemberID: " + member.getMemberID() +
                "\nBook ID: " + isbnPart +
                "\nBorrow Date & Time: " + borrowDateTime.format(formatter) +
                "\nReturn Before: " + returnDateTime.format(formatter) +
                "\nCurrent Date & Time: " + currentDateTimeFormatted +
                "\nFine: " + getFine();
    }


    public static void removeRecord(String borrowID) {
        try {
            Path path = Paths.get("borrowrecords.txt");
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.contains(borrowID)) {
                    // Show confirmation dialog
                    int response = JOptionPane.showConfirmDialog(null, 
                        "Confirm Remove?\n" + line, 
                        "Confirm Removal", 
                        JOptionPane.YES_NO_OPTION);
                    
                    if (response == JOptionPane.YES_OPTION) {
                        // Skip adding this line to updatedLines, effectively removing it
                        continue;
                    }
                }
                updatedLines.add(line);
            }

            Files.write(path, updatedLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
