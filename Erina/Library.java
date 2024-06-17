import java.util.ArrayList;

public class Library {
    private String name;
    private String address;
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<Genre> genres;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        if (book.getGenre() != null) {
            book.getGenre().addBook(book);
        }
    }

    public void removeBook(Book book) {
        books.remove(book);
        if (book.getGenre() != null) {
            book.getGenre().removeBook(book);
        }
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public ArrayList<Book> searchBooks(String title) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public Member findMemberByName(String name) {
        for (User user : users) {
            if (user instanceof Member && user.getName().equalsIgnoreCase(name)) {
                return (Member) user;
            }
        }
        return null;
    }

    public void addGenre(Genre genre) {
        if (!genres.contains(genre)) {
            genres.add(genre);
        }
    }

    public void removeGenre(Genre genre) {
        if (genres.contains(genre)) {
            genres.remove(genre);
            for (Book book : genre.getBooks()) {
                book.setGenre(null);
            }
        }
    }

    public Genre findGenreByName(String name) {
        for (Genre genre : genres) {
            if (genre.getName().equalsIgnoreCase(name)) {
                return genre;
            }
        }
        return null;
    }
}
