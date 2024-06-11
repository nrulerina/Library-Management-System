import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Member extends User{
    private String name;
    private String memberId;
    private List<Book> borrowedBooks;
    private LibraryMain libraryMain;

    public Member(String name, String memberId) {
        super(name);
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailable(false);
        } else {
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setAvailable(true);
        } else {
            System.out.println("This book was not borrowed by you.");
        }
    }

    @Override
    public void showMenu() {
        String[] options = {"Borrow Book", "Return Book", "View Borrowed Books", "Logout"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Member Menu",
                "Choose an option",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0:
                String isbn = JOptionPane.showInputDialog("Enter book ISBN to borrow:");
                Book bookToBorrow = libraryMain.findBookByIsbn(isbn);
                if (bookToBorrow != null && bookToBorrow.isAvailable()) {
                    BorrowRecord borrowBook = new BorrowRecord(this, bookToBorrow);
                    borrowBook.execute();
                    JOptionPane.showMessageDialog(null, "Book borrowed successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not available or not found.");
                }
                break;
            case 1:
                isbn = JOptionPane.showInputDialog("Enter book ISBN to return:");
                Book bookToReturn = libraryMain.findBookByIsbn(isbn);
                if (bookToReturn != null && this.getBorrowedBooks().contains(bookToReturn)) {
                    this.returnBook(bookToReturn);
                    JOptionPane.showMessageDialog(null, "Book returned successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "You have not borrowed this book or book not found.");
                }
                break;
            case 2:
                StringBuilder borrowedBooksList = new StringBuilder("Books you have borrowed:\n");
                for (Book b : this.getBorrowedBooks()) {
                    borrowedBooksList.append(b).append("\n");
                }
                JOptionPane.showMessageDialog(null, borrowedBooksList.toString());
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Logging out.");
                return;
            default:
                JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
        }
    }
}
