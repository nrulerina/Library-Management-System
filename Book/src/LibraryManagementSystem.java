import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class LibraryManagementSystem {
    private static Library library;

    public static void main(String[] args) {
        // Initialize the library
        library = new Library("My Library", "123 Main St, City");

        // Load data from files
        loadUsersFromFile("users.txt");
        loadGenresFromFile("genres.txt");
        loadPublishersFromFile("publishers.txt");
        loadBooksFromFile("books.txt");

        // Start the application
        loginMenu();
    }

    private static void loadUsersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String userType = userData[0].trim(); // Assuming the first value indicates user type
                if (userType.equalsIgnoreCase("admin")) {
                    String name = userData[1].trim();
                    String email = userData[2].trim();
                    String address = userData[3].trim();
                    String phoneNumber = userData[4].trim();
                    String memberID = userData[5].trim();
                    String username = userData[6].trim();
                    String password = userData[7].trim();
                    Date registrationDate = parseDate(userData[8].trim()); // Assuming date is in a specific format
                    String adminID = userData[9].trim();

                    Admin admin = new Admin(name, email, address, phoneNumber, memberID, username, password, registrationDate, adminID);
                    library.addUser(admin); // Assuming addUser method in Library adds users to library
                } else {
                    // Process other types of users (e.g., Member) if needed
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as per your date format in file
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void loadGenresFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("publisher.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Genre genre = new Genre(line.trim()); // Assuming each line is a genre name
                library.addGenre(genre);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadPublishersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("publisher.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] publisherData = line.split(",");
                String name = publisherData[0];
                String address = publisherData[1];
                String email = publisherData[2];

                Publisher publisher = new Publisher(name, address, email);
                library.addPublisher(publisher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadBooksFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title:")) {
                    String title = line.substring(line.indexOf(":") + 2).trim(); // Read title after "Title: "
                    String author = reader.readLine().substring(line.indexOf(":") + 2).trim(); // Read author after "Author: "
                    String isbn = reader.readLine().substring(line.indexOf(":") + 2).trim(); // Read ISBN after "ISBN: "
                    String publisherName = reader.readLine().substring(line.indexOf(":") + 2).trim(); // Read publisher after "Publisher: "
                    Date publishedDate = new Date(Long.parseLong(reader.readLine().substring(line.indexOf(":") + 2).trim())); // Read date after "Published Date: "
                    int copiesAvailable = Integer.parseInt(reader.readLine().substring(line.indexOf(":") + 2).trim()); // Read copies after "Copies Available: "
                    String genreName = reader.readLine().substring(line.indexOf(":") + 2).trim(); // Read genre after "Genre: "
    
                    Publisher publisher = findPublisherByName(publisherName);
                    Genre genre = library.findGenreByName(genreName);
    
                    if (publisher != null && genre != null) {
                        library.addBook(null, title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
                    } else {
                        System.out.println("Publisher or genre not found for book: " + title);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static Publisher findPublisherByName(String name) {
        for (Publisher publisher : library.getPublishers()) {
            if (publisher.getName().equalsIgnoreCase(name)) {
                return publisher;
            }
        }
        return null;
    }

    private static void loginMenu() {
        while (true) {
            String[] options = {"Admin", "Member", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Login as:", "Library Management System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    adminLogin();
                    break;
                case 1:
                    memberLogin();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Exiting the system. Goodbye!");
                    return; // Exit the program
            }
        }
    }

    private static void memberLogin(){

    }

    private static void adminLogin() {
        String username = JOptionPane.showInputDialog("Enter admin username:");
        String password = JOptionPane.showInputDialog("Enter admin password:");

        Admin admin = library.findAdminByUsername(username);

        if (admin != null && admin.login(username, password)) {
            adminMenu(admin);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    private static void adminMenu(Admin admin) {
        boolean logout = false;

        while (!logout) {
            String[] options = {"Add Book", "Remove Book", "Manage Member", "Add Announcement", "Show List of Books",
                    "Show List of Members", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Admin Menu:", "Library Management System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

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
                    admin.showListOfBooks(); // Assuming admin has this method
                    break;
                case 5:
                    admin.showListOfMembers(); // Assuming admin has this method
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Logged out successfully.");
                    logout = true;
                    break;
            }
        }
    }

    private static void addBook(Admin admin) {
        String title = JOptionPane.showInputDialog("Enter book title:");
        String author = JOptionPane.showInputDialog("Enter book author:");
        String isbn = JOptionPane.showInputDialog("Enter book ISBN:");
        String publisherName = JOptionPane.showInputDialog("Enter publisher name:");
        Date publishedDate = new Date(); // Set default published date to current date
        int copiesAvailable = Integer.parseInt(JOptionPane.showInputDialog("Enter copies available:"));
        String genreName = JOptionPane.showInputDialog("Enter book genre:");

        Publisher publisher = findPublisherByName(publisherName);
        Genre genre = library.findGenreByName(genreName);

        if (publisher != null && genre != null) {
            Book newBook = new Book(title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
            library.addBook(admin,title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
            JOptionPane.showMessageDialog(null, "Book added successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Publisher or genre not found.");
        }
    }

    private static void removeBook(Admin admin) {
        String bookTitle = JOptionPane.showInputDialog("Enter book title to remove:");
        Book bookToRemove = findBookByTitle(bookTitle);

        if (bookToRemove != null) {
            library.removeBook(admin, bookToRemove);
            JOptionPane.showMessageDialog(null, "Book removed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Book with title '" + bookTitle + "' not found.");
        }
    }

    private static Book findBookByTitle(String title) {
        for (Book book : library.getBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    private static void manageMember(Admin admin) {
        String memberId = JOptionPane.showInputDialog("Enter member ID to manage:");
        // Placeholder for managing members
        // Implement functionality as needed
        JOptionPane.showMessageDialog(null, "Feature under construction.");
    }

    private static void addAnnouncement(Admin admin) {
        String message = JOptionPane.showInputDialog("Enter announcement message:");
        Announcement announcement = new Announcement(message, new Date());
        admin.addAnnouncement(announcement); // Assuming admin has this method
        JOptionPane.showMessageDialog(null, "Announcement added successfully.");
    }

    // private static void memberLogin() {
    //     String username = JOptionPane.showInputDialog("Enter member username:");
    //     String password = JOptionPane.showInputDialog("Enter member password:");

    //     Member member = library.findMemberByUsername(username);

    //     if (member != null && member.login(username, password)) {
    //         memberMenu(member);
    //     } else {
    //         JOptionPane.showMessageDialog(null, "Invalid username or password.");
    //     }
    // }

    

    

}
