import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class Announcement {
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
    
  @Override
    public String toString() {
        return "Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "\nMessage: " + message + "\n";
    }
}
