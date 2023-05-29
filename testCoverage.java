import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.TemporaryFolder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class testCoverage {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    String basePathError= "/Users/yaseminakaydin/Desktop/hallo/ex1.md";
    String basepath= "/Users/yaseminakaydin/Desktop/exercises/exercise1.md";
    

    /**
     * Methode testet, ob die Headerlänge genau 9 entspricht (Bedingungen plus Markdown-Trennzeichen)
     * und ob die Zeilenanzahl 8 entspricht, wenn wir 3 Bedingungen haben
     * @throws IOException
     */
    @Test
    public void testReadDataFromFileCountElements() throws IOException {
        String[] elements= Parser.readData(basepath);
        List<String> header= Parser.header(elements);
        List<List<Integer>> numbers= Parser.numbers(elements);

        Assertions.assertEquals(8, numbers.size());
        Assertions.assertEquals(9, header.size());

            //anzahl der Elemente gleich ?
            // ob elements nicht null ist bei allen ex.md Dateien in einer Schleife
             //Fehlerfall, wenn da andere Zahlen als 0 und 1 vorkommen
        //Test für die IO Exception

    }

    @Test
    public void testReadDataError(){
        String[] elements= Parser.readData("/Users/yaseminakaydin/Desktop/exercises/ex3.md");
        List<List<Integer>> numbers= Parser.numbers(elements);
        // assertThrows Exception

    }

    @Test
    public void testReadDataNotNull(){
        String basePath = "/Users/yaseminakaydin/Desktop/exercises/ex";
        int startNumber = 0;
        int endNumber = 7;

        for (int i = startNumber; i <= endNumber; i++) {
            String filePath = basePath + i + ".md";
            String[] elements = Parser.readData(filePath);
            Assertions.assertNotNull(elements);
        }

    }

    @Test (expected = IOException.class)
    public void testWriteDaa_IOException() {

    }

    @Test (expected = IllegalArgumentException.class)
    public void testReadErroorDataFormat(){
        String basePath = "/Users/yaseminakaydin/Desktop/exercises/ex3.md";
        String[] elements = Parser.readData(basePath);
        List<List<Integer>> numbers= Parser.numbers(elements);
    }




    @Test
    public void testWriteData_IOException(){
        String path = "example.md";
        List<String> header = Arrays.asList("Header1", "Header2", "Header3");
        List<List<Integer>> numbers = new ArrayList<>();
        numbers.add(Arrays.asList(1, 2, 3));
        numbers.add(Arrays.asList(4, 5, 6));

        Path nonWritablePath = Path.of("/nonexistent/directory", path);
        Parser.writeData(String.valueOf(nonWritablePath), header, numbers);
    }

    @Test
    public void testAufgabe2aMbuu()  {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/AUfgabe2a.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mbuuTestAufgabe2a.md", Parser.header(elements), nums);

    }

    @Test
    public void testAufgabe2bMbuu()  {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/Aufgabe2b.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mbuuTestAufgabe2b.md", Parser.header(elements), nums);

    }

    @Test
    public void testAufgabe2bMcDc() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/Aufgabe2b.md";
        String[] elements= Parser.readData(datName);
        Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mcdcAufgabe2b.md", Parser.header(elements),
                Parser.findConditionsForMcDC(
                        Parser.findTestCasesForMcDc(Parser.generateTestMCDC(
                                3, Parser.numbers(
                                        elements))), Parser.numbers(elements)) );


    }

    @Test
    public void testExercise1Mbuu(){
        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise1.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mbuuExercise1.md", Parser.header(elements), nums);

    }

    @Test
    public void testExercise2Mbuu() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise2.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mbuuExercise2.md", Parser.header(elements), nums);

    }

    @Test
    public void testExercise1Mcdc(){
        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise1.md";
        String[] elements= Parser.readData(datName);
        Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mcdcExercise1.md", Parser.header(elements),
                Parser.findConditionsForMcDC(
                        Parser.findTestCasesForMcDc(Parser.generateTestMCDC(
                                3, Parser.numbers(
                                        elements))), Parser.numbers(elements)) );

    }

    @Test
    public void testExercise2Mcdc() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise2.md";
        String[] elements= Parser.readData(datName);
        Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mcdcExercise2.md", Parser.header(elements),
                Parser.findConditionsForMcDC(
                        Parser.findTestCasesForMcDc(Parser.generateTestMCDC(
                                3, Parser.numbers(
                                        elements))), Parser.numbers(elements)) );

    }





    @Test
    public void testGenerateMcDC() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/example.md";
        String[] elements= Parser.readData(datName);
        Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mcdcTestResult.md", Parser.header(elements), Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements)) );

    }

    @Test
    public void testGenerateMBUU() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/example.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData("/Users/yaseminakaydin/Desktop/exercises/mbuuTestResult.md", Parser.header(elements), nums);
    }

    @Test
    public void testListMCDC(){
        List<String> pathNames = new ArrayList<>();
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex0.md");
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex2.md");
        Parser.generateList(pathNames, true);
    }

    @Test
    public void testListMBUU(){
        List<String> pathNames = new ArrayList<>();
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex0.md");
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex2.md");
        Parser.generateList(pathNames, false);

    }



}
