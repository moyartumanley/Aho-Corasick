# Trendmaxxing

Info about your project goes here
    shoutout 𝔠𝔬𝔫𝔫𝔬𝔯𝔰𝔞𝔢𝔭

Rachel's notes here for now lmao (working on the Aho-Corasick algorithm):
- Tree similar to hw3: trie where nodes have a true or false value for if the String is within the dictionary (i.e. if the node is the last character of a word or not)
- There are 3 different kinds of connections:
    - Children: exactly the same from hw3: next letter in the word
    - Suffixes: connects the node of the longest possible suffix that exists in the tree. 
    - Terminal Suffixes: connects to the next **word** that can be reached by following the blue edges. Can be null if no such node exists

Sources:
- https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm
- https://cp-algorithms.com/string/aho_corasick.html
- https://compiler.club/aho-corasick/ (This one has a demo)

**HOW THE ALGORITHM WORKS FOR THE SAKE OF ABSTRACTION:**
After addding all of the nodes, call `updateSuffixes()` to essentially "finish" the tree. Each node has two kinds of suffixes. 

"Suffix" is the largest suffix that exist in the tree. For example, let's say the tree has the word "cat". The possible suffixes are "at", "a", and "". If the tree has the representing "at", then the suffix of "cat" points to the node "at". If "at" doesn't exist but "a" does, then the suffix is "a". Only single letter nodes should have their suffix be "". 

"Terminal Suffix" is the next word after following a chain of suffixes. This can be null if no such node exists. For example, let's say the tree has both the word "acceptable" and its largest suffix is "ceptable". That is not a word, but is in the tree somewhere and is thus the largest suffix. However, somewhere, "table" is in the tree, and it is "ceptable"'s largest suffix. Since "table" is a word, "table" will also be "ceptable"'s terminal suffix. "acceptable"'s suffix is not a word, so we go to the suffix's suffix. The suffix's suffix is a word, so acceptable's terminal suffix is "table". The chaining of suffixes would repeat until we either find a word or reach the null node, which in the null node case the terminal suffix is null.
