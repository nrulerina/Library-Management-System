import java.util.*;
import javax.swing.*;

public class LibraryMain {

    private static Library library = new Library("Triple Blue Library");

    public static void main(String[] args) {
        // Initialize some data
        initializeLibrary();

        // Display the main menu
        mainMenu();
    }

    /*private static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int role = JOptionPane.showOptionDialog(null, "Welcome to the " + library.getName(), "Choose Your Role ",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Admin", "Member", "Exit"}, "Member");

            if (role == JOptionPane.YES_OPTION) {
                adminMenu(scanner);
            } else if (role == JOptionPane.NO_OPTION) {
                memberMenu(scanner);
            } else if (role == JOptionPane.CANCEL_OPTION) {
                return;
            }

        }
    }

    private static void adminMenu(Scanner scanner) {
        Admin admin = loginAdmin(scanner);
        if (admin == null) {
            JOptionPane.showMessageDialog(null, "Admin not found or incorrect credentials.");
            return;
        }

        while (true) {
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
                    admin.addBook(book);
                    library.addBook(book);
                    JOptionPane.showMessageDialog(null, "Book added successfully.");
                    break;
                case 1:
                    isbn = JOptionPane.showInputDialog("Enter book ISBN to remove:");
                    Book bookToRemove = findBookByIsbn(isbn);
                    if (bookToRemove != null) {
                        admin.removeBook(bookToRemove);
                        library.getBooks().remove(bookToRemove);
                        JOptionPane.showMessageDialog(null, "Book removed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found.");
                    }
                    break;
                case 2:
                    StringBuilder booksList = new StringBuilder("Books in the library:\n");
                    for (Book b : library.getBooks()) {
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

    private static void memberMenu(Scanner scanner) {
        Member member = loginMember(scanner);
        if (member == null) {
            JOptionPane.showMessageDialog(null, "Member not found or incorrect credentials.");
            return;
        }

        while (true) {
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
                    Book bookToBorrow = findBookByIsbn(isbn);
                    if (bookToBorrow != null && bookToBorrow.isAvailable()) {
                        BorrowRecord borrow = new BorrowRecord(member, bookToBorrow);
                        borrow.execute();
                        JOptionPane.showMessageDialog(null, "Book borrowed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not available or not found.");
                    }
                    break;
                case 1:
                    isbn = JOptionPane.showInputDialog("Enter book ISBN to return:");
                    Book bookToReturn = findBookByIsbn(isbn);
                    if (bookToReturn != null && member.getBorrowedBooks().contains(bookToReturn)) {
                        member.returnBook(bookToReturn);
                        JOptionPane.showMessageDialog(null, "Book returned successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "You have not borrowed this book or book not found.");
                    }
                    break;
                case 2:
                    StringBuilder borrowedBooksList = new StringBuilder("Books you have borrowed:\n");
                    for (Book b : member.getBorrowedBooks()) {
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
    }*/

    private static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String[] options = {"Admin", "Member", "Exit"};
            int role = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to the " + library.getName(),
                    "Choose Your Role",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]
            );

            if (role == JOptionPane.YES_OPTION) {
                Admin admin = loginAdmin(scanner);
                if (admin != null) {
                    admin.showMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Admin not found or incorrect credentials.");
                }
            } else if (role == JOptionPane.NO_OPTION) {
                Member member = loginMember(scanner);
                if (member != null) {
                    member.showMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Member not found or incorrect credentials.");
                }
            } else if (role == JOptionPane.CANCEL_OPTION || role == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Exiting the system. Goodbye!");
                return;
            }
        }
    }

    private static Admin loginAdmin(Scanner scanner) {
        String adminId = JOptionPane.showInputDialog("Enter admin ID:");
        for (Admin admin : library.getAdmins()) {
            if (admin.getAdminId().equals(adminId)) {
                return admin;
            }
        }
        return null;
    }

    private static Member loginMember(Scanner scanner) {
        String memberId = JOptionPane.showInputDialog("Enter member ID:");
        for (Member member : library.getMembers()) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : library.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private static void initializeLibrary() {
        // Initialize some admins
        Admin admin1 = new Admin("Mulyani","A002");
        library.addAdmin(admin1);
        Admin admin2 = new Admin("Ramu","A003");
        library.addAdmin(admin2);

        // Initialize some members
        Member member1 = new Member("Arini", "M001");
        library.addMember(member1);
        Member member2 = new Member("Aufa", "M002");
        library.addMember(member2);

        // Initialize some books
        Book book1 = new Book("Atomic Habits", "James Clear", "ISBN12345");
        Book book2 = new Book("Hot To Talk To Anyone", "Leil LownDes", "ISBN67890");
        library.addBook(book1);
        library.addBook(book2);
        admin1.addBook(book1);
        admin1.addBook(book2);
    }
}
