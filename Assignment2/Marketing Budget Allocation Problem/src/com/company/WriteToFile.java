package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class WriteToFile {

    public void clear() throws IOException {
        FileWriter file = new FileWriter("outputs.txt");
        file.write("");
        file.close();
    }
    public void write(String output, int solutionNumber) throws IOException {
        FileWriter file = new FileWriter("outputs.txt", true);
        file.write("Iterate number : " + solutionNumber + "\n");
        file.write(output);
        file.close();
    }
}
