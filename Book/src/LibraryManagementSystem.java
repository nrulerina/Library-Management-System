import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class LibraryManagementSystem {
    private static Library library;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    String phoneNumber = userData[5].trim();
                    String username = userData[6].trim();
                    String password = userData[7].trim();
                    Date registrationDate = parseDate(userData[8].trim()); // Assuming date is in a specific format
                    String adminID = userData[9].trim();

                    Admin admin = new Admin(name, email, address, phoneNumber, username, password, registrationDate, adminID);
                    library.addUser(admin); // Assuming addUser method in Library adds users to library
                } else if (userType.equalsIgnoreCase("member")){
                    String name = userData[1].trim();
                    String email = userData[2].trim();
                    String address = userData[3].trim();
                    String phoneNumber = userData[5].trim();
                    String username = userData[6].trim();
                    String password = userData[7].trim();
                    Date registrationDate = parseDate(userData[8].trim()); // Assuming date is in a specific format
                    String memberID = userData[9].trim();

                    Member member = new Member(name, email, address, phoneNumber, username, password, registrationDate, memberID);
                    library.addUser(member);
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
    

    public static void memberMenu(Member member) {
        while (true) {
            String[] options = {"Borrow Book", "Return Book", "View Borrowed Books", "View Reservation Queue", "Review Book", "View Library Card", "Show List of Books", "View Reviews", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Member Menu:", "Library Management System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    
            switch (choice) {
                case 0: // Borrow Book
                    String bookID = JOptionPane.showInputDialog("Enter the last 5 digits of the book ISBN to borrow:");
                    for (Book book : library.getBooks()) {
                        if (book.getIsbn().endsWith(bookID)) {
                            BorrowRecord br = new BorrowRecord(member, book, ZonedDateTime.now());
                            br.saveToFile();
                            JOptionPane.showMessageDialog(null, "Book borrowed successfully!\n" + br);
                        }
                    }
                    break;
                case 1: // Return Book
                    String borrowID = JOptionPane.showInputDialog("Enter borrow ID to return:");
                    BorrowRecord.removeRecord(borrowID); // Remove from file
                    JOptionPane.showMessageDialog(null, "Book removed successfully!");
                    break;
                /*case 4: // Review Book
                    String bookID = JOptionPane.showInputDialog("Enter book ID to review:");
                    int rating = Integer.parseInt(JOptionPane.showInputDialog("Enter rating (1-5):"));
                    String comment = JOptionPane.showInputDialog("Enter comment:");
                    Review.addReview(member, findBookByID(bookID), rating, comment);

                // Display the added review information
                    System.out.println("Review added successfully:");
                    System.out.println("Book ID: " + bookID);
                    System.out.println("Rating: " + rating);
                    System.out.println("Comment: " + comment);
                    System.out.println();

                // Return to member menu
                    break;
                /*case 2: // View Borrowed Books
                    member.showBorrowedBooks();
                    break;
                case 3: // View Reservation Queue
                    showReservationQueue();
                    break;
                case 4: // Review Book
                    bookID = JOptionPane.showInputDialog("Enter book ID to review:");
                    int rating = Integer.parseInt(JOptionPane.showInputDialog("Enter rating (1-5):"));
                    String comment = JOptionPane.showInputDialog("Enter comment:");
                    reviewBook(bookID, rating, comment);
                    break;*/
                    

// Assuming this code is inside a method or a class where JOptionPane can be used
case 4: // Review Book
                    reviewBook(member);
                    break;
                
                
                case 5: // View Library Card
                viewLibraryCard(member);
                    break;
                case 6:  //Show List of Books
                    showBooksSelectionMenu();
                    break;

                    case 7: // View Reviews
                    viewReviews(member);    
    
                case 8: // Logout
                    JOptionPane.showMessageDialog(null, "Logged out successfully.");
                    return; 
            }
        }
    }

     private static void reviewBook(Member member) {
        String bookID = JOptionPane.showInputDialog("Enter the last 5 digits of the book ISBN to review:");
        Book book = findBookByID(bookID);
        if (book != null) {
            try {
                int rating = Integer.parseInt(JOptionPane.showInputDialog("Enter rating (1-5):"));
                if (rating < 1 || rating > 5) {
                    throw new NumberFormatException(); // Ensure rating is between 1 and 5
                }
                String comment = JOptionPane.showInputDialog("Enter comment:");

                // Add the review
                Review.addReview(member, book, rating, comment);
                JOptionPane.showMessageDialog(null, "Review added successfully.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid rating format. Please enter a number between 1 and 5.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Book not found.");
        }
    }

    private static void viewReviews(Member member) {
        List<Review> memberReviews = Review.getReviewsForMember(member);
        StringBuilder reviewList = new StringBuilder("Your Reviews:\n");
        if (memberReviews.isEmpty()) {
            reviewList.append("No reviews found.");
        } else {
            for (Review review : memberReviews) {
                reviewList.append("Review ID: ").append(review.getReviewID()).append("\n");
                reviewList.append("Book: ").append(review.getBook().getTitle()).append("\n");
                reviewList.append("Rating: ").append(review.getRating()).append("\n");
                reviewList.append("Comment: ").append(review.getComment()).append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(null, reviewList.toString());
    }

    private static Book findBookByID(String bookID) {
        for (Book book : library.getBooks()) {
            if (book.getIsbn().endsWith(bookID)) {
                return book;
            }
        }
        return null; // Book not found
    }


    public static void showBooksSelectionMenu() {
        String[] options = {"Genre with Books", "Publisher with Books", "Show List of Books"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Book Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    
        switch (choice) {
            case 0:  // Genre with Books
                viewBooksByGenre();
                showBooksSelectionMenu();
                break;
            case 1:  // Publisher with Books
                viewBooksByPublisher();
                showBooksSelectionMenu();
                break;
            case 2:  // Show List of Books
                library.showListOfBooks(); // Assuming admin has this method
                showBooksSelectionMenu();
                break;
            default:
                break;
        }
    }
    

    private static void viewBooksByGenre() {
        while (true) {
            JComboBox<String> genreComboBox = new JComboBox<>();
            for (Genre genre : library.getGenres()) {
                genreComboBox.addItem(genre.getName());
            }
    
            int option = JOptionPane.showOptionDialog(null, genreComboBox,
                    "Select Genre", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, null, null);
    
            if (option == JOptionPane.CLOSED_OPTION) {
                return; // If user cancels, return from method
            }
    
            String selectedGenre = (String) genreComboBox.getSelectedItem();
            StringBuilder sb = new StringBuilder();
            boolean foundBooks = false;
            for (Book book : library.getBooks()) {
                if (book.getGenre().getName().equals(selectedGenre)) {
                    sb.append("Title: ").append(book.getTitle()).append("\n")
                            .append("Author: ").append(book.getAuthor()).append("\n")
                            .append("ISBN: ").append(book.getIsbn()).append("\n")
                            .append("Publisher: ").append(book.getPublisher().getName()).append("\n")
                            .append("Published Date: ").append(dateFormat.format(book.getPublishedDate())).append("\n")
                            .append("Copies Available: ").append(book.getCopiesAvailable()).append("\n")
                            .append("Genre: ").append(book.getGenre().getName()).append("\n\n");
                    foundBooks = true;
                }
            }
    
            if (!foundBooks) {
                JOptionPane.showMessageDialog(null, "No books recorded for genre: " + selectedGenre, "No Books", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, sb.toString(), "Books in Genre: " + selectedGenre, JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    
    private static void viewBooksByPublisher() {
        while (true) {
            JComboBox<String> publisherComboBox = new JComboBox<>();
            for (Publisher publisher : library.getPublishers()) {
                publisherComboBox.addItem(publisher.getName());
            }
    
            int option = JOptionPane.showOptionDialog(null, publisherComboBox,
                    "Select Publisher", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, null, null);
    
            if (option == JOptionPane.CLOSED_OPTION) {
                return; // If user cancels, return from method
            }
    
            String selectedPublisher = (String) publisherComboBox.getSelectedItem();
            StringBuilder sb = new StringBuilder();
            boolean foundBooks = false;
            for (Book book : library.getBooks()) {
                if (book.getPublisher().getName().equals(selectedPublisher)) {
                    sb.append("Title: ").append(book.getTitle()).append("\n")
                            .append("Author: ").append(book.getAuthor()).append("\n")
                            .append("ISBN: ").append(book.getIsbn()).append("\n")
                            .append("Publisher: ").append(book.getPublisher().getName()).append("\n")
                            .append("Published Date: ").append(dateFormat.format(book.getPublishedDate())).append("\n")
                            .append("Copies Available: ").append(book.getCopiesAvailable()).append("\n")
                            .append("Genre: ").append(book.getGenre().getName()).append("\n\n");
                    foundBooks = true;
                }
            }
    
            if (!foundBooks) {
                JOptionPane.showMessageDialog(null, "No books recorded for publisher: " + selectedPublisher, "No Books", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, sb.toString(), "Books by Publisher: " + selectedPublisher, JOptionPane.PLAIN_MESSAGE);
            }
        }
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
                    removeBooks(admin);
                    break;
                case 2:
                    manageMember(admin);
                    break;
                case 3:
                    addAnnouncement(admin);
                    break;
                case 4:
                    showBooksSelectionMenu();
                    break;                    
                case 5:
                    library.showListOfMembers(); // Assuming admin has this method
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Logged out successfully.");
                    logout = true;
                    break;
            }
        }
    }

      private static void viewLibraryCard(Member member) {
        // Display library card information for the member
        LibraryCard libraryCard = member.getLibraryCard();
        JOptionPane.showMessageDialog(null, libraryCard.getFormattedDetails(), "Library Card", JOptionPane.PLAIN_MESSAGE);
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
    
                String publishedDateString = JOptionPane.showInputDialog("Enter book published date (YYYY-MM-DD):");
        Date publishedDate = null;
        try {
            publishedDate = dateFormat.parse(publishedDateString);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
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

public static void removeBooks(Admin admin) {
    String isbnSuffix = JOptionPane.showInputDialog("Enter the last 5 digits of the book's ISBN to remove:");
    Book bookToRemove = findBookByIsbnSuffix(isbnSuffix);

    if (bookToRemove != null) {
        if (admin != null) {
            library.removeBook(admin,bookToRemove);
            updateBooksFile();
            JOptionPane.showMessageDialog(null, "Book removed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Only admin can remove books!");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Book with ISBN ending '" + isbnSuffix + "' not found.");
    }
}

private static Book findBookByIsbnSuffix(String isbnSuffix) {
    for (Book book : library.getBooks()) {
        if (book.getIsbn().endsWith(isbnSuffix)) {
            return book;
        }
    }
    return null;
}

private static void updateBooksFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
        for (Book book : library.getBooks()) {
            writer.write("Title: " + book.getTitle());
            writer.newLine();
            writer.write("Author: " + book.getAuthor());
            writer.newLine();
            writer.write("ISBN: " + book.getIsbn());
            writer.newLine();
            writer.write("Publisher: " + book.getPublisher().getName());
            writer.newLine();
            writer.write("Published Date: " + book.getPublishedDate());
            writer.newLine();
            writer.write("Copies Available: " + book.getCopiesAvailable());
            writer.newLine();
            writer.write("Genre: " + book.getGenre().getName());
            writer.newLine();
            writer.newLine(); // Add a blank line to separate books
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error updating books file: " + e.getMessage());
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

    

    

    

}
