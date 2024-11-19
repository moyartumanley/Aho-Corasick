import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    // True if this node represents the last character in a String that is from the created dictionary
    // from the root of the tree to this node.
    public boolean inDictionary;

    // The letter that is added to the parent node to 
    // This may not be used because the edges (keys in the chidren map) represent the letter
    public char letter;

    // The word (or substring) that the node represents
    public String string;

    // The node's children keyed by each child node's letter
    public Map<Character, TreeNode> children = new HashMap<>();

    // The node's suffixes
    public TreeNode suffix;
    public TreeNode dictionarySuffix;

    public TreeNode(){
        inDictionary = false;
        string = "";
    }

    //Constructor that automanically sets the last character of the string as the letter while string the string itself
    public TreeNode(String string){
        inDictionary = false;
        letter = string.charAt(string.length() -1);
        this.string = string;
    }

}
