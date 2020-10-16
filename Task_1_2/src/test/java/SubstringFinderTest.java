import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SubstringFinderTest {
    @Test
    public void simpleTest1() throws IOException {
        FileWriter fw = new FileWriter("input.txt", StandardCharsets.UTF_8);
        String str = "nnnnn n n nn nnnn nnnn nnn";

        fw.write(str);
        fw.close();

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("input.txt"), StandardCharsets.UTF_8);
        ArrayList<Integer> result = SubstringFinder.findSubstring(inputStreamReader, "nnn".toCharArray());
        Integer[] expectedResult = new Integer[]{0, 1, 2, 13, 14, 18, 19, 23};
        assertArrayEquals(expectedResult, result.toArray());
        inputStreamReader.close();

        File file = new File("input.txt");
        boolean wasDeleted = file.delete();
    }

    @Test
    public void simpleTest2() throws IOException {
        FileWriter fw = new FileWriter("input.txt", StandardCharsets.UTF_8);
        String str = "ararara arara araara";

        fw.write(str);
        fw.close();

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("input.txt"), StandardCharsets.UTF_8);
        ArrayList<Integer> result = SubstringFinder.findSubstring(inputStreamReader, "ara".toCharArray());
        Integer[] expectedResult = new Integer[]{0, 2, 4, 8, 10, 14, 17};
        assertArrayEquals(expectedResult, result.toArray());
        inputStreamReader.close();

        File file = new File("input.txt");
        boolean wasDeleted = file.delete();
    }

    @Test
    public void noMatchesTest() throws IOException {
        FileWriter fw = new FileWriter("input.txt", StandardCharsets.UTF_8);
        String str = "ararara arara araara";

        fw.write(str);
        fw.close();

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("input.txt"), StandardCharsets.UTF_8);
        ArrayList<Integer> result = SubstringFinder.findSubstring(inputStreamReader, "ssdaara".toCharArray());
        Integer[] expectedResult = new Integer[]{};
        assertArrayEquals(expectedResult, result.toArray());
        inputStreamReader.close();

        File file = new File("input.txt");
        boolean wasDeleted = file.delete();
    }

    @Test
    public void largeTest20GBFile() throws IOException { //on my laptop it takes 2m45s
        FileWriter fw = new FileWriter("input.txt", StandardCharsets.UTF_8);
        String str = "The mailbox has been filled with one resounding question: When will Billy " +
                "Herrington go up against Mark Wolff? Well your prayers have been answered! The result is one".repeat(30) +
                " of our most visually stunning productions yet. First wiry young newcomer Nick Steel eggs Billy on. \n".repeat(30);
        Integer[] expectedResult = new Integer[(int) (20L * 1024 * 1024 * 1024 / str.length())];
        for (int i = 0; i < (int) (20L * 1024 * 1024 * 1024 / str.length()); i++) {
            if (i % 10000000 == 0)
                fw.flush();
            fw.write(str);
            expectedResult[i] = ((int) (i * str.length()));
        }
        fw.close();

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("input.txt"), StandardCharsets.UTF_8);
        ArrayList<Integer> result = SubstringFinder.findSubstring(inputStreamReader, "The mailbox has been filled with".toCharArray());


        assertArrayEquals(expectedResult, result.toArray());
        inputStreamReader.close();

        File file = new File("input.txt");
        boolean wasDeleted = file.delete();
    }
}
