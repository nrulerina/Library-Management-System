import java.util.Date;
import java.util.UUID;

public class LibraryCard {
    private String cardID;
    private Member member;
    private Date expiryDate;
    private Date issueDate;

    
    public LibraryCard(String cardID, Member member, Date expiryDate, Date issueDate) {
        this.cardID = cardID;
        this.member = member;
        this.expiryDate = expiryDate;
        this.issueDate = issueDate;
    }

    
    public String generateCardID() {
        return UUID.randomUUID().toString();
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