import java.util.*;


public class EntryNode {
    private char letter;
    private boolean isTerminal;
    private List<EntryNode> children = new ArrayList<>();

    EntryNode(char letter) {
        this(letter, false);
    }

    EntryNode(char letter, boolean isTerminal) {
        this.letter = letter;
        this.isTerminal = isTerminal;
    }

    List<EntryNode> getChildren() { return children; }

    EntryNode getChild(Character data) {
        int min = 0;
        int max = children.size() - 1;

        while (min <= max) {
            int mid = (min + max) / 2;
            EntryNode child = children.get(mid);
            if (child.letter == data) {
                return child;
            } else if (child.letter > data) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return null;
    }

    void addChild(EntryNode child) {
        int min = 0;
        int max = children.size() - 1;

        while (min <= max) {
            int mid = (min + max) / 2;
            char c = children.get(mid).letter;
            if (child.letter > c) {
                min = mid + 1;
            } else if (child.letter < c) {
                max = mid - 1;
            }
        }
        children.add(min, child);
    }

    void removeChild(EntryNode child) { this.children.remove(child); }

    boolean isTerminal() {
        return isTerminal;
    }

    void setTerminal(boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EntryNode)) {
            return false;
        }

        return ((EntryNode) obj).letter == this.letter;
    }

    public String toString() {
        char modifier = ' ';
        if (isTerminal) {
            modifier = '*';
        }

        return String.valueOf(new char[] {letter, modifier});
    }

    void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this);
        for (EntryNode child : children) {
            child.print(prefix + (isTail ? "    " : "│   "), false);
        }
    }
}

