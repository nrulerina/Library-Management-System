import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Library {
    private static Library instance; // Singleton instance
    private String name;
    private String address;
    private ArrayList<Book> books;
    private ArrayList<Publisher> publishers;
    private ArrayList<Genre> genres;
    private ArrayList<User> users;
    private ArrayList<Member> members;



    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.publishers = new ArrayList<>();
        this.users = new ArrayList<>();

    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    // Add User
    public void addUser(User user) {
        users.add(user);
    }

    // Add Genre
    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    // Remove Genre
    public void removeGenre(Genre genre) {
        genres.remove(genre);
    }

    // Find Genre by Name
    public Genre findGenreByName(String name) {
        for (Genre genre : genres) {
            if (genre.getName().equalsIgnoreCase(name)) {
                return genre;
            }
        }
        return null;
    }

    // Add Publisher
    public void addPublisher(Publisher publisher) {
        publishers.add(publisher);
    }

    // Remove Publisher
    public void removePublisher(Publisher publisher) {
        publishers.remove(publisher);
    }

    // Find Publisher by Name
    public Publisher findPublisherByName(String name) {
        for (Publisher publisher : publishers) {
            if (publisher.getName().equalsIgnoreCase(name)) {
                return publisher;
            }
        }
        return null;
    }

    // Get all publisher names as an array of strings
    public String[] getAllPublisherNames() {
        String[] publisherNames = new String[publishers.size()];
        for (int i = 0; i < publishers.size(); i++) {
            publisherNames[i] = publishers.get(i).getName();
        }
        return publisherNames;
    }

    // Get all genre names as an array of strings
    public String[] getAllGenreNames() {
        String[] genreNames = new String[genres.size()];
        for (int i = 0; i < genres.size(); i++) {
            genreNames[i] = genres.get(i).getName();
        }
        return genreNames;
    }

    // Add Book
    public void addBook(Admin admin, String title, String author, String isbn, Publisher publisher, Date publishedDate, int copiesAvailable, Genre genre) {
        if (admin != null) {
            Book newBook = new Book(title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
            books.add(newBook);
            saveBookToFile(newBook);
            JOptionPane.showMessageDialog(null, "Book added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Only admin can add books!");
        }
    }

    // Remove Book
    public void removeBook(Admin admin, Book book) {
        if (admin != null) {
            books.remove(book);
            JOptionPane.showMessageDialog(null, "Book removed successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Only admin can remove books!");
        }
    }

    

    // Save Book to File
    private void saveBookToFile(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt", true))) {
            writer.write("Title: " + book.getTitle() + "\n");
            writer.write("Author: " + book.getAuthor() + "\n");
            writer.write("ISBN: " + book.getIsbn() + "\n");
            writer.write("Publisher: " + book.getPublisher().getName() + "\n");
            writer.write("Published Date: " + book.getPublishedDate() + "\n");
            writer.write("Copies Available: " + book.getCopiesAvailable() + "\n");
            writer.write("Genre: " + book.getGenre().getName() + "\n");
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to save book to file.");
        }
    }

    // Add book with interactive input for publisher and genre selection/creation
    public void addBookInteractive(Admin admin) {
        String title = JOptionPane.showInputDialog("Enter book title:");
        String author = JOptionPane.showInputDialog("Enter book author:");
        String isbn = JOptionPane.showInputDialog("Enter book ISBN:");
        Publisher publisher = selectOrCreatePublisher();
        Genre genre = selectOrCreateGenre();
        Date publishedDate = new Date(); // Set default published date to current date
        int copiesAvailable = Integer.parseInt(JOptionPane.showInputDialog("Enter copies available:"));

        if (publisher != null && genre != null) {
            addBook(admin, title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
        } else {
            JOptionPane.showMessageDialog(null, "Publisher or genre not found. Book not added.");
        }
    }

    // Select or create publisher
    private Publisher selectOrCreatePublisher() {
        String[] options = {"Select Existing Publisher", "Create New Publisher"};
        int choice = JOptionPane.showOptionDialog(null, "Select or create publisher:", "Select/Create Publisher",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                return selectExistingPublisher();
            case 1:
                return createNewPublisher();
            default:
                return null;
        }
    }

    public Member findMemberByUsername(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        return null; // Member not found
    }

    // Select existing publisher
    private Publisher selectExistingPublisher() {
        String[] publisherNames = getAllPublisherNames();
        String selectedPublisherName = (String) JOptionPane.showInputDialog(null, "Select publisher:", "Select Publisher",
                JOptionPane.QUESTION_MESSAGE, null, publisherNames, publisherNames[0]);

        if (selectedPublisherName != null) {
            return findPublisherByName(selectedPublisherName);
        } else {
            return null;
        }
    }

    // Create new publisher
    private Publisher createNewPublisher() {
        String name = JOptionPane.showInputDialog("Enter new publisher name:");
        String address = JOptionPane.showInputDialog("Enter new publisher address:");
        String email = JOptionPane.showInputDialog("Enter new publisher email:");

        Publisher newPublisher = new Publisher(name, address, email);
        addPublisher(newPublisher);
        return newPublisher;
    }

    // Select or create genre
    private Genre selectOrCreateGenre() {
        String[] options = {"Select Existing Genre", "Create New Genre"};
        int choice = JOptionPane.showOptionDialog(null, "Select or create genre:", "Select/Create Genre",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                return selectExistingGenre();
            case 1:
                return createNewGenre();
            default:
                return null;
        }
    }

    // Select existing genre
    private Genre selectExistingGenre() {
        String[] genreNames = getAllGenreNames();
        String selectedGenreName = (String) JOptionPane.showInputDialog(null, "Select genre:", "Select Genre",
                JOptionPane.QUESTION_MESSAGE, null, genreNames, genreNames[0]);

        if (selectedGenreName != null) {
            return findGenreByName(selectedGenreName);
        } else {
            return null;
        }
    }

    // Create new genre
    private Genre createNewGenre() {
        String name = JOptionPane.showInputDialog("Enter new genre name:");

        Genre newGenre = new Genre(name);
        addGenre(newGenre);
        return newGenre;
    }

    

    public Admin findAdminByUsername(String username) {
        for (User user : users) {
            if (user instanceof Admin && user.getUsername().equals(username)) {
                return (Admin) user;
            }
        }
        return null; // Admin with the given username not found
    }
    // Other methods (getter/setter for books, etc.) omitted for brevity

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }
}
