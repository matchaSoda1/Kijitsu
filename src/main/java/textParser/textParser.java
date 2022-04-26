package textParser;

import domain.Card;
import domain.Picture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class textParser {


    public List<String> readFile(String fileName) throws IOException {
        //        String fileName = "C:\\Users\\junen\\Desktop\\lyrics.txt";
//        try (Stream<String> stream = Files.lines(Paths.get(fileName))) { //fast!
//            stream.forEach(s -> lines.add(s));
//        }
//        return lines;

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



    public void addTranslation(String fileName) throws IOException {
        //read translation file
        List<String> translation = readFile(fileName);

        //create cards
    }
}
