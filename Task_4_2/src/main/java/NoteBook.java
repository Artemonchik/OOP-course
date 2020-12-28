import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class NoteBook {
    private final ArrayList<Note> notes = new ArrayList<>(10);

    /**
     * Create note with current message and add to notes
     * @param message message for note
     */
    public void addNote(String message) {
        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }
        notes.add(new Note(message));
    }

    /**
     * Remove note with specified message
     * @param message to store
     */
    public void removeNote(String message) {
        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getMessage().equals(message)) {
                notes.remove(i);
            }
        }
    }

    /**
     * @return sorted by date array of notes
     */
    public ArrayList<Note> getSortedNotes() {
        ArrayList<Note> result = new ArrayList<>();
        for (Note note : notes) {
            result.add(new Note(note.getMessage(), note.getCreationDate()));
        }
        result.sort(Comparator.comparing(Note::getCreationDate));
        return result;
    }

    /**
     * Find all notes from the {@code from} to the {@code till} with specified {@code keywords}
     * @return sorted array of notes
     */
    public ArrayList<Note> getSortedNotes(Date from, Date till, String[] keywords) {
        if (from == null || till == null) {
            throw new IllegalArgumentException("till and from cannot be null");
        }
        if (keywords == null || keywords.length == 0) {
            keywords = new String[]{""};
        }
        ArrayList<Note> result = new ArrayList<>();
        for (Note note : notes) {
            if (!(note.getCreationDate().compareTo(till) < 0 && note.creationDate.compareTo(from) >= 0)) {
                continue;
            }
            for (String keyWord : keywords) {
                if (note.getMessage().contains(keyWord)) {
                    result.add(new Note(note.getMessage(), note.getCreationDate()));
                }
            }
        }
        result.sort(Comparator.comparing(Note::getCreationDate));
        return result;
    }

    public static class Note {
        private final Date creationDate;
        private final String message;

        public Note(String message) {
            this.message = message;
            this.creationDate = new Date();
        }

        public Date getCreationDate() {
            return creationDate;
        }

        public String getMessage() {
            return message;
        }

        private Note(String message, Date creationDate) {
            this.creationDate = creationDate;
            this.message = message;
        }
    }
}
