import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
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
            System.out.println("Couldn't open or find " + filename);
        }
    }

    public void addLine(String line){
        // To prevent whitespace lines from being split first and then passed into addWord()
        if(line.trim().isEmpty()){
            return;
        }
        String [] separatedLine = line.trim().split("\\s+");
        for(int i = 0; i < separatedLine.length; i++){
            addWord(separatedLine[i]);
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
        StringBuilder sb = new StringBuilder();
        String current = randomWord(BEGINS_SENTENCE);
        while(!endsWithPunctuation(current)){
            sb.append(current).append(" ");
            current = randomWord(current);
        }
        sb.append(current);
        return sb.toString();
    }

    public String randomWord(String keyWord){
        ArrayList<String> list = words.get(keyWord);
        Random rand = new Random();
        int index = 0;
        if(list != null && !list.isEmpty()){
            index = rand.nextInt(list.size());
        } else {
            // If keyword doesn't exist or is empty,
            // fall back to selecting random word from BEGINS_SENTENCE
            // This prevents returning am empty string
            // (implemented based on failed case)
            list = words.get(BEGINS_SENTENCE);
            if((list == null) || list.isEmpty()) {
                return "";
            } else{
                index = rand.nextInt(list.size());
            }
        }
        return list.get(index);
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
        return words.toString();
    }
}
