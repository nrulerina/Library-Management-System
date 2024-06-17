public class Member extends User {
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