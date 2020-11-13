import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
-----------------------------------------------------------------------------
    Sample solution to a Hangman problem.
    Stub class author is Dr. Phil Maguire, Lecturer at Maynooth University.
    Brain class author is me, Mihai Bocioroaga.
-----------------------------------------------------------------------------
 */

public class Stub{

    public static void main (String[] args){
        Scanner myscanner = new Scanner(System.in);
        int num = Integer.parseInt(myscanner.nextLine());
        String[] dictionary = new String[num];
        for(int i=0;i<num;i++){
            dictionary[i]=myscanner.nextLine();
        }
        String hash = dictionary[num-1]+(int)(Math.random()*100);
        int games = 100;
        int score = 0;
        for(int x=0;x<games;x++){

            Random r = new Random();
            String target = dictionary[r.nextInt(num)];

            String blackout="";
            for(int i=0;i<target.length();i++){
                blackout=blackout+"_";
            }

            Brain mybrain = new Brain(dictionary, blackout);
            int lives=8;

            boolean running = true;

            while(running){
                char guess = mybrain.guessLetter();
                String original = mybrain.hiddenWord;
                char[] arrayform = original.toCharArray();
                for(int i=0;i<target.length();i++){
                    if(target.charAt(i)==guess){
                        arrayform[i]=guess;
                    }
                }
                String newform = "";
                for(int i=0;i<target.length();i++){
                    newform=newform+arrayform[i];
                }
                mybrain.hiddenWord=newform;
                if(newform.equals(original)){
                    lives=lives-1;
                }
                if(lives==0){
                    running=false;
                }
                if(mybrain.hiddenWord.equals(target)){
                    running=false;
                    score=score+1;
                }
            }
        }
        System.out.println("You got "+score+" correct out of 100");
        try{
            System.out.println("Your Receipt: "+sha256(hash+score));
        }catch(NoSuchAlgorithmException e){};
    }

    public static String sha256(String input) throws NoSuchAlgorithmException {
        byte[] in = hexStringToByteArray(input);
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(in);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        if(len%2==1){
            s=s+"@";
            len++;
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
class Brain {
    public String[] dictionary;
    public String hiddenWord = "_____";
    private char[] letters = "etaoinsrhdlucmfywgpbvkxqjz".toCharArray();

    private Random r = new Random();
    private List<String> refinedDict = new ArrayList<>();
    private Map<Character, Integer> frequencyMap = new HashMap<>();

    public Brain(String[] wordlist, String target) {
        dictionary = wordlist;
        hiddenWord = target;
    }

    public char guessLetter() {
        if(refinedDict.isEmpty()) {
            generateRefinedDictionary(refinedDict);
        }
        for(String word : refinedDict){
            countChars(word.toCharArray(), frequencyMap);
        }
    }
    private void generateRefinedDictionary(List<String> dictToRefine){
        for (String word : dictionary) {
            if (word.matches(hiddenWord.replace("_", "."))) {
                dictToRefine.add(word);
            }
        }
    }
    private void countChars(char[] charsToCount, Map<Character, Integer> frequencyMap){
        for(Character c : charsToCount) {
            Integer count = frequencyMap.get(c);
            if (count == null)
                count = 0;
            frequencyMap.put(c, ++count);
        }
    }
    private char computeGuess(Map<Character, Integer> charFrequencyMap){
        ArrayDeque<Character> chars = new ArrayDeque<>();
        for(Map.Entry<Character, Integer> entry : charFrequencyMap.entrySet()){
            for(int i = entry.getValue(); i > 0; i--){
                chars.add(entry.getKey());
            }
        }
        int[] charFrequency = new int[26];
        for(int i = 0; i < charFrequency.length; i++){
            charFrequency[i] = Collections.frequency( chars, (char)(i + 97) );
        }
        for(int i = 0; i < charFrequency.length; i++){
            for(int j = 0; j < charFrequency.length; j++){

            }
        }
    }
}