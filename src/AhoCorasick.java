import java.util.ArrayList;
import java.util.Map;

public class AhoCorasick {
    
     private TreeNode root; 


     //currently code ripped off from hw 3 lmao
    // Number of words contained in the tree
    private int size;

    public AhoCorasick(){
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void add(String word){
        if (!contains(word)){ // no effect if word already exists
            TreeNode current = root;

            while (word.length() > 0){ // iterates through each character in the word, going from left to right
                char currentChar = word.charAt(0); 

                if (!current.children.containsKey(currentChar)){ // if letter doesn't exist at that branch, put it in the tree
                    current.children.put(currentChar, new TreeNode());
                }

                word = word.substring(1, word.length()); // moves to next character/node
                current = current.children.get(currentChar);
            }
            current.inDictionary = true;
            size++;
        }
    }

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){
        TreeNode current = root;

        while (word.length() > 0){ // iterates through each character of the word

            char currentChar = word.charAt(0);

            if (current.children.containsKey(currentChar)){
                word = word.substring(1, word.length());
                current = current.children.get(currentChar);
            }
            else{
                return false; // word doesn't exist if the tree doesn't have parts of the word
            }
        }
        if (current.inDictionary){ // word must be an actual word and not a part of a word.
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        ArrayList<String> result = new ArrayList<>();
        TreeNode current = root;
        String prefixCopy = prefix + "";

        while (prefixCopy.length() > 0){ // loop to get to the right node containing the prefix

            char currentChar = prefixCopy.charAt(0);

            if (current.children.containsKey(currentChar)){
                prefixCopy = prefixCopy.substring(1, prefixCopy.length());
                current = current.children.get(currentChar);
            }
            else{
                return result; // prefix doesn't exist, thus returns nothing.
            }
        }
        if (current.inDictionary){ // checks if prefix is a word
            result.add(prefix);
        }
        result.addAll(recursiveGetWordsForPrefix(prefix, current));

        return result;
    }

    private ArrayList<String> recursiveGetWordsForPrefix(String prefix, TreeNode currentLetter){
        if (currentLetter.children.size() == 0){
            return new ArrayList<>();
        }
        else{
            ArrayList<String> words = new ArrayList<>();
            Map<Character, TreeNode> letters = currentLetter.children;

            for (Character letter : letters.keySet()) { // loop that searches all letters that the current letter/node can connect to
                String potentialWord = prefix + letter;

                if (letters.get(letter).inDictionary){ // if a word is found, add to list
                    words.add(potentialWord);
                }

                words.addAll(recursiveGetWordsForPrefix(potentialWord, letters.get(letter))); // recursive call to find potential words in the next node's children
            }
            return words;
        }
    }

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
}
