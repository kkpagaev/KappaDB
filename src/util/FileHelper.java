package util;

import java.io.*;

public class FileHelper {
    public static String getStringFromFile(String fileName) {
        java.io.File fileToRead = new java.io.File(fileName);
        StringBuilder str = new StringBuilder();
        try(FileReader fileStream = new FileReader( fileToRead );
            BufferedReader bufferedReader = new BufferedReader( fileStream ) ) {

            String line;

            while( (line = bufferedReader.readLine()) != null ) {
                str.append(line);
            }

        } catch ( IOException ex ) {
            //exception Handling
        }
        return str.toString();
    }

    public static void setFileContent(String oldFileName, String content) {
        try {
            PrintWriter writer = new PrintWriter(oldFileName);
            writer.println(content);
            writer.close();
        } catch (Exception ignored) {

        }

    }
}
