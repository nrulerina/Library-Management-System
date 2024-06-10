import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class ReserveBook {
    private Map<Member, Book> reservations;
    private Date rDate;

    public ReserveBook() {
        this.reservations = new HashMap<>();
    }

    public void reserve(Member member, Book book) {
        if (book.isAvailable()) {
            reservations.put(member, book);
            book.setAvailable(false);
            System.out.println(member.getName() + " reserved " + book.getTitle());
        } else {
            System.out.println("Book is not available for reservation.");
        }
    }

    public void cancelReservation(Member member) {
        Book book = reservations.remove(member);
        if (book != null) {
            book.setAvailable(true);
            System.out.println(member.getName() + " canceled reservation for " + book.getTitle());
        } else {
            System.out.println("No reservation found for " + member.getName());
        }
    }
}
