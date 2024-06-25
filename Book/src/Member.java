import java.util.Date;

public class Member extends User {
    private String memberID;
    private Date registrationDate;

    public Member(String name, String email, String address, String phoneNumber, String memberID,
                  String username, String password, Date registrationDate,String userID) {
                    super(name, email, address, phoneNumber,memberID, username, password, registrationDate);

        this.memberID = memberID;
        this.registrationDate = registrationDate;
    }

    

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean login(String username, String password) {
        // Custom implementation for member login if needed
        return super.login(username, password);
    }

    public void borrowBook(Book book) {
        // Implement borrow book functionality
        // Example: update member's borrowing history or decrement available copies
        System.out.println(getName() + " borrowed book: " + book.getTitle());
    }

    public void returnBook(Book book) {
        // Implement return book functionality
        // Example: update member's borrowing history or increment available copies
        System.out.println(getName() + " returned book: " + book.getTitle());
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", memberID='" + memberID + '\'' +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
