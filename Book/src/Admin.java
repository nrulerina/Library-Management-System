import java.util.ArrayList;
import java.util.Date;

public class Admin extends User {
    private String adminID;
    private ArrayList<Announcement> announcements;

    public Admin(String name, String email, String address, String phoneNumber, String memberID,
                String username, String password, Date dateOfBirth, String adminID) {
        super(name, email, address, phoneNumber, memberID, username, password, dateOfBirth);
        this.adminID = adminID;
        this.announcements = new ArrayList<>();
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void addAnnouncement(Announcement announcement) {
        announcements.add(announcement);
    }

    public void removeAnnouncement(Announcement announcement) {
        announcements.remove(announcement);
    }

    @Override
    public boolean login(String username, String password) {
        // Custom implementation for admin login if needed
        return super.login(username, password);
    }

    public void showListOfBooks() {
        // Implement logic to show list of books
        Library library = new Library("My Library", "123 Main St, City"); // Example instantiation
        ArrayList<Book> books = library.getBooks(); // Assuming getBooks() returns the list of books

        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("List of Books:");
            for (Book book : books) {
                System.out.println(book); // Assuming Book class overrides toString() method
            }
        }
    }

    public void showListOfMembers() {
        // Implement logic to show list of members
        Library library = new Library("My Library", "123 Main St, City"); // Example instantiation
        ArrayList<Member> members = library.getMembers(); // Assuming getMembers() returns the list of members

        if (members.isEmpty()) {
            System.out.println("No members available.");
        } else {
            System.out.println("List of Members:");
            for (User member : members) {
                System.out.println(member); // Assuming User class overrides toString() method
            }
        }
    }
}
