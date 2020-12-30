import org.apache.commons.cli.*;

import java.io.File;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleApplication {
    private static final String noteBookPath = "notebook.json";

    public static void main(String[] args) {
        Options options = new Options();

        Option add = new Option("a", "add", true, "Notes you want to add");
        add.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(add);

        Option rm = new Option("r", "rm", true, "Notes you want to remove");
        rm.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(rm);

        Option show = new Option("s", "show", true, "Notes you want to get");
        show.setOptionalArg(true);
        show.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(show);

        DefaultParser parser = new DefaultParser();
        CommandLine cl = null;
        try {
            cl = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        Option[] opts = cl.getOptions();
        NoteBook noteBook = null;
        if (new File(noteBookPath).exists()) {
            noteBook = NoteBookManager.loadNoteBook(Path.of(noteBookPath));
        } else {
            noteBook = new NoteBook();
        }
        for (Option opt : opts) {
            switch (opt.getLongOpt()) {
                case "add":
                    for (String note : cl.getOptionValues("add")) {
                        noteBook.addNote(note);
                    }
                    break;
                case "rm":
                    for (String note : cl.getOptionValues("rm")) {
                        noteBook.removeNote(note);
                    }
                    break;
                case "show":
                    String[] showOpts = cl.getOptionValues("show");
                    if (showOpts == null || showOpts.length == 0) {
                        System.out.println(prettyStr(noteBook.getSortedNotes()));
                    } else if (showOpts.length >= 2) {
                        try {
                            SimpleDateFormat dateF = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.ENGLISH);
                            System.out.println(prettyStr(noteBook.getSortedNotes(dateF.parse(showOpts[0]),
                                    dateF.parse(showOpts[1]),
                                    Arrays.copyOfRange(showOpts, 2, showOpts.length))));
                        } catch (java.text.ParseException e) {
                            System.err.println("Incorrect date format: use dd.MM.yyyy hh:mm::ss");
                            return;
                        }
                    } else {
                        System.err.println("Please use\n--show\n--show fromdate todate keywords");
                    }
                    break;
            }
            NoteBookManager.storeNoteBook(noteBook, Path.of(noteBookPath));
        }
    }

    private static String prettyStr(ArrayList<NoteBook.Note> arr) {
        return String.join("\n", arr
                .stream()
                .map((note -> note.getMessage() + " at " + note.getCreationDate()))
                .toArray(String[]::new));
    }
}