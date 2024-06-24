import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Publisher {
    private String name;
    private String address;
    private String email;

    public Publisher(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void publishBook(Book book) {
        // Implement publish book logic
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void writeToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(name + "," + address + "," + email);
        writer.newLine();
        writer.close();
    }

    public static ArrayList<Publisher> readFromFile(String fileName) throws IOException {
        ArrayList<Publisher> publishers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine())!= null) {
            String[] values = line.split(",");
            String name = values[0];
            String address = values[1];
            String email = values[2];
            publishers.add(new Publisher(name, address, email));
        }
        reader.close();
        return publishers;
    }
}