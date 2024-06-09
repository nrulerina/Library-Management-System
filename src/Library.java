import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private String address;
    private List<Book> books;
    private List<User> users;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void issueBook(Book book, User user) {
        // Implement issue book logic
    }

    public void returnBook(Book book, User user) {
        // Implement return book logic
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", books=" + books +
                ", users=" + users +
                '}';
    }
}
