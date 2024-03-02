import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Translator {

    // english to dwarven translator method:
    public static String englishToDwarf(String input) {
        input = input.toUpperCase();
        String[] inArray = input.split(" ");
        int i = 0;
        int count = 1;
        StringBuilder output = new StringBuilder();
        String hr = "___________________________________________________\n";

        // write code that finds the translation and places it in the next open spot in the output array
        // then output all of the translated words line by line.
        // try to keep words with double meaning like bear somehow. idk how, good luck :)
        while(i < inArray.length) {
            int wordLength = inArray[i].length();

            try (BufferedReader br = new BufferedReader(new FileReader("Dwarven.txt"))) {
                String line;
                // read file as long as line isn't empty
                output.append(inArray[i].toUpperCase()).append(":\n");
                while ((line = br.readLine()) != null) {
                    // if the line contains the input word append the translated
                    // part of that line to the StringBuilder "output"
                    int findCheck = 0;
                    if (line.contains(" " + inArray[i] + " ") && !(line.contains("_"))) {
                        output.append(" • ").append(line.substring(wordLength + 2)).append("\n");
                        findCheck++;
                    } 
                    else if (line.contains(" " + inArray[i] + " ") && line.contains("_")) {
                        output.append(" • ").append(line.substring(wordLength + 3)).append("\n");
                        findCheck++;
                    }
//                    else if (count == 2197 && findCheck == 0){
//                        output.append("No matches found.\n");
//                    }
                    // increases "count" integer for each line read
                    count++;
                }
                if (output.length() == input.length() + 1) {
                    return "No matches found.\n";
                }
                if (i < inArray.length - 1) {
                    output.append(hr);
                }
            }
            catch (IOException e) {
                return "Error reading file: " + e.getMessage() + "\n";
            }
            i++;
        }
        return output.toString();
    }

    // dwarven to english translator method:
    public static String dwarfToEnglish(String word) {
        word = word.toLowerCase();
        int count = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("Dwarven.txt"))) {
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.contains(" " + word + " ") && !(line.contains("_"))) {
                    String[] words = line.split(" ");
                    output.append(" • ").append(words[1].toLowerCase()).append("\n");
                } else if (line.contains(" " + word + " ") && line.contains("_")) {
                    String[] words = line.split(" ");
                    output.append(" • ").append(words[2].substring(1)).append(" ").append(words[1].toLowerCase()).append("\n");
                }
                count++;
            }
            if (output.length() == 0) {
                return "No matches found.\n";
            } else {
                return output.toString();
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage() + "\n";
        }
    }

    // get all dwarven words
    public static String getAllDwarvenWords() {
        try (BufferedReader br = new BufferedReader(new FileReader("Dwarven.txt"))) {
            List<String> words = br.lines().map(line -> line.split(" ")[0]).distinct().collect(Collectors.toList());
            return String.join(", ", words) + "\n";
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage() + "\n";
        }
    }
}
