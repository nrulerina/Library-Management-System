import java.util.Date;

public class Member extends User {
    private String memberID;
    private Date registrationDate;

    private LibraryCard libraryCard;

    public Member(String name, String email, String address, String phoneNumber,
                  String username, String password, Date registrationDate, String memberID) {
        super(name, email, address, phoneNumber, username, password, registrationDate);
        this.memberID = memberID;
        this.registrationDate = registrationDate;
        this.libraryCard = new LibraryCard(this, new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000), new Date());
    }

    public LibraryCard getLibraryCard() {
        return libraryCard;
    }
    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getAddress(){
        return address;
    }

    public String getPhoneNumber(){
        return phoneNumber;
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
        return super.login(username, password);
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
