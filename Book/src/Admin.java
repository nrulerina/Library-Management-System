import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Admin extends User {
    private String adminID;
    private ArrayList<Announcement> announcements;

    public Admin(String name, String email, String address, String phoneNumber,
                String username, String password, Date registrationDate, String adminID) {
        super(name, email, address, phoneNumber, username, password, registrationDate);
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
        saveAnnouncementToFile(announcement);
    }

     private void saveAnnouncementToFile(Announcement announcement) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("announcement.txt", true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writer.write("Date: " + dateFormat.format(announcement.getDate()) + "\n");
            writer.write("Message: " + announcement.getMessage() + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        ArrayList<Book> books = Library.getInstance("My Library", "123 Main St, City").getBooks();
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books available.");
        } else {
            StringBuilder message = new StringBuilder("List of Books:\n");
            for (Book book : books) {
                message.append("- ").append(book.getTitle()).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
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
