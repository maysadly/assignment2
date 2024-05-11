import java.util.*;


public class BST<K extends Comparable<K>, V> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public void put(K key, V val) {
        if (root == null) {
            root = new Node(key, val);
            size++;
            return;
        }

        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                if (x.left == null) {
                    x.left = new Node(key, val);
                    size++;
                    return;
                }
                x = x.left;
            } else if (cmp > 0) {
                if (x.right == null) {
                    x.right = new Node(key, val);
                    size++;
                    return;
                }
                x = x.right;
            } else {
                x.val = val;
                return;
            }
        }
    }

    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public void delete(K key) {
        Node x = root;
        Node parent = null;
        while (x != null && !x.key.equals(key)) {
            parent = x;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
        }

        if (x == null) return;

        if (x.left == null) {
            if (parent == null) root = x.right;
            else if (parent.left == x) parent.left = x.right;
            else parent.right = x.right;
        } else if (x.right == null) {
            if (parent == null) root = x.left;
            else if (parent.left == x) parent.left = x.left;
            else parent.right = x.left;
        } else {
            Node successorParent = x;
            Node successor = x.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            if (successorParent == x) successorParent.right = successor.right;
            else successorParent.left = successor.right;

            x.key = successor.key;
            x.val = successor.val;
        }

        size--;
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return new NodeIterator();
    }
    
    private class NodeIterator implements Iterator<Map.Entry<K, V>> {
        private Stack<Node> stack;
        private Node current;
    
        public NodeIterator() {
            stack = new Stack<>();
            current = root;
        }
    
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }
    
        public Map.Entry<K, V> next() {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            Node node = stack.pop();
            current = node.right;
            return new AbstractMap.SimpleEntry<>(node.key, node.val);
        }
    }

    public int size() {
        return size;
    }
}

