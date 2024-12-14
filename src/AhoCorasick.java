import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AhoCorasick {
    
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    /**
     * Aho-Corasick tree with null root node for testing methods (which were originally public)
     * It is best to use the constructor with the list parameter to properly set suffixes
     */
    public AhoCorasick(){
        root = new TreeNode(); //null node
        root.suffix = root; //root's suffix is itself, helps with recursion for suffix assignment
    }

    /**
     * Unmodifiable Aho-Corasick tree with all of the words in wordList. 
     */
    public AhoCorasick(List<String> wordList){
        root = new TreeNode(); //null node
        root.suffix = root;

        addWords(wordList);
        updateSuffixes();
    }

    /**
     * Unmodifiable Aho-Corasick tree with all of the words in wordList. 
     * @param wordList
     */
    public AhoCorasick(Set<String> wordList){
        root = new TreeNode(); //null node
        root.suffix = root;

        addWords(wordList);
        updateSuffixes();
    }

    /**
     * Adds all of the words from the list to the tree where each letter in sequence is added as a node
     * If a word, is already in the tree, then nothing will happen in relation to that word.
     * @param wordList
     */
    private void addWords(List<String> wordList){
        for (int i = 0; i < wordList.size(); i++){
            addWord(wordList.get(i));
        }
    }

    /**
     * Adds all of the words from the list to the tree where each letter in sequence is added as a node
     * If a word, is already in the tree, then nothing will happen in relation to that word.
     * @param wordList
     */
    private void addWords(Set<String> wordList){
        for (String word : wordList) {
            addWord(word);
        }
    }


    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    private void addWord(String word){
        if (!containsWord(word)){ // no effect if word already exists
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
        getNodeWithString(word).inDictionary = true;
        size++;
        }
    }

    /**
     * Checks whether the string exists in the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String string){
        TreeNode wordNode = getNodeWithString(string);
        return wordNode != null;
    }

    /**
     * Checks whether the word exists in the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean containsWord(String word){
        TreeNode wordNode = getNodeWithString(word);
        return wordNode != null && wordNode.inDictionary;
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
     * Searches for similar words to the string, ordering the elements of the list with words with similar prefixes to similar suffixes.
     * @param string
     * @return resulting list of similar words
     */
    public ArrayList<String> searchNotPrefixSimilarWords(String string){
        ArrayList<String> result = new ArrayList<>();
        String stringCopy = string + "";

        TreeNode prefixNode = root;
        while (stringCopy.length() > 0){ // loop to get to the right node containing the string

            char currentChar = stringCopy.charAt(0);

            if (prefixNode.children.containsKey(currentChar)){
                stringCopy = stringCopy.substring(1, stringCopy.length());
                prefixNode = prefixNode.children.get(currentChar);
            }
            else{
                break;
            }
        }

        result.addAll(getWordsContainingSuffix(string));

        while(result.contains("")){ //removes all empty strings just in case
            result.remove("");
        }

        LinkedHashSet<String> removingDups = new LinkedHashSet<>(result); //removing duplicates just in case
        result = new ArrayList<>(removingDups);
        
        return result;
    }

    /**
     * Finds suffix and terminal suffix of a given string, then finds words beginning with thoses suffixes in the current tree.
     * The order of the list can be arbitrary.
     * @param string
     * @return list of words with a suffix of the word
     */
    public List<String> getWordsContainingSuffix(String string){
        List<String> result = new ArrayList<>();

        String suffix = "";
        String terminalSuffix = "";
        boolean foundSuffix = false;
        boolean foundTerminalSuffix = false;

        for(int i = 1; i < string.length(); i++){
            String substring = (string.substring(i));
            if (!foundSuffix && contains(substring)){
                suffix = substring;
                foundSuffix = true;
            }
            if (!foundTerminalSuffix && containsWord(substring) && getNodeWithString(substring).inDictionary){
                terminalSuffix = substring;
                foundTerminalSuffix= true;
            }
            if(foundSuffix && foundTerminalSuffix){
                break;
            }
        }

        if (foundTerminalSuffix && !suffix.equals(terminalSuffix)){
            result.addAll(getWordsForPrefix(terminalSuffix));
        }

        if (!suffix.equals("")){
            result.addAll(getWordsForPrefix(suffix));
        }

        return result;
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
    private TreeNode getNodeWithString(String string){
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
     * Sets suffix and terminal suffix pointers for every node. 
     */
    private void updateSuffixes(){ 
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
