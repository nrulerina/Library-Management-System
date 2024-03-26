import java.util.ArrayList;

public class Database {

    ArrayList<User> users = new ArrayList<User>();
    ArrayList<String> usernames = new ArrayList<String>();

    public void AddUser(User s) {
        // Add user to database
        users.add(s);
        usernames.add(s.getName());
    }

    public int login(String phoneNumber, String email) {
        int n =-1;// Check if user exists in database
        for (User s : users) {
            if (s.getPhoneNumber().equals(phoneNumber) && s.getEmail().equals(email)) {
                n= users.indexOf(s);
                break; // User found, login successful
            }
        }
        return n; // User not found, login failed
    }

    public User getUser(int n) {
        // Get user from database
        return users.get(n);
    }
}
