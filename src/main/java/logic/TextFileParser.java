package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFileParser {

    public List<String> readFile(String fileName) throws IOException {

        List<String> lines = new ArrayList<>();

        File file = new File(fileName);
        Scanner reader = new Scanner(file);

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            lines.add(line);
        }

        reader.close();
        return lines;
    }

}
