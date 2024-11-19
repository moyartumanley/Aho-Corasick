import java.util.ArrayList;
import java.util.Map;

public class AhoCorasick {
    
     private TreeNode root; 


     //currently code ripped off from hw 3 lmao
    // Number of words contained in the tree
    private int size;

    public AhoCorasick(){
        root = new TreeNode(); //null node
        root.suffix = root;
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void add(String word){
        if (!contains(word)){ // no effect if word already exists
            TreeNode current = root;

            for (int i = 0; i < word.length(); i++){ // iterates through each character in the word, going from left to right
                char currentChar = word.charAt(i); 

                if (!current.children.containsKey(currentChar)){ // if letter doesn't exist at that branch, put it in the tree
                    current.children.put(currentChar, new TreeNode(word.substring(0, i + 1)));
                }

                current = current.children.get(currentChar);

                if(current.string.length() == 1){ // All of root's children's suffixes will be root. Helps with setting suffixes later
                    current.suffix = root;
                }
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

        TreeNode current = getNodeWithString(prefix);
        if (current == null){
            return result;
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

    /**
     * Finds the node that represents the string inputed.
     * @param string 
     * @return Node containing the string. If the tree does not contain the string, returns null.
     */
    public TreeNode getNodeWithString(String string){
        TreeNode current = root;
        while (string.length() > 0){ // loop to get to the right node containing the string

            char currentChar = string.charAt(0);

            if (current.children.containsKey(currentChar)){
                string = string.substring(1, string.length());
                current = current.children.get(currentChar);
            }
            else{
                return null; // string doesn't exist inside the tree, thus returns nothing.
            }
        }
        return current;
    }

    /**
     * Sets suffix and terminal suffix pointers for every node. Should be called after adding all strings to the tree.
     */
    public void updateSuffixes(){
        recursiveSetSuffixes(root);
        recursiveSetTerminalSuffixes(root);
    }
    
    private void recursiveSetSuffixes(TreeNode currentNode){
        Map<Character, TreeNode> currentChildren = currentNode.children;
        if (currentChildren.size() == 0){
            return;
        }
        for (Character letter : currentChildren.keySet()){
            if (currentChildren.get(letter).suffix == null){
                if (currentNode.suffix.children.get(letter) != null){
                    currentChildren.get(letter).suffix = currentNode.suffix.children.get(letter);
                }
                else{
                    currentChildren.get(letter).suffix = currentNode.suffix;
                }
            }
            recursiveSetSuffixes(currentChildren.get(letter));

        }
    }

    private void recursiveSetTerminalSuffixes(TreeNode currentNode){
        Map<Character, TreeNode> currentChildren = currentNode.children;
        if (currentChildren.size() == 0){
            return;
        }
        for (Character letter : currentChildren.keySet()){
            TreeNode nextSuffix = currentChildren.get(letter).suffix;

            while (!nextSuffix.equals(root)){
                if (nextSuffix.inDictionary){
                    currentChildren.get(letter).terminalSuffix = nextSuffix;
                    break;
                }
                else{
                    nextSuffix = nextSuffix.suffix;
                }
            }
            recursiveSetTerminalSuffixes(currentChildren.get(letter));
        }
    }
}
