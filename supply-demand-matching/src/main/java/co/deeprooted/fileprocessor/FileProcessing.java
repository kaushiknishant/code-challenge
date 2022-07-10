package co.deeprooted.fileprocessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileProcessing {
    public Scanner fileReader(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        return sc;
    }
    public void fileWriter(String fileName, List<String> output) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        for(String str : output) {
            fileWriter.write(str + "\n");
        }
        fileWriter.close();
    }
}
