import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    // True if this node represents the last character in a String that is from the created dictionary
    // from the root of the tree to this node.
    public boolean inDictionary;

    // The letter this node represents
    // This isn't strickly necessary since you can get the letter from the key used to link this node's parent,
    // but this can make things easier.
    public char letter;

    // The node's children keyed by each child node's letter
    public Map<Character, TreeNode> children;
    public Map<Character, TreeNode> suffix;
    public Map<Character, TreeNode> dictionarySuffix;

    public TreeNode(){
        children = new HashMap<>();
        suffix = new HashMap<>();
        dictionarySuffix = new HashMap<>();
        inDictionary = false;
    }
}
