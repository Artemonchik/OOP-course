import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NoteBookManager {
    /**
     * Load NoteBook by the path from JSON format
     * @param path path to store
     * @return NoteBook that was loaded
     */
    public static NoteBook loadNoteBook(Path path) {
        try {
            String jsonString = new String(Files.readAllBytes(path));
            Gson gson = new Gson();
            return gson.fromJson(jsonString, NoteBook.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Meow meow meow. Cannot parse file in JSON format by this path");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot read data from the file by this path");
        }
    }

    /**
     * Stores notebook by the path in JSON format
     * @param noteBook notebook to store
     * @param path path to store this notebook
     */
    public static void storeNoteBook(NoteBook noteBook, Path path) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(String.valueOf(path))));
            Gson gson = new Gson();
            bw.write(gson.toJson(noteBook));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot write in this file");
        }
    }
}
