import java.util.*;

public class main {
    public static void main(String args[]) throws Exception {
        Scanner myscanner = new Scanner(System.in);
        String letters = myscanner.nextLine();
        String[] array = new String[myscanner.nextInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = myscanner.nextLine();
        }
        System.out.println(findLength(letters, array));
    }


    public static int findLength(String letters, String[] array) {

        String regex = "[" + letters + "]{0," + letters.length() + "}";
        ArrayList<String> filteredDict = new ArrayList<>();
        int longestWordLength = 0;

        for (String word : array) {
            if (word.matches(regex)) {
                filteredDict.add(word);
            }
        }

        for (String word : filteredDict) {

            ArrayList<Character> charList = new ArrayList<>();
            for (Character c : word.toCharArray()) {
                charList.add(c);
            }

            ArrayList<Character> letterList = new ArrayList<>();
            for (Character c : letters.toCharArray()) {
                letterList.add(c);
            }

            for (Character letter : charList) {
                letterList.remove(letter);
                charList.remove(letter);
            }

            if (charList.isEmpty()) {
                if (word.length() > longestWordLength)
                    longestWordLength = word.length();
            }
        }

        return longestWordLength;

    }
}

