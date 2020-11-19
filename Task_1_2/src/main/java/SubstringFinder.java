import java.io.*;
import java.util.ArrayList;

import static java.lang.Integer.max;

public class SubstringFinder {
    /**
     * Finds the indices of the occurrence of a substring {@param searchParam} in {@param file}
     *
     * @param file        InputStreamReader where we want to search occurrence
     * @param searchParam char[] we want to find
     * @return array of indices of the occurrences. Count from 0
     * @throws IOException if some problems with a reading of the file
     */
    public static ArrayList<Long> findSubstring(InputStreamReader file, char[] searchParam) throws IOException {
        if (file == null) {
            throw new NullPointerException("File is null");
        }
        if (searchParam == null) {
            throw new NullPointerException("searchParam is null");
        }

        ArrayList<Long> indexes = new ArrayList<>();
        char[] buf = new char[searchParam.length * 2]; // create buffer with size of len(searchParam) * 2
        int paramLen = searchParam.length; // lenght of the parameter

        long posInFile = 0; // current position in file to return indices
        int readLen = file.read(buf, paramLen, paramLen);
        if (readLen < paramLen) {
            return indexes;
        }
        boolean lastScan = false;
        do {
            System.arraycopy(buf, paramLen, buf, 0, paramLen);
            if (readLen == paramLen) {

                readLen = file.read(buf, paramLen, paramLen);
                if(readLen < paramLen){
                    readLen = max(readLen, 0);
                    readLen++;
                    lastScan = true;
                }
            }

            for (int i = 0; i < readLen; i++) {
                boolean isSubStr = false;
                for (int j = 0; j < paramLen; j++) {
                    if (buf[i + j] != searchParam[j]) {
                        break;
                    }
                    if (j == paramLen - 1) {
                        isSubStr = true;
                        break;
                    }
                }
                if (isSubStr) {
                    indexes.add((long) posInFile);
                }
                posInFile++;
            }
        } while (!lastScan);

        return indexes;
    }

}