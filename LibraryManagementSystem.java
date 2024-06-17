import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

// class Book {
//     private String bookID;
//     private String title;
//     private String author;
//     private String genre;
//     private int publicationYear;
//     private boolean available;
//     private static ArrayList<Book> inventory = new ArrayList<>();

//     public Book(String bookID, String title, String author, String genre, int publicationYear) {
//         this.bookID = bookID;
//         this.title = title;
//         this.author = author;
//         this.genre = genre;
//         this.publicationYear = publicationYear;
//         this.available = true;
//         inventory.add(this);
//     }

//     public void setAvailable(boolean available) {
//         this.available = available;
//     }

//     public String getBookID() {
//         return bookID;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public String getAuthor() {
//         return author;
//     }

//     public String getGenre() {
//         return genre;
//     }

//     public int getPublicationYear() {
//         return publicationYear;
//     }

//     public boolean isAvailable() {
//         return available;
//     }

//     public void setBorrowed(boolean borrowed) {
//         this.available = !borrowed;
//     }

//     public static ArrayList<Book> getInventory() {
//         return inventory;
//     }

//     public void setGenre(String genre) {
//         this.genre = genre;
//     }
// }

class User {
    protected String name;
    protected String email;
    protected String address;
    protected String phoneNumber;
    protected String memberID;
    protected Date dateOfBirth;
    protected String username;
    protected String password;

    public User(String name, String email, String address, String phoneNumber, String memberID, String username, String password, Date dateOfBirth) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.memberID = memberID;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

class Member extends User {
    private ArrayList<BorrowRecord> borrowList;
    private ArrayList<ReservationQueue> reserveList;
    private static ArrayList<Member> members = new ArrayList<>();

    public Member(String name, String email, String address, String phoneNumber, String memberID, String username, String password, Date dateOfBirth) {
        super(name, email, address, phoneNumber, memberID, username, password, dateOfBirth);
        this.borrowList = new ArrayList<>();
        this.reserveList = new ArrayList<>();
        members.add(this);
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            BorrowRecord borrowRecord = new BorrowRecord(this, book, new Date(), null);
            borrowList.add(borrowRecord);
            book.setBorrowed(true);
            JOptionPane.showMessageDialog(null, "Book borrowed successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Book is not available.");
        }
    }

    public void returnBook(String bookID) {
        for (BorrowRecord borrowRecord : new ArrayList<>(borrowList)) {
            if (borrowRecord.getBook().getBookID().equals(bookID)) {
                borrowList.remove(borrowRecord);
                borrowRecord.getBook().setBorrowed(false);
                JOptionPane.showMessageDialog(null, "Book returned successfully.");
                break;
            }
        }
    }

    public void showMenu() {
        while (true) {
            String[] options = {"Borrow Book", "Return Book", "View Borrowed Books", "View Reservation Queue", "Show List of Books", "Logout"};
            int choice = JOptionPane.showOptionDialog(null, "Member Menu:", "Library Management System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    String bookID = JOptionPane.showInputDialog("Enter book ID to borrow:");
                    for (Book book : Book.getInventory()) {
                        if (book.getBookID().equals(bookID)) {
                            borrowBook(book);
                            break;
                        }
                    }
                    break;
                case 1:
                    bookID = JOptionPane.showInputDialog("Enter book ID to return:");
                    returnBook(bookID);
                    break;
                case 2:
                    showBorrowedBooks();
                    break;
                case 3:
                    showReservationQueue();
                    break;
                case 4:
                    showListOfBooks();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Logged out successfully.");
                    return; // Exit the showMenu() method and effectively end the program
            }
        }
    }

    public void showBorrowedBooks() {
        StringBuilder borrowedBooks = new StringBuilder("Borrowed Books:\n");
        for (BorrowRecord record : borrowList) {
            borrowedBooks.append(record.getBook().getTitle()).append(" (").append(record.getBook().getBookID()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, borrowedBooks.toString(), "Borrowed Books", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showReservationQueue() {
        StringBuilder reservationQueue = new StringBuilder("Reservation Queue:\n");
        for (ReservationQueue queue : reserveList) {
            reservationQueue.append(queue.getBook().getTitle()).append(" (").append(queue.getBook().getBookID()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, reservationQueue.toString(), "Reservation Queue", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showListOfBooks() {
        StringBuilder bookList = new StringBuilder("List of Books:\n");
        for (Book book : Book.getInventory()) {
            bookList.append(book.getTitle()).append(" (").append(book.getBookID()).append(")\n");
        }
        JOptionPane.showMessageDialog(null, bookList.toString(), "List of Books", JOptionPane.INFORMATION_MESSAGE);
    }

    public ArrayList<BorrowRecord> getBorrowList() {
        return borrowList;
    }

    public ArrayList<ReservationQueue> getReserveList() {
        return reserveList;
    }

    public static ArrayList<Member> getMembers() {
        return members;
    }
}

class Admin extends User {
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
        // Placeholder for managing members
        // Implement functionality as needed
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
        // Add new member to the list of members
    }
}

class BorrowRecord {
    private Member member;
    private Book book;
    private Date borrowDate;
    private Date returnDate;

    public BorrowRecord(Member member, Book book, Date borrowDate, Date returnDate) {
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}

class ReservationQueue {
    private Member member;
    private Book book;

    public ReservationQueue(Member member, Book book) {
        this.member = member;
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }
}

class Announcement {
    private String message;
    private Date date;

    public Announcement(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Initialize some books
        Book book1 = new Book("B001", "Java Programming", "John Doe", "Programming", 2020);
        Book book2 = new Book("B002", "Data Structures", "Jane Smith", "Computer Science", 2019);
        Book book3 = new Book("B003", "History of Rome", "Mark Johnson", "History", 2015);

        // Initialize an admin and a member
        Admin admin = new Admin("Admin Name", "admin@example.com", "Admin Address", "1234567890", "M001", "admin", "adminpass", new Date(), "A001");
        Member member = new Member("Member Name", "member@example.com", "Member Address", "9876543210", "M002", "member", "memberpass", new Date());

        // Sample borrow operations
        member.borrowBook(book1);
        member.borrowBook(book2);

        // Sample reservation queue
        ReservationQueue reservation1 = new ReservationQueue(member, book3);
        member.getReserveList().add(reservation1);

        // Start the application
        loginMenu();
    }

    public static void loginMenu() {
        while (true) {
            String[] options = {"Admin", "Member", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Login as:", "Library Management System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

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

    public static void adminLogin() {
        String username = JOptionPane.showInputDialog("Enter admin username:");
        String password = JOptionPane.showInputDialog("Enter admin password:");

        Admin admin = new Admin("Admin Name", "admin@example.com", "Admin Address", "1234567890", "M001", "admin", "adminpass", new Date(), "A001");

        if (admin.login(username, password)) {
            adminMenu(admin);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
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

    public static void memberLogin() {
        String password = JOptionPane.showInputDialog("Enter member password:");
        String username = JOptionPane.showInputDialog("Enter member username:");

        Member member = new Member("Member Name", "member@example.com", "Member Address", "9876543210", "M002", "member", "memberpass", new Date());

        if (member.login(username, password)) {
            memberMenu(member);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    public static void memberMenu(Member member) {
        member.showMenu();
    }
}
