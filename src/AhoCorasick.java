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
     * Aho-Corasick tree with null root node
     * It is best to use the constructor with the list parameter to properly set suffixes
     */
    public AhoCorasick(){
        root = new TreeNode(); //null node
        root.suffix = root; //root's suffix is itself, helps with recursion for suffix assignment
    }

    /**
     * Aho-Corasick tree with all of the words in wordList. 
     */
    public AhoCorasick(List<String> wordList){
        root = new TreeNode(); //null node
        root.suffix = root;

        addWords(wordList);
        updateSuffixes();
    }

    /**
     * Aho-Corasick tree with all of the words in wordList. 
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
    public void addWords(List<String> wordList){
        for (int i = 0; i < wordList.size(); i++){
            addWord(wordList.get(i));
        }
    }

    /**
     * Adds all of the words from the list to the tree where each letter in sequence is added as a node
     * If a word, is already in the tree, then nothing will happen in relation to that word.
     * @param wordList
     */
    public void addWords(Set<String> wordList){
        for (String word : wordList) {
            addWord(word);
        }
    }


    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void addWord(String word){
        if (add(word)){
        getNodeWithString(word).inDictionary = true;
        size++;
            }
        }

    /**
     * Adds the string ot the tree.
     *  Returns true if successful, false otherwise.
     */ 
    public boolean add(String string){
        if (!contains(string)){ // no effect if word already exists
            TreeNode current = root;

            for (int i = 0; i < string.length(); i++){ // iterates through each character in the word, going from left to right
                char currentChar = string.charAt(i); 

                if (!current.children.containsKey(currentChar)){ // if letter doesn't exist at that branch, put it in the tree
                    current.children.put(currentChar, new TreeNode(string.substring(0, i + 1)));
                }

                current = current.children.get(currentChar);

                if(current.string.length() == 1){ // All of root's children's suffixes will be root. Helps with setting suffixes later
                    current.suffix = root;
                }
            }
            return true;
    }
    return false;
}

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){
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

        TreeNode prefixNode = root;
        while (string.length() > 0){ // loop to get to the right node containing the string

            char currentChar = string.charAt(0);

            if (prefixNode.children.containsKey(currentChar)){
                string = string.substring(1, string.length());
                prefixNode = prefixNode.children.get(currentChar);
            }
            else{
                break;
            }
        }


        if (prefixNode.terminalSuffix!= null && !prefixNode.suffix.equals(prefixNode.terminalSuffix)){
            result.addAll(getWordsForPrefix(prefixNode.terminalSuffix.string));
        }

        if (!prefixNode.suffix.string.equals("")){
            result.addAll(getWordsForPrefix(prefixNode.suffix.string));
        }
        

        while(result.contains("")){ //removes all empty strings
            result.remove("");
        }

        LinkedHashSet<String> removingDups = new LinkedHashSet<>(result);
        result = new ArrayList<>(removingDups);
        
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
     * Sets suffix and terminal suffix pointers for every node. 
     */
    public void updateSuffixes(){ //TODO: make pointers that show what the node is a suffix of for easier navigation. Maybe also add traversal list. 
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
