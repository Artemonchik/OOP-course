import java.awt.*;
import java.io.*;
import java.nio.Buffer;

public class Main {
    public static void main(String[] args) {
        String fileName = args[0];
        String searchParam = args[1];
        try {
            InputStreamReader file = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            Buffer buf = new CircularFifoBuffer(4);
            for (int value = file.read(), pos = 0; value != -1; value = file.read(), pos++) {
                char ch = (char) (value);
                int i = 0;
                while (i < searchParam.length() && searchParam.charAt(i) == ch) {
                    i++;
                    ch = (char)file.read();
                }
                if (i == searchParam.length()) {
                    System.out.println(pos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
