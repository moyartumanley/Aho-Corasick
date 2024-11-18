# Trendmaxxing

Info about your project goes here


Rachel's notes here for now lmao (working on the Aho-Corasick algorithm):
- Tree similar to hw3: trie where nodes have a true or false value for if the String is within the dictionary (i.e. if the node is the last character of a word or not)
- There are 3 different kinds of children which, in text that I'm reading from, is represented by color of the edge:
    - Black edges: exactly the same from hw3: next letter in the word
    - Blue edges: connects the node of the longest possible suffix that exists in the tree. Can be found using breadth-first search
    - Green edge: connects to the next node that can be reached by following the blue edges
    - https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm

- Figure out what memoizing is lmao
