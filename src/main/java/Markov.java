import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Markov {
    private static final String BEGINS_SENTENCE = "__$";
    private String prevWord;
    private HashMap<String, ArrayList<String>> words = new HashMap<>();
    private static final String PUNCTUATION_MARKS = ".!?$";

    public Markov(){
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    public void addFromFile(String filename){
        try (FileReader fr = new FileReader(filename)){
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
                String newLine = scan.nextLine();
                addLine(newLine);
            }
        } catch (Exception e) {
            System.out.println("Couldn't open " + filename);
        }
    }

    public void addLine(String line){
        if(line.isEmpty()){
            return;
        } else{
            String [] separatedLine = line.split("\\s+");
            for(int i = 0; i < separatedLine.length; i++){
                addWord(separatedLine[i]);
            }
        }
    }

    public void addWord(String currentWord){
        if(endsWithPunctuation(prevWord)){
            words.get(BEGINS_SENTENCE).add(currentWord);
        } else{
            if(!words.containsKey(prevWord)){
                words.put(prevWord, new ArrayList<>());
            }
            words.get(prevWord).add(currentWord);
        }
        prevWord = currentWord;
    }

    public String getSentence() {
        return " ";
    }

    public String randomWord(String word){
        return word;
    }

    public static boolean endsWithPunctuation(String word){
        try{
            char lastChar = word.charAt(word.length() - 1);
            for(int i = 0; i < PUNCTUATION_MARKS.length(); i++){
                if(PUNCTUATION_MARKS.charAt(i) == lastChar){
                    return true;
                }
            }
        } catch (Exception e){
            System.out.println("ERROR caused by: " + word);
        }
        return false;
    }

    public String toString(){
        return " ";
    }
}
