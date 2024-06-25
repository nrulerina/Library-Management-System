import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
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
    private static void showListOfMembers() {
        ArrayList<Member> members = library.getMembers();
        if (members == null || members.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No members available.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Member member : members) {
                sb.append("Name: ").append(member.getName()).append("\n")
                  .append("Email: ").append(member.getEmail()).append("\n")
                  .append("Address: ").append(member.getAddress()).append("\n")
                  .append("Phone Number: ").append(member.getPhoneNumber()).append("\n")
                  .append("Member ID: ").append(member.getMemberID()).append("\n")
                  .append("Username: ").append(member.getUsername()).append("\n")
                  .append("Registration Date: ").append(member.getRegistrationDate()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "List of Members", JOptionPane.PLAIN_MESSAGE);
        }
    }
    private static void loadUsersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1); // Split by comma, include empty trailing fields
                if (userData.length < 10) {
                    System.err.println("Incomplete user data line: " + line);
                    continue;
                }
    
                String userType = userData[0].trim();
                String name = userData[1].trim();
                String email = userData[2].trim();
                String address = userData[3].trim();
                String phoneNumber = userData[4].trim();
                String memberID = userData[5].trim();
                String username = userData[6].trim();
                String password = userData[7].trim();
                Date registrationDate = parseDate(userData[8].trim());
    
                if (userType.equalsIgnoreCase("admin")) {
                    String adminID = userData[9].trim();
                    Admin admin = new Admin(name, email, address, phoneNumber, memberID, username, password, registrationDate, adminID);
                    library.addUser(admin); // Assuming library has addUser method
                } else if (userType.equalsIgnoreCase("member")) {
                    String userID = userData[9].trim();
                    Member member = new Member(name, email, address, phoneNumber, memberID, username, password, registrationDate, userID);
                    library.addUser(member); // Assuming library has addUser method
                } else {
                    System.err.println("Unknown user type: " + userType);
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String genreName = line.trim();
                Genre genre = new Genre(genreName);
                library.addGenre(genre);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private static void loadPublishersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("publishers.txt"))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title:")) {
                    String title = extractValue(line); // Read title after "Title: "
                    String author = extractValue(reader.readLine()); // Read author after "Author: "
                    String isbn = extractValue(reader.readLine()); // Read ISBN after "ISBN: "
                    String publisherName = extractValue(reader.readLine()); // Read publisher after "Publisher: "
                    String publishedDateStr = extractValue(reader.readLine()); // Read date after "Published Date: "
                    Date publishedDate = parseDate(publishedDateStr);
                    
                    // Read copies available
                    String copiesAvailableStr = extractValue(reader.readLine());
                    int copiesAvailable = 0; // Default value if parsing fails
                    try {
                        copiesAvailable = Integer.parseInt(copiesAvailableStr);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing copies available for book: " + title);
                        e.printStackTrace();
                    }
                    
                    String genreName = extractValue(reader.readLine()); // Read genre after "Genre: "
    
                    Publisher publisher = findPublisherByName(publisherName);
                    Genre genre = library.findGenreByName(genreName);
    
                    if (publisher != null && genre != null) {
                        library.addBook(title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
                    } else {
                        System.out.println("Publisher or genre not found for book: " + title);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String extractValue(String line) {
        if (line == null || line.indexOf(":") == -1) {
            return ""; // Handle cases where line is null or does not contain ":"
        }
        return line.substring(line.indexOf(":") + 2).trim();
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

    private static void memberLogin() {
        String username = JOptionPane.showInputDialog("Enter member username:");
        String password = JOptionPane.showInputDialog("Enter member password:");
    
        Member member = library.findMemberByUsername(username);
    
        if (member != null && member.login(username, password)) {
            memberMenu(member);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }
    

    private static void memberMenu(Member member) {
        while (true) {
            // Display menu options using JOptionPane
            String[] options = {"View Borrowed Books", "View Publishers with Books", "View Genres with Books", "Borrow a Book", "Renew Membership", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Welcome, " + member.getName() + "! Select an option:",
                    "Member Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    
            switch (choice) {
                case 0: // View Borrowed Books
                    // viewBorrowedBooks(member);
                    break;
                case 1: // View Publishers with Books
                    viewPublishersWithBooks();
                    break;
                case 2: // View Genres with Books
                    viewGenresWithBooks();
                    break;
                case 3: // Borrow a Book
                    // borrowBook(member);
                    break;
                case 4: // Renew Membership
                    // renewMembership(member);
                    break;
                case 5: // Logout
                    JOptionPane.showMessageDialog(null, "Logging out...");
                    return; // Exit the method and return to login screen
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    

    private static void viewPublishersWithBooks() {
        // Retrieve publishers with their associated books from the library
        // Example: Construct a message to display publishers and their books using StringBuilder
        StringBuilder message = new StringBuilder("Publishers with Books:\n");
        for (Publisher publisher : library.getPublishers()) {
            message.append("- ").append(publisher.getName()).append(": ");
            for (Book book : library.getBooks()) {
                message.append(book.getTitle()).append(", ");
            }
            message.append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Publishers with Books", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private static void viewGenresWithBooks() {
        // Retrieve genres with their associated books from the library
        // Example: Construct a message to display genres and their books using StringBuilder
        StringBuilder message = new StringBuilder("Genres with Books:\n");
        for (Genre genre : library.getGenres()) {
            message.append("- ").append(genre.getName()).append(": ");
            for (Book book : library.getBooks()) {
                message.append(book.getTitle()).append(", ");
            }
            message.append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Genres with Books", JOptionPane.INFORMATION_MESSAGE);
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
                    library.showListOfBooks(); // Assuming admin has this method
                    break;
                case 5:
                    showListOfMembers(); // Assuming admin has this method
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Logged out successfully.");
                    logout = true;
                    break;
            }
        }
    }

    public static void addBook(Admin admin) {
        while (true) {
            try {
                // Input dialogs for book details
                String title = JOptionPane.showInputDialog("Enter book title:");
                if (title == null || title.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid book title.");
                    continue;
                }
    
                String author = JOptionPane.showInputDialog("Enter book author:");
                if (author == null || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid author name.");
                    continue;
                }
    
                String isbn = JOptionPane.showInputDialog("Enter book ISBN:");
                if (isbn == null || isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid ISBN.");
                    continue;
                }
    
                Date publishedDate = new Date(); // Set default published date to current date
                int copiesAvailable = Integer.parseInt(JOptionPane.showInputDialog("Enter copies available:"));
    
                // Load publishers into JComboBox
                JComboBox<String> publisherComboBox = new JComboBox<>();
                for (Publisher publisher : library.getPublishers()) {
                    publisherComboBox.addItem(publisher.getName());
                }
                publisherComboBox.addItem("Add new publisher");
                int publisherChoice = JOptionPane.showOptionDialog(null, publisherComboBox, "Select Publisher", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    
                Publisher publisher = null;
                if (publisherChoice != -1) {
                    if (publisherComboBox.getSelectedItem().equals("Add new publisher")) {
                        String newPublisherName = JOptionPane.showInputDialog("Enter new publisher name:");
                        String newPublisherAddress = JOptionPane.showInputDialog("Enter new publisher address:");
                        String newPublisherEmail = JOptionPane.showInputDialog("Enter new publisher email:");
                        publisher = new Publisher(newPublisherName, newPublisherAddress, newPublisherEmail);
                        library.addPublisher(publisher);
                        updatePublisherFile(publisher); // Update publisher file here
                    } else {
                        publisher = findPublisherByName((String) publisherComboBox.getSelectedItem());
                    }
                }
    
                // If publisher is not selected or created, continue loop
                if (publisher == null) {
                    JOptionPane.showMessageDialog(null, "Please select or create a publisher.");
                    continue;
                }
    
                // Load genres into JComboBox
                JComboBox<String> genreComboBox = new JComboBox<>();
                for (Genre genre : library.getGenres()) {
                    genreComboBox.addItem(genre.getName());
                }
                genreComboBox.addItem("Add new genre");
                int genreChoice = JOptionPane.showOptionDialog(null, genreComboBox, "Select Genre", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    
                Genre genre = null;
                if (genreChoice != -1) {
                    if (genreComboBox.getSelectedItem().equals("Add new genre")) {
                        String newGenreName = JOptionPane.showInputDialog("Enter new genre name:");
                        genre = new Genre(newGenreName);
                        library.addGenre(genre);
                        updateGenreFile(genre); // Update genre file here
                    } else {
                        genre = library.findGenreByName((String) genreComboBox.getSelectedItem());
                    }
                }
    
                // If genre is not selected or created, continue loop
                if (genre == null) {
                    JOptionPane.showMessageDialog(null, "Please select or create a genre.");
                    continue;
                }
    
                // Add the book only if both publisher and genre are selected or created
                Book newBook = new Book(title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
                library.addBook(title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
                updateBookFile(newBook); // Update book file here
                JOptionPane.showMessageDialog(null, "You have successfully added the book.");
    
                int option = JOptionPane.showOptionDialog(null, "Do you want to add another book?", "Add Book", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (option == JOptionPane.NO_OPTION) {
                    break; // Exit the loop if user selects "No"
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input for copies available. Please enter a valid number.");
            }
        }
    }
    private static void updateBookFile(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt", true))) {
            writer.write("Title: " + book.getTitle());
            writer.newLine();
            writer.write("Author: " + book.getAuthor());
            writer.newLine();
            writer.write("ISBN: " + book.getIsbn());
            writer.newLine();
            writer.write("Publisher: " + book.getPublisher().getName());
            writer.newLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            writer.write("Published Date: " + dateFormat.format(book.getPublishedDate()));
            writer.newLine();
            writer.write("Copies Available: " + book.getCopiesAvailable());
            writer.newLine();
            writer.write("Genre: " + book.getGenre().getName());
            writer.newLine();
            writer.newLine(); // Add a blank line for separation between books
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    

public static void updatePublisherFile(Publisher publisher) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("publishers.txt", true))) {
        writer.write(publisher.getName() + "," + publisher.getAddress() + "," + publisher.getEmail());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void updateGenreFile(Genre genre) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("genres.txt", true))) {
        writer.write(genre.getName());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
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
