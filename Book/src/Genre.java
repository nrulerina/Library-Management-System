import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Genre {
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void writeToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(name);
        writer.newLine();
        writer.close();
    }

    public static ArrayList<Genre> readFromFile(String fileName) throws IOException {
        ArrayList<Genre> genres = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine())!= null) {
            genres.add(new Genre(line));
        }
        reader.close();
        return genres;
    }

    
}
