import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Admin extends User {
    private String adminID;
    private ArrayList<Announcement> announce;

    public Admin(String name, String email, String address, String phoneNumber, String memberID, String username, String password, Date dateOfBirth, String adminID) {
        super(name, email, address, phoneNumber, memberID, username, password, dateOfBirth);
        this.adminID = adminID;
        this.announce = new ArrayList<>();
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public void addBookToInventory(Book book) {
        book.setAvailable(true);
        Book.getInventory().add(book);
    }

    public void removeBookFromInventory(String bookID) {
        Book bookToRemove = null;
        for (Book book : Book.getInventory()) {
            if (book.getBookID().equals(bookID)) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            Book.getInventory().remove(bookToRemove);
        }
    }

    public void manageMember(String memberID) {
       
    }

    public void addAnnouncement(Announcement announcement) {
        this.announce.add(announcement);
    }

    public void showListOfBooks() {
        StringBuilder bookList = new StringBuilder("List of Books:\n");
        for (Book book : Book.getInventory()) {
            bookList.append(book.getTitle()).append(" (").append(book.getBookID()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, bookList.toString(), "List of Books", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showListOfMembers() {
        StringBuilder memberList = new StringBuilder("List of Members:\n");
        for (Member member : Member.getMembers()) {
            memberList.append(member.getName()).append(" (").append(member.getMemberID()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, memberList.toString(), "List of Members", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateNewMember() {
        String name = JOptionPane.showInputDialog("Enter new member name:");
        String email = JOptionPane.showInputDialog("Enter new member email:");
        String address = JOptionPane.showInputDialog("Enter new member address:");
        String phoneNumber = JOptionPane.showInputDialog("Enter new member phone number:");
        String memberID = JOptionPane.showInputDialog("Enter new member ID:");
        String username = JOptionPane.showInputDialog("Enter new member username:");
        String password = JOptionPane.showInputDialog("Enter new member password:");
        Date dateOfBirth = new Date();

        Member newMember = new Member(name, email, address, phoneNumber, memberID, username, password, dateOfBirth);
        // Tak isi
    }

    public static void adminMenu(Admin admin) {
        boolean logout = false;

        while (!logout) {
            String[] options = {"Add Book", "Remove Book", "Manage Member", "Add Announcement", "Show List of Books", "Show List of Members", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Admin Menu:", "Library Management System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    addBook(admin);
                    break;
                case 1:
                    removeBook(admin);
                    break;
                case 2:
                    manageMember(admin);
                    break;
                case 3:
                    addAnnouncement(admin);
                    break;
                case 4:
                    admin.showListOfBooks();
                    break;
                case 5:
                    admin.showListOfMembers();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Logged out successfully.");
                    logout = true;
                    break;
            }
        }
    }

    public static void addBook(Admin admin) {
        String bookID = JOptionPane.showInputDialog("Enter book ID:");
        String title = JOptionPane.showInputDialog("Enter book title:");
        String author = JOptionPane.showInputDialog("Enter book author:");
        String genre = JOptionPane.showInputDialog("Enter book genre:");
        int publicationYear = Integer.parseInt(JOptionPane.showInputDialog("Enter book publication year:"));

        Book newBook = new Book(bookID, title, author, genre, publicationYear);
        admin.addBookToInventory(newBook);
        JOptionPane.showMessageDialog(null, "Book added successfully.");
    }

    public static void removeBook(Admin admin) {
        String bookID = JOptionPane.showInputDialog("Enter book ID to remove:");
        admin.removeBookFromInventory(bookID);
        JOptionPane.showMessageDialog(null, "Book removed successfully.");
    }

    public static void manageMember(Admin admin) {
        String memberID = JOptionPane.showInputDialog("Enter member ID to manage:");
        admin.manageMember(memberID);
        // Placeholder for managing members
        // Implement functionality as needed
    }

    public static void addAnnouncement(Admin admin) {
        String message = JOptionPane.showInputDialog("Enter announcement message:");
        Announcement announcement = new Announcement(message, new Date());
        admin.addAnnouncement(announcement);
        JOptionPane.showMessageDialog(null, "Announcement added successfully.");
    }
}