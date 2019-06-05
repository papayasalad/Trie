import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Trie {
    private Tree trie = new Tree();

    public void printTrie() {
        trie.print();
    }

    public void addWords(List<String> words) {
        for (String word: words) {
            insert(word);
        }
    }

    public void insert(String word) {
        EntryNode current = trie.getRoot();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            EntryNode child = current.getChild(character);
            if (child == null) {
                child = new EntryNode(character, i == word.length() - 1);
                current.addChild(child);
            } else if (i == word.length() - 1) {
                child.setTerminal(true);
            }

            current = child;
        }
    }

    public boolean contains(String potentialWord) {
        EntryNode current = trie.getRoot();
        for (int i = 0; i < potentialWord.length(); i++) {
            char character = potentialWord.charAt(i);

            EntryNode child = current.getChild(character);
            if (child == null) {
                return false;
            }
            current = child;
        }

        return current.isTerminal();
    }

    // remove word function:
    // 1) unmark terminal to false for the last character;
    // 2) remove the child from map of children if the EntryNode is not a termianl and has no children.
    public void remove(String word) {
        int wordLength = word.length();
        for (int i = 0; i < wordLength; i++) {
            EntryNode current = trie.getRoot();
            for (int j = 0; j < word.length() - 1; j++) {
                current = current.getChild(word.charAt(j));
            }
            EntryNode child = current.getChild(word.charAt(word.length() - 1));
            if (i == 0) {
                child.setTerminal(false);
            }
            if (!child.isTerminal() && child.getChildren().isEmpty()) {
                current.removeChild(child);
                word = word.substring(0, word.length() - 1);
            } else {
                return;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("src/main/resources/dictionary.txt"));
        List<String> words = new ArrayList<>();

        while (scan.hasNext()) {
            words.addAll(Arrays.asList(scan.nextLine().split(" ")));
        }

        Trie trie = new Trie();
        trie.addWords(words);
        trie.printTrie();

        System.out.println();
        System.out.println("This test should report false:");
        System.out.println("Contains 's': " + trie.contains("s"));
        System.out.println("Contains 'bye': " + trie.contains("bye"));
        System.out.println("Contains 'bird': " + trie.contains("bird"));

        System.out.println();
        System.out.println("These tests should report true:");
        System.out.println("Contains 'she': " + trie.contains("she"));
        System.out.println("Contains 'sells': " + trie.contains("sells"));
        System.out.println("Contains 'sea': " + trie.contains("sea"));
        System.out.println("Contains 'shells': " + trie.contains("shells"));
        System.out.println("Contains 'by': " + trie.contains("by"));
        System.out.println("Contains 'the': " + trie.contains("the"));
        System.out.println("Contains 'shore': " + trie.contains("shore"));
        System.out.println("Contains 'shorebird': " + trie.contains("shorebird"));

        trie.remove("shorebird");
        trie.remove("sea");
        trie.printTrie();
    }
}
