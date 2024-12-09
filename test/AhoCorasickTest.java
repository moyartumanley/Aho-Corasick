import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AhoCorasickTest {  

    @Test
    public void testAdd(){ //Test from HW3. Should still work after implementation
        AhoCorasick tree = new AhoCorasick();
        tree.addWord("cat");
        tree.addWord("muse");
        tree.addWord("muscle");
        tree.addWord("musk");
        tree.addWord("po");
        tree.addWord("pot");
        tree.addWord("pottery");
        tree.addWord("potato");
        tree.addWord("possible");
        tree.addWord("possum");
        tree.addWord("pot");
        assertEquals(10, tree.size());
    }

    @Test
    public void testContains(){ //Altered test from HW3. Should still work after implementation
        AhoCorasick tree = new AhoCorasick();
        tree.addWord("cat");
        tree.addWord("muse");
        tree.addWord("muscle");
        tree.addWord("musk");
        tree.addWord("po");
        tree.addWord("pot");
        tree.addWord("pottery");
        tree.addWord("potato");
        tree.addWord("possible");
        tree.addWord("possum");
        tree.addWord("pot");
        assertTrue(tree.contains("mu"));
        assertFalse(tree.contains("dog"));
        assertTrue(tree.contains("pot"));
        assertTrue(tree.contains("pottery"));
        assertTrue(tree.contains("possum"));
        assertEquals(10, tree.size());
    }

    @Test
    public void testContainsWord(){ //Test from HW3. Should still work after implementation
        AhoCorasick tree = new AhoCorasick();
        tree.addWord("cat");
        tree.addWord("muse");
        tree.addWord("muscle");
        tree.addWord("musk");
        tree.addWord("po");
        tree.addWord("pot");
        tree.addWord("pottery");
        tree.addWord("potato");
        tree.addWord("possible");
        tree.addWord("possum");
        tree.addWord("pot");
        assertTrue(tree.contains("mu"));
        assertFalse(tree.contains("dog"));
        assertTrue(tree.contains("pot"));
        assertTrue(tree.contains("pottery"));
        assertTrue(tree.contains("possum"));
        assertEquals(10, tree.size());
    }

    @Test
    public void testPrefix(){ //Test from HW3 with an added test for a case with no results. Should still work after implementation
        AhoCorasick tree = new AhoCorasick();
        tree.addWord("cat");
        tree.addWord("muse");
        tree.addWord("muscle");
        tree.addWord("musk");
        tree.addWord("poe");
        tree.addWord("pot");
        tree.addWord("pottery");
        tree.addWord("potato");
        tree.addWord("possible");
        tree.addWord("possum");
        tree.addWord("pot");
        ArrayList<String> result = tree.getWordsForPrefix("pot");
        assertEquals(3, result.size());
        assertTrue(result.contains("pot"));
        assertTrue(result.contains("pottery"));
        assertTrue(result.contains("potato"));
        
        result = tree.getWordsForPrefix("mu");
        assertEquals(3, result.size());
        assertTrue(result.contains("muse"));
        assertTrue(result.contains("muscle"));
        assertTrue(result.contains("musk"));

        result = tree.getWordsForPrefix("ssssss");
        assertEquals(0, result.size());
    }

    @Test
    public void testSuffixes(){
        AhoCorasick tree = new AhoCorasick();
        tree.addWord("a");
        tree.addWord("ab");
        tree.addWord("bc");
        tree.addWord("bca");
        tree.addWord("c");
        tree.addWord("caa");
        tree.updateSuffixes();

        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("a").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("b").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("c").suffix);
        assertEquals(tree.getNodeWithString("b"), tree.getNodeWithString("ab").suffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").suffix);
        assertEquals(tree.getNodeWithString("ca"), tree.getNodeWithString("bca").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").suffix);

    }

    @Test
    public void testTerminalSuffixes(){
        AhoCorasick tree = new AhoCorasick();
        tree.addWord("a");
        tree.addWord("ab");
        tree.addWord("bc");
        tree.addWord("bca");
        tree.addWord("c");
        tree.addWord("caa");
        tree.updateSuffixes();

        assertEquals(null, tree.getNodeWithString("a").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("b").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("c").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("ab").terminalSuffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("bca").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").terminalSuffix);

    }

    @Test
    public void testBothSuffixes(){
        AhoCorasick tree = new AhoCorasick();
        tree.addWord("a");
        tree.addWord("ab");
        tree.addWord("bab");
        tree.addWord("bc");
        tree.addWord("bca");
        tree.addWord("c");
        tree.addWord("caa");
        tree.updateSuffixes();

        //All suffixes
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("a").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("b").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("c").suffix);
        assertEquals(tree.getNodeWithString("b"), tree.getNodeWithString("ab").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ba").suffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").suffix);
        assertEquals(tree.getNodeWithString("ab"), tree.getNodeWithString("bab").suffix);
        assertEquals(tree.getNodeWithString("ca"), tree.getNodeWithString("bca").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").suffix);

        //All terminal suffixes
        assertEquals(null, tree.getNodeWithString("a").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("b").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("c").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("ab").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ba").terminalSuffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").terminalSuffix);
        assertEquals(tree.getNodeWithString("ab"), tree.getNodeWithString("bab").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("bca").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").terminalSuffix);
    }

    @Test
    public void testFinalTreeListConstructor(){
        List<String> wordList = new ArrayList<>();
        wordList.add("a");
        wordList.add("ab");
        wordList.add("bab");
        wordList.add("bc");
        wordList.add("bca");
        wordList.add("c");
        wordList.add("caa");
        AhoCorasick tree = new AhoCorasick(wordList);

        assertTrue(tree.contains("a"));
        assertTrue(tree.contains("ab"));
        assertTrue(tree.contains("bab"));
        assertTrue(tree.contains("bc"));
        assertTrue(tree.contains("bca"));
        assertTrue(tree.contains("c"));
        assertTrue(tree.contains("caa"));

        assertEquals(7, tree.size());

        //Suffix Test is the same
        //All suffixes
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("a").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("b").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("c").suffix);
        assertEquals(tree.getNodeWithString("b"), tree.getNodeWithString("ab").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ba").suffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").suffix);
        assertEquals(tree.getNodeWithString("ab"), tree.getNodeWithString("bab").suffix);
        assertEquals(tree.getNodeWithString("ca"), tree.getNodeWithString("bca").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").suffix);

        //All terminal suffixes
        assertEquals(null, tree.getNodeWithString("a").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("b").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("c").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("ab").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ba").terminalSuffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").terminalSuffix);
        assertEquals(tree.getNodeWithString("ab"), tree.getNodeWithString("bab").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("bca").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").terminalSuffix);
    }

    @Test
    public void testFinalTreeSetConstructor(){
        Set<String> wordList = new HashSet<>();
        wordList.add("a");
        wordList.add("ab");
        wordList.add("bab");
        wordList.add("bc");
        wordList.add("bca");
        wordList.add("c");
        wordList.add("caa");
        AhoCorasick tree = new AhoCorasick(wordList);

        assertTrue(tree.contains("a"));
        assertTrue(tree.contains("ab"));
        assertTrue(tree.contains("bab"));
        assertTrue(tree.contains("bc"));
        assertTrue(tree.contains("bca"));
        assertTrue(tree.contains("c"));
        assertTrue(tree.contains("caa"));

        assertEquals(7, tree.size());

        //Suffix Test is the same
        //All suffixes
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("a").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("b").suffix);
        assertEquals(tree.getNodeWithString(""), tree.getNodeWithString("c").suffix);
        assertEquals(tree.getNodeWithString("b"), tree.getNodeWithString("ab").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ba").suffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").suffix);
        assertEquals(tree.getNodeWithString("ab"), tree.getNodeWithString("bab").suffix);
        assertEquals(tree.getNodeWithString("ca"), tree.getNodeWithString("bca").suffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").suffix);

        //All terminal suffixes
        assertEquals(null, tree.getNodeWithString("a").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("b").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("c").terminalSuffix);
        assertEquals(null, tree.getNodeWithString("ab").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ba").terminalSuffix);
        assertEquals(tree.getNodeWithString("c"), tree.getNodeWithString("bc").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("ca").terminalSuffix);
        assertEquals(tree.getNodeWithString("ab"), tree.getNodeWithString("bab").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("bca").terminalSuffix);
        assertEquals(tree.getNodeWithString("a"), tree.getNodeWithString("caa").terminalSuffix);
    }

    @Test
    public void testSearchForAlternativeWords(){
        List<String> wordList = new ArrayList<>();
        wordList.add("a");
        wordList.add("ab");
        wordList.add("bab");
        wordList.add("bc");
        wordList.add("bca");
        wordList.add("c");
        wordList.add("caa");
        AhoCorasick tree = new AhoCorasick(wordList);

        assertEquals(List.of("a","ab"), tree.searchNotPrefixSimilarWords("aa"));
        assertEquals(List.of("c","caa"), tree.searchNotPrefixSimilarWords("cac"));
        assertEquals(List.of("caa"), tree.searchNotPrefixSimilarWords("bcaa"));
    }

    @Test
    public void testSearchForAlternativeWordsWithActualWords(){
        List<String> wordList = new ArrayList<>();
        wordList.add("red");
        wordList.add("read");
        wordList.add("dread");
        wordList.add("bead");
        wordList.add("eat");
        wordList.add("ear");
        wordList.add("dear");
        wordList.add("bear");
        wordList.add("fear");
        wordList.add("led");
        wordList.add("lead");
        wordList.add("leaf");
        wordList.add("earth");
        wordList.add("reed");
        AhoCorasick tree = new AhoCorasick(wordList);

        assertEquals(List.of("ear","earth"), tree.searchNotPrefixSimilarWords("bear"));
        assertEquals(List.of("ear","earth", "eat"), tree.searchNotPrefixSimilarWords("ree"));
    }
    
}
