import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class NoteBookTest {
    NoteBook notebook;

    @Before
    public void initNoteBook() throws InterruptedException {
        notebook = new NoteBook();
        notebook.addNote("Mew anime");
        Thread.sleep(10);
        notebook.addNote("Gav");
        Thread.sleep(10);
        notebook.addNote("Who anime");
        Thread.sleep(10);
        notebook.addNote("I love anime. Anime is my life");
        Thread.sleep(10);
        notebook.addNote("Mew");

    }

    @Test
    public void getNotesTest() {
        String[] res = notebook.getSortedNotes().stream().map(NoteBook.Note::getMessage).toArray(String[]::new);
        String[] expectedRes = {"Mew anime", "Gav", "Who anime", "I love anime. Anime is my life", "Mew"};
        assertArrayEquals(expectedRes, res);
    }

    @Test
    public void getNotesTestWithDateRangeAndKeyWords() {
        ArrayList<NoteBook.Note> arr = notebook.getSortedNotes();
        Date from = arr.get(2).getCreationDate();
        ArrayList<NoteBook.Note> res = notebook.getSortedNotes(from, arr.get(4).getCreationDate(), new String[]{"anime", "Gav", "Mew"});
        assertEquals("", "Who anime", res.get(0).getMessage());
        assertEquals("", "I love anime. Anime is my life", res.get(1).getMessage());
        assertEquals("", 2, res.size());
    }

    @Test
    public void rmTest() {
        notebook.removeNote("Gav");
        notebook.removeNote("AAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRR");
        String[] res = notebook.getSortedNotes().stream().map(NoteBook.Note::getMessage).toArray(String[]::new);
        String[] expectedRes = {"Mew anime", "Who anime", "I love anime. Anime is my life", "Mew"};
        assertArrayEquals(expectedRes, res);
    }

}
