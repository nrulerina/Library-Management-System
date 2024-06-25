import java.io.*;
import java.nio.file.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.swing.JOptionPane;

public class ReservationRecord {
    private static int reserveCount;
    private String reserveID;
    private Member member;
    private Book book;
    private ZonedDateTime reserveDateTime;

    public ReservationRecord(Member m, Book b, ZonedDateTime resDT) {
        this.member = m;
        this.book = b;
        this.reserveDateTime = resDT;
        this.reserveID = "R"+ ++reserveCount;
    }

    public String getReserveID() {
        return reserveID;
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

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reserverecords.txt", true))) {
            writer.write(toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(0, 5) : book.getIsbn();
        return getReserveID() + "," +
                member.getMemberID() + "," +
                isbnPart + "," +
                reserveDateTime.format(formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String currentDateTimeFormatted = ZonedDateTime.now().format(formatter);
        String isbnPart = book.getIsbn().length() > 5 ? book.getIsbn().substring(book.getIsbn().length() - 5) : book.getIsbn();

        return "\nReservation Details:\n" +
                "\nReserveID: " + getReserveID() +
                "\nMemberID: " + member.getMemberID() +
                "\nBook ID: " + isbnPart +
                "\nReserve Date & Time: " + reserveDateTime.format(formatter) +
                "\nEstimate Available: " + " " +
                "\nCurrent Date & Time: " + currentDateTimeFormatted;
    }

    public static void removeRecord(String reserveID) {
        try {
            Path path = Paths.get("reserverecords.txt");
            List<String> lines = Files.readAllLines(path);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.contains(reserveID)) {
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
