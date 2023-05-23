import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class testCoverage {

    Parser parser= new Parser();



    public void getExamplesMD(){
        String basePath = "/Users/yaseminakaydin/Desktop/exercises/ex";
        int startNumber = 0;
        int endNumber = 7;

        for (int i = startNumber; i <= endNumber; i++) {
            String filePath = basePath + i + ".md";
            String[] elements = Parser.readData(filePath);
            System.out.println(elements);
        }
    }

    @Test
    public void testReadDataFromFile() throws IOException {


    }



    @Test
    public void testGenerateMcDC(){

    }

    @Test
    public void testGenerateMBUU(){

    }
}
