import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
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
    private ArrayList<Admin> admins;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.publishers = new ArrayList<>();
        this.users = new ArrayList<>();
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();

    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public static Library getInstance(String name, String address) {
        if (instance == null) {
            instance = new Library(name, address);
        }
        return instance;
    }

    public Member findMemberByUsername(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        return null; // If no member is found
    }
    // Add User
    public void addUser(User user) {
        users.add(user);
    }
    public ArrayList<Book> getBooks() {
        return books; // Assuming 'books' is your list of all books in the library
    }
    
    public void showListOfMembers() {
        if (members.isEmpty()) {
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

    

    // public List<Book> getAvailableBooks() {
    //     List<Book> availableBooks = new ArrayList<>();
    //     for (Book book : books) {
    //         if (book.isAvailable()) { // Implement isAvailable() method in Book class as needed
    //             availableBooks.add(book);
    //         }
    //     }
    //     return availableBooks;
    // }

    public ArrayList<Admin> getAdmins() {
        return admins;
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

    public void addUser(Member member) {
        members.add(member);
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

    public void addMember(String name, String email, String address, String phoneNumber, String username, String password, Date registrationDate, String memberID) {
        Member member = new Member(name, email, address, phoneNumber, username, password, registrationDate, memberID);
        addUser(member);
        JOptionPane.showMessageDialog(null, "Member added successfully.");
    }

    // // Add Book
    public void addBook(String title, String author, String isbn, Publisher publisher, Date publishedDate, int copiesAvailable, Genre genre) {
        Book newBook = new Book(title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
        books.add(newBook);
        // You might also want to update any other internal data structures or files here
    }

private static void updatePublisherFile(Publisher publisher) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("publishers.txt", true))) {
        writer.write(publisher.getName() + "," + publisher.getAddress() + "," + publisher.getEmail());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void showListOfBooks() {
    if (books.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No books available.");
    } else {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append("Title: ").append(book.getTitle()).append("\n")
              .append("Author: ").append(book.getAuthor()).append("\n")
              .append("ISBN: ").append(book.getIsbn()).append("\n")
              .append("Publisher: ").append(book.getPublisher().getName()).append("\n")
              .append("Published Date: ").append(book.getPublishedDate()).append("\n")
              .append("Copies Available: ").append(book.getCopiesAvailable()).append("\n")
              .append("Genre: ").append(book.getGenre().getName()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "List of Books", JOptionPane.PLAIN_MESSAGE);
    }
}


private static void updateGenreFile(Genre genre) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("genres.txt", true))) {
        writer.write(genre.getName());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
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
            addBook(title, author, isbn, publisher, publishedDate, copiesAvailable, genre);
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

    // public Member findMemberByUsername(String username) {
    //     for (User user : users) {
    //         if (user instanceof Member && user.getUsername().equals(username)) {
    //             return (Member) user;
    //         }
    //     }
    //     return null; // Member not found
    // }

    public Admin findAdminByUsername(String username) {
        for (User user : users) {
            if (user instanceof Admin && user.getUsername().equals(username)) {
                return (Admin) user;
            }
        }
        return null; // Admin with the given username not found
    }
    // Other methods (getter/setter for books, etc.) omitted for brevity


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

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }
}
