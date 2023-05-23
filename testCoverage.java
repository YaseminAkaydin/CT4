import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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
            //anzahl der Elemente gleich ?
            // ob elements nicht null ist bei allen ex.md Dateien in einer Schleife
             //Fehlerfall, wenn da andere Zahlen als 0 und 1 vorkommen
        //Test für die IO Exception

    }

    @Test
    public void writeData(){
        //Fehler Message abtesten
    }



    @Test
    public void testGenerateMcDC(){
        //einfach alles notwenidge aufrufen und am Ende mit der Lösung vergleichen (2-3x)
    }

    @Test
    public void testGenerateMBUU(){
        //einfach alles notwenidge aufrufen und am Ende mit der Lösung vergleichen (2-3x)
    }
}
