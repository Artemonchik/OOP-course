
import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SubstringFinder {
    /**
     * Finds the indices of the occurrence of a substring {@param searchParam} in {@param file}
     * @param file InputStreamReader where we want to search occurrence
     * @param searchParam char[] we want to find
     * @return array of indices of the occurrences. Count from 0
     * @throws IOException if some problems with a reading of the file
     */
    public static ArrayList<Integer> findSubstring(InputStreamReader file, char[] searchParam) throws IOException {
        ArrayList<Integer> indexes = new ArrayList<>();
        char[] buf = new char[searchParam.length * 2];
        int paramLen = searchParam.length;
        int initialPosInFile = -paramLen;
        while (true) {
            boolean exitFlag = false;
            int result = file.read(buf, paramLen, paramLen);
            if (result != paramLen) {
                exitFlag = true;
                if(result == -1){
                    result = 0;
                }
                buf[paramLen + result] = 0;
            }
            m:
            for (int i = 0; i < paramLen; i++) {
                for (int j = 0; j < paramLen; j++) {
                    if (searchParam[j] != buf[i + j]) {
                        continue m;
                    }
                }
                indexes.add(initialPosInFile + i);
            }
            if (exitFlag)
                break;
            System.arraycopy(buf, paramLen, buf, 0, paramLen);
            initialPosInFile += paramLen;
        }
        return indexes;
    }
}