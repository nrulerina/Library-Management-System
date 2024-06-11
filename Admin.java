import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Admin extends User {
    private String name;
    private String adminid;
    private List<Book> managedBooks;
    LibraryMain libraryMain;
    Library library;

    public Admin(String n,String id) {
        super(n);
        this.adminid=id;
        this.managedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAdminId() {
        return adminid;
    }

    public void addBook(Book book) {
        managedBooks.add(book);
    }

    public void removeBook(Book book) {
        managedBooks.remove(book);
    }

    public List<Book> getManagedBooks() {
        return managedBooks;
    }

    @Override
    public void showMenu() {
        String[] options = {"Add Book", "Remove Book", "View Books", "Logout"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Admin Menu",
                "Choose an option",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0:
                String title = JOptionPane.showInputDialog("Enter book title:");
                String author = JOptionPane.showInputDialog("Enter book author:");
                String isbn = JOptionPane.showInputDialog("Enter book ISBN:");
                Book book = new Book(title, author, isbn);
                libraryMain.library.addBook(book);
                JOptionPane.showMessageDialog(null, "Book added successfully.");
                break;
            case 1:
                isbn = JOptionPane.showInputDialog("Enter book ISBN to remove:");
                Book bookToRemove = LibraryMain.findBookByIsbn(isbn);
                if (bookToRemove != null) {
                    libraryMain.library.removeBook(bookToRemove);
                    JOptionPane.showMessageDialog(null, "Book removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found.");
                }
                break;
            case 2:
                StringBuilder booksList = new StringBuilder("Books in the library:\n");
                for (Book b : libraryMain.library.getBooks()) {
                    booksList.append(b).append("\n");
                }
                JOptionPane.showMessageDialog(null, booksList.toString());
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Logging out.");
                return;
            default:
                JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
        }
    }
    
}
