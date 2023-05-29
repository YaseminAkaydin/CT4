import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Parser {

    /**
     * Die Methode schreibt das Ergebnis erneut zurück in eine Markdowndatei, aber so, dass erneut
     * eine Wahrheitstabelle entsteht
     */
    public static void writeData(String path, List<String> header, List<List<Integer>> numbers){

        try {
            // Pfad zur Markdown-Datei angeben
            Path filePath = Path.of(path);
            StringBuilder st= new StringBuilder();
            StringBuilder stNUmbers= new StringBuilder();
            ListIterator listIterator= header.listIterator();
            while (listIterator.hasNext()){
                st.append(listIterator.next());
            }


            Files.writeString(filePath,st.toString() + "\n",StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.writeString(filePath,"| --- | --- | --- | --- |\n", StandardOpenOption.APPEND);

            for( List<Integer> innerList : numbers){
                Files.writeString(filePath, "|\n", StandardOpenOption.APPEND);
                for(int n: innerList){
                    Files.writeString(filePath, "|" + n , StandardOpenOption.APPEND);
                }
            }
            Files.writeString(filePath, "|", StandardOpenOption.APPEND);



            System.out.println("Der Inhalt wurde erfolgreich in die Markdown-Datei geschrieben.");
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben in die Markdown-Datei: " + e.getMessage());
        }
    }





/**
 * Methode liest eine Wahrheitstabelle aus einer Markdown-Datei aus
 * @param datName
 */
    public static String[] readData(String datName) {
        String text= new String();
        String[] elements= new String[100];

        try {
            String s = Files.readString(Path.of(datName));
            s.split("\\| "+ "\r\n" + " \\|");
            text = s.replace("[", "").replace("]", "").replace(",", "");//.replace("---", "");
            elements = text.split("\\s+");


                } catch (IOException e) {
            e.printStackTrace();

        }
        System.out.println("---"+elements);
        System.out.println(Arrays.stream(elements).toList());
        return elements;

    }

    public static List<List<Integer>> numbers(String[] elements){
        List<Integer> numbers = new ArrayList<>();
        for (String element : elements) {
            if (element.matches("\\d+")) {
                if(Integer.parseInt(element)==0 || Integer.parseInt(element)==1){
                    numbers.add(Integer.parseInt(element));
                }else {
                    throw new IllegalArgumentException("Werte in der MarkdownFile sind nicht korrekt!");
                }
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        int index = 0;
        while (index + 4 <= numbers.size()) {
            List<Integer> sublist = numbers.subList(index, index + 4);
            result.add(sublist);
            index += 4;
        }
        return result;
    }



    public static List<String> header(String[] elements){
        String[] header= Arrays.copyOfRange(elements, 0, 9);
        List<String> headerLIst= Arrays.asList(header);
        return headerLIst;
    }
    public static Map<List<Integer>, Integer> convertListToMap(List<List<Integer>> numbers){
        Map<List<Integer>, Integer> map = new HashMap<>();

        for (List<Integer> innerList : numbers) {
            List<Integer> key = innerList.subList(0, 3);
            Integer value = innerList.get(3);
            map.put(key, value);
        }
        return map;
    }


    public static Map<List<Integer>, Integer> generateMBUU(List<List<Integer>> numbers){
        Map<List<Integer>, Integer> numberMap=convertListToMap(numbers) ;
        Map<List<Integer>, Integer> cases= new HashMap();

        for (List<Integer> key: numberMap.keySet()) {
            int result=numberMap.get(key);
            int result2=0;

            for (int i = 0; i < key.size(); i++) {
                List<Integer> tmp = new ArrayList<>(key);

                if(tmp.get(i)==0){
                    tmp.set(i, 1);
            }else {
                    tmp.set(i, 0);
                }
                for (List<Integer> key2: numberMap.keySet()){
                    if (key2.equals(tmp)){
                        if(numberMap.get(key2)!=null){
                            result2= numberMap.get(key2);
                        }
                        if(!(result==result2)){
                            if (!cases.containsKey(key))
                                cases.put(key, result);

                        }else {

                            break;
                        }
                    }
                }
        }
        }

        return cases;

    }

  


    public static Map<List<Integer>, List<String>> generateTestMCDC(int numberCond, List<List<Integer>> numbers) {
        Map<List<Integer>, Integer> numberMap = convertListToMap(numbers);
        int length = (int) numberMap.keySet().stream().count();
        Map<List<Integer>, List<String>> cases = new HashMap();


        String condition = "";
        int limit= (int) (Math.pow(2, numberCond)/2);

        for (int i = 0; i < numberCond; i++) {
            Map<List<Integer>, Integer> tmpNumbers = new HashMap(numberMap);


            char letter = (char) ('A' + i);
            condition = String.valueOf(letter);
            for (int j = 1; j <= limit; j++) {
                for (Iterator<Map.Entry<List<Integer>, Integer>> iterator = tmpNumbers.entrySet().iterator(); iterator.hasNext(); ) {

                    Map.Entry<List<Integer>, Integer> entry = iterator.next();

                    List<Integer> key = entry.getKey();
                    int result = entry.getValue();

                    List<Integer> tmp = new ArrayList<>(key);
                    if (tmp.get(i) == 0) {
                        tmp.set(i, 1);
                    } else {
                        tmp.set(i, 0);
                    }
                    int result2 = numberMap.get(tmp);

                    if (result != result2) {
                        List<String> existingValueListForKey = cases.get(key);
                        List<String> existingValueListForTmp = cases.get(tmp);

                        if (existingValueListForKey != null) {
                            existingValueListForKey.add(condition + j);
                        } else {
                            List<String> newValueList = new ArrayList<>();
                            newValueList.add(condition + j);
                            cases.put(key, newValueList);
                        }

                        if (existingValueListForTmp != null) {
                            existingValueListForTmp.add(condition + j);
                        } else {
                            List<String> newValueList = new ArrayList<>();
                            newValueList.add(condition + j);
                            cases.put(tmp, newValueList);
                        }
                        tmpNumbers.remove(key);
                        tmpNumbers.remove(tmp);
                        break;
                    }

                }

            }
        }


        return cases;
    }
    
    public static List<List<Integer>> mapToList(  Map<List<Integer>, Integer> cases  ){
        List<List<Integer>> num= new ArrayList<>();
        List<Integer> nums= new ArrayList<>();
        Map<List<Integer>, Integer> tmp = new HashMap<>(cases);
        for (Map.Entry<List<Integer>, Integer> entry : tmp.entrySet()) {
            List<Integer> entryList = new ArrayList<>(entry.getKey());
            entryList.add(entry.getValue()); num.add(entryList);
        }
        return num;
        
    }

    public static List<List<Integer>> findTestCasesForMcDc(Map<List<Integer>, List<String>> cases){
        List<Integer> keyWithLongestValue = null;
        int maxLength = 0;
        List<Integer> numConditions=new ArrayList<>();
        if (!cases.isEmpty()) {
            numConditions = cases.keySet().iterator().next();
        }



        List<List<Integer>> testCases= new ArrayList<>();
        Map<List<Integer>, List<String>> tmpCases= new HashMap<>(cases);

        for (Map.Entry<List<Integer>, List<String>> entry : tmpCases.entrySet()) {
            List<String> value = entry.getValue();
            int length = value.size();

            if (length > maxLength) {
                maxLength = length;
                keyWithLongestValue = entry.getKey();
            }

        }




        testCases.add(keyWithLongestValue);
        for (String value : new ArrayList<>(tmpCases.get(keyWithLongestValue))) {
            for (List<Integer> val : new ArrayList<>(tmpCases.keySet())) {
                List<String> KeValues = tmpCases.get(val);
                if (KeValues.contains(value)) {
                    if(!(testCases.contains(val))){
                        testCases.add(val);
                        tmpCases.remove(val);
                    }
                }
            }
        }

        char missingLetter='\0';
        List<String> value = cases.get(keyWithLongestValue); // Wert des Schlüssels erhalten
        int length = value.size();
        char limit= (char) ('A'+ length);
        if(length!=numConditions.size()){
            List<Character> letters=new ArrayList<>();
            for (String values: tmpCases.get(keyWithLongestValue)) {
                letters.add(values.charAt(0));
            }

            for (char i = 'A'; i  <= limit; i++) {
                if (!(letters.contains(i))) {
                    missingLetter=i;
                }

            }


            for (List<Integer> val : new ArrayList<>(tmpCases.keySet())){
                List<String> KeValues = tmpCases.get(val);
                if (KeValues.contains(Character.toString(missingLetter)+"1")) {
                    if(!(testCases.contains(val))){
                        testCases.add(val);
                        tmpCases.remove(val);
                    }
                }

            }


        }

        return testCases;

    }

    public static List<List<Integer>> findConditionsForMcDC(List<List<Integer>> testCases, List<List<Integer>> numbers){
        List<List<Integer>> result= new ArrayList<>(testCases);
        List<List<Integer>> cases= new ArrayList<>();
        Map<List<Integer>, Integer> numberMap= convertListToMap(numbers);
            for (List<Integer> resultList: result) {
                if(numberMap.containsKey(resultList)){
                    int value= numberMap.get(resultList);
                    List<Integer> tmp = new ArrayList<>(resultList);
                    tmp.add(value);
                    cases.add(tmp);


            }
        }
            return cases;

    }
    public static String extractFileName(String path) {
        int lastIndex = path.lastIndexOf("/");

        if (lastIndex != -1 && lastIndex < path.length() - 1) {
            return path.substring(lastIndex + 1, path.length()-3);
        } else {
            return null;
        }
    }


    public static void generateList(List<String> pathNames, boolean coverage) {
        int counter= 0;
        String basePathToWrite="/Users/yaseminakaydin/Desktop/exercises/Results/";
            for (String path: pathNames) {
                if(coverage){
                String[] elements= readData(path);
                String fileName= extractFileName(path);
                writeData(basePathToWrite + fileName + "McDcTest.md" ,
                        header(elements),findConditionsForMcDC(
                                findTestCasesForMcDc(
                                        generateTestMCDC(3,numbers(elements))), numbers(elements)) );

                counter++;

            } else {
                    String[] elements= readData(path);
                    String fileName= extractFileName(path);
                    List<List<Integer>> nums= Parser.mapToList(Parser.generateMBUU(Parser.numbers(elements)));
                    writeData(basePathToWrite + fileName + "MBUUTest.md", Parser.header(elements), nums);
                }



        }


    }




//    public static void main(String[] args) throws IOException {
//        String datName = "/Users/yaseminakaydin/Desktop/exercises/exercise1.md";
//        String[] elements= readData(datName);
//        System.out.println(numbers(elements));
//        System.out.println(header(elements));
//       //writeData("/Users/yaseminakaydin/Desktop/exercises/hallo", header(elements), numbers(elements));
//       //convertListToMap(numbers(elements));
//       //List<List<Integer>> nums= mapToList(generateMBUU(numbers(elements)));
//       //writeData("/Users/yaseminakaydin/Desktop/exercises/mbuuTest.md", header(elements), nums);
//        generateTestMCDC(3, numbers(elements));
//       findTestCasesForMcDc(generateTestMCDC(3, numbers(elements)));
//       //findConditionsForMcDC(findTestCasesForMcDc(generateTestMCDC(3,numbers(elements))), numbers(elements));
//       writeData("/Users/yaseminakaydin/Desktop/exercises/mcdcExercise1.md", header(elements),findConditionsForMcDC(findTestCasesForMcDc(generateTestMCDC(3,numbers(elements))), numbers(elements)) );
//
//
//
//
//    }
}
