import java.util.*;

public class Library {
    private String name;
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<Admin> admins;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }
}
