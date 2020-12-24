import org.junit.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class NoteBookManagerTest {
    @Test
    public void storeAndReadTest() throws InterruptedException {

        NoteBook notebook = new NoteBook();
        notebook.addNote("Mew anime");
        Thread.sleep(10);
        notebook.addNote("Gav");
        Thread.sleep(10);
        notebook.addNote("Who anime");
        Thread.sleep(10);
        notebook.addNote("I love anime. Anime is my life");
        Thread.sleep(10);
        notebook.addNote("Mew");

        NoteBookManager.storeNoteBook(notebook, Path.of("myNoteBook.json"));
        NoteBook loadedNotebook = NoteBookManager.loadNoteBook(Path.of("myNoteBook.json"));
        assertArrayEquals(notebook.getSortedNotes().stream().map(NoteBook.Note::getMessage).toArray(String[]::new),
                loadedNotebook.getSortedNotes().stream().map(NoteBook.Note::getMessage).toArray(String[]::new));
        assertArrayEquals(notebook.getSortedNotes().stream().map(NoteBook.Note::getCreationDate).map(Date::toString).toArray(String[]::new),
                loadedNotebook.getSortedNotes().stream().map(NoteBook.Note::getCreationDate).map(Date::toString).toArray(String[]::new));
    }
}
