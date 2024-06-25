import java.util.Date;
import java.util.UUID;  
public class LibraryCard {
    private String cardID;
    private Member member;
    private Date expiryDate;
    private Date issueDate;
     private LibraryCard libraryCard;

    public LibraryCard(Member member, Date expiryDate, Date issueDate) {
        this.cardID = generateCardID(); // Generate card ID upon creation
        this.member = member;
        this.expiryDate = expiryDate;
        this.issueDate = issueDate;
    }
    private String generateCardID() {
        return "LC-" + UUID.randomUUID().toString().substring(0, 4);
    }

    public String getCardID() {
        return cardID;
    }

    public Member getMember() {
        return member;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void renewMembership() {
        long oneYearInMillis = 365L * 24 * 60 * 60 * 1000;
        this.expiryDate = new Date(System.currentTimeMillis() + oneYearInMillis);
    }

    public boolean checkValidity() {
        return new Date().before(this.expiryDate);
    }

    public void updateExpiryDate(Date newDate) {
        this.expiryDate = newDate;
    }

    public void editCard(Member member, Date expiryDate, Date issueDate) {
        this.member = member;
        this.expiryDate = expiryDate;
        this.issueDate = issueDate;
    }

  
    public String getFormattedDetails() {
        String formattedDetails = "Library Card Information:\n" +
                "Card ID: " + cardID + "\n" +
                "Member Name: " + member.getName() + "\n" +
                "Expiry Date: " + expiryDate.toString() + "\n" +
                "Issue Date: " + issueDate.toString() + "\n";
        return formattedDetails;
    }}