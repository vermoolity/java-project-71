package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



public class Differ {
    static String Symbol;
    static HashMap<String, Object> generalKeyAndValues = new HashMap<>();
    public static String generate(String firstFilePath,
                                  String secondFilePath,
                                  String symbol) throws Exception {

        Symbol = symbol;
        // Достаем из файлов, формата json, контент и сортируем ключи в Map
        TreeMap<String, Object> firstFileContent = new TreeMap<String, Object>(openfile(firstFilePath));
        TreeMap<String, Object> secondFileContent = new TreeMap<String, Object>(openfile(secondFilePath));

        return resultString(iterateOverAllValues(firstFileContent, secondFileContent));
    }

    // Достаем из файлов, формата json, контент
    public static Map<String, Object> openfile (String readFilePath) throws Exception {
        Path path = Paths.get(readFilePath).toAbsolutePath().normalize();
        // проверка существования файла
        if (!java.nio.file.Files.exists(path)) {
            throw new Exception("File '" + path + "' does not exist");
        }
        String content = java.nio.file.Files.readString(path);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> fileContent
                = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
        });
        return fileContent;
    }
    public static List<String> iterateOverAllValues (Map<String, Object> firstFileContent,
                                                     Map<String, Object> secondFileContent) {
        ArrayList<String> result = new ArrayList<String>();
        for (var key : firstFileContent.keySet()) {
            var value = firstFileContent.get(key);
            if (generalKeyAndValues.containsKey(key)) {
                if (!generalKeyAndValues.get(key).toString().equals(value.toString())) {
                    String resultSymbol = presenceOfValue(key, value, secondFileContent);
                    result.add(resultSymbol + " " + key + ": " + value);
                }
            } else {
                String resultSymbol = presenceOfValue(key, value, secondFileContent);
                result.add(resultSymbol + " " + key + ": " + value);
            }
        }
        return result;
    }
    // Проверка наличия значения.
    public static String presenceOfValue(String key,
                                         Object value,
                                         Map<String, Object> secondFileContent) {
        String resultSymbol = Symbol;
        if (secondFileContent.containsKey(key)) {
            if (value.toString().equals(secondFileContent.get(key).toString())) {
                resultSymbol = " ";
                generalKeyAndValues.put(key, value);
            }
        }
        return resultSymbol;
    }

    public static String resultString(List<String> listOfResults) {
        String result = "";
        for (var i  = 0; i < listOfResults.size() - 1; i++) {
            result = result + "  " + listOfResults.get(i) + "\n";
        }
        result = result + "  " + listOfResults.get(listOfResults.size() - 1);
        if (Symbol.equals("-")) {
            result = "{\n" + result;

        } else {
            result = result + "\n}";
        }
        return result;
    }
}
