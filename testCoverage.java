import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.rules.TemporaryFolder;

import java.io.File;
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
    String baseWritePath= "/Users/yaseminakaydin/Desktop/exercises/Results/";
    

    @BeforeAll
    public void deleteResultsFiles(){
        String folderPath = "/Users/yaseminakaydin/Desktop/exercises/Results";

        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            deleteFiles(folder);
            System.out.println("Alle Dateien im Ordner wurden gelöscht.");
        } else {
            System.out.println("Der angegebene Ordner existiert nicht.");
        }
    }

    /**
     * Hilfsmethode, um vor allen Tests einmal den Ordner "Results" zu leeren
     * @param folder
     */
    public static void deleteFiles(File folder) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFiles(file);
                } else {
                    file.delete();
                }
            }
        }
    }
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

    /**
     * Der Test überprüft, ob unsere Einlesemethode korrekt erkennt, ob es fehlerhafte Einträge
     * in der angegeben Markdown-Datei gibt
     */
    @Test (expected = IllegalArgumentException.class)
    public void testReadDataError(){
        String[] elements= Parser.readData("/Users/yaseminakaydin/Desktop/exercises/ex3.md");
        List<List<Integer>> numbers= Parser.numbers(elements);
    }

    /**
     * Der Test überprüft, ob unsere Einlesemethode richtig funktioniert und überprüft,
     * ob die zurückgegebnen Elemente auch nicht null sind.
     */
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

    //TODO
    @Test (expected = IOException.class)
    public void testWriteDaa_IOException() {

    }

    //TODO
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

    /**
     * Test überprüft, ob unsere Methode für MBÜÜ für Aufgabe 2a aus dem Aufgabenblatt richtig einliest
     * und die richtigen Testcases zurückgibt. Wir haben das Beispiel per Hand gelöst und
     * vergleichen hier mit unserer händischen Lösung.
     */
    @Test
    public void testAufgabe2aMbuu()  {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/Aufgabe2a.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        List<List<Integer>> result = new ArrayList<>(Arrays.asList(
                Arrays.asList(0, 0, 0, 1),
                Arrays.asList(1, 0, 0, 0),
                Arrays.asList(0, 1, 0, 0),
                Arrays.asList(0, 0, 1, 1),
                Arrays.asList(1, 0, 1, 0),
                Arrays.asList(0, 1, 1, 1),
                Arrays.asList(1, 1, 1, 0)
        ));
        boolean isEqual=  compareLists(nums, result);
        assertTrue(isEqual);
    }

    /**
     * Hilfsmethode, die überprüft, ob der Inhalt zweier Listen aus Listen von Integern
     * gleich ist, obwohl diese in einer anderen Reihenfolge vorkommen.
     * @param list1
     * @param list2
     * @return true, falls beide Listen die gleichen Einträge haben
     */
    public static boolean compareLists(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (List<Integer> innerList1 : list1) {
            boolean foundMatch = false;

            for (List<Integer> innerList2 : list2) {
                if (innerList1.equals(innerList2)) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                return false;
            }
        }

        return true;
    }

    /**
     * Test überprüft, ob unsere Methode für MBÜÜ für Aufgabe 2b aus dem Aufgabenblatt richtig einliest
     * und die richtigen Testcases zurückgibt. Wir haben das Beispiel per Hand gelöst und
     * vergleichen hier mit unserer händischen Lösung.
     */
    @Test
    public void testAufgabe2bMbuu()  {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/Aufgabe2b.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        List<List<Integer>> result = new ArrayList<>(Arrays.asList(
                Arrays.asList(0, 0, 1, 0),
                Arrays.asList(0, 1, 0, 0),
                Arrays.asList(1, 0, 0, 0),
                Arrays.asList(1, 0, 1, 1),
                Arrays.asList(1, 1, 0, 1),
                Arrays.asList(1, 1, 1, 0)
        ));
        boolean isEqual= compareLists(nums, result);
        assertTrue(isEqual);

    }

    //TODO
    /**
     * Test überprüft, ob unsere Methode für McDC für Aufgabe 2b aus dem Aufgabenblatt richtig einliest
     * und die richtigen Testcases zurückgibt. Wir haben das Beispiel per Hand gelöst und
     * vergleichen hier mit unserer händischen Lösung. Da unsere MCDC-Methode immer das erste Optimum findet, haben
     * wir gleichzeitig überprüft, ob die Lösung 4 Cases beinhaltet.
     */
    @Test
    public void testAufgabe2bMcDc() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/Aufgabe2b.md";
        String[] elements= Parser.readData(datName);
        Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements));
        Parser.writeData(baseWritePath+"mcdcAufgabe2b.md", Parser.header(elements),
                Parser.findConditionsForMcDC(
                        Parser.findTestCasesForMcDc(Parser.generateTestMCDC(
                                3, Parser.numbers(
                                        elements))), Parser.numbers(elements)) );
        String[] elemnts= Parser.readData("/Users/yaseminakaydin/Desktop/exercises/Results/mcdcAufgabe2b.md");
        List<List<Integer>> nums= Parser.numbers(elemnts);
        System.out.println("!!!!!" + nums +"!!!!!!");
        List<List<Integer>> result = new ArrayList<>(Arrays.asList(
                Arrays.asList(0, 0, 1, 0),
                Arrays.asList(1, 0, 0, 0),
                Arrays.asList(1, 0, 1, 1),
                Arrays.asList(1, 1, 1, 0)
        ));

        boolean isEqual= compareLists(nums, result);
        assertTrue(isEqual);


    }

    @Test
    public void testExercise1Mbuu(){
        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise1.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData(baseWritePath+"mbuuExercise1.md", Parser.header(elements), nums);

    }

    @Test
    public void testExercise2Mbuu() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise2.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData(baseWritePath+"mbuuExercise2.md", Parser.header(elements), nums);

    }

    @Test
    public void testExercise1Mcdc(){
        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise1.md";
        String[] elements= Parser.readData(datName);
        Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements));
        Parser.writeData(baseWritePath+"mcdcExercise1.md", Parser.header(elements),
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
        Parser.writeData(baseWritePath+"mcdcExercise2.md", Parser.header(elements),
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
        Parser.writeData(baseWritePath+"mcdcTestResultOfExample.md", Parser.header(elements), Parser.findConditionsForMcDC(Parser.findTestCasesForMcDc(Parser.generateTestMCDC(3, Parser.numbers(elements))), Parser.numbers(elements)) );

    }

    @Test
    public void testGenerateMBUU() {
        String datName = "/Users/yaseminakaydin/Desktop/exercises/example.md";
        String[] elements= Parser.readData(datName);
        List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
        Parser.writeData(baseWritePath+"mbuuTestResultOfExample.md", Parser.header(elements), nums);
    }

    @Test
    public void testListMCDC(){
        List<String> pathNames = new ArrayList<>();
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex0.md");
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex2.md");
        Parser.generateList(pathNames, true);
        String filePathex0 = "/Users/yaseminakaydin/Desktop/exercises/Results/ex0McDcTest.md";
        String filePathex2 = "/Users/yaseminakaydin/Desktop/exercises/Results/ex2McDcTest.md";

        File file0 = new File(filePathex0);
        File file2 = new File(filePathex2);
        assertTrue(file0.exists());
        assertTrue(file2.exists());


    }

    @Test
    public void testListMBUU(){
        List<String> pathNames = new ArrayList<>();
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex0.md");
        pathNames.add("/Users/yaseminakaydin/Desktop/exercises/ex2.md");
        Parser.generateList(pathNames, false);

        String filePathex0 = "/Users/yaseminakaydin/Desktop/exercises/Results/ex0MBUUTest.md";
        String filePathex2 = "/Users/yaseminakaydin/Desktop/exercises/Results/ex2MBUUTest.md";

        File file0 = new File(filePathex0);
        File file2 = new File(filePathex2);
        assertTrue(file0.exists());
        assertTrue(file2.exists());

    }



}
