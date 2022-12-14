package pl.edu.pw.ee;

import static pl.edu.pw.ee.Color.BLACK;
import static pl.edu.pw.ee.Color.RED;

public class RedBlackTree<K extends Comparable<K>, V> {

    private Node<K, V> root;
    private int count = 0;

    public V get(K key) {
        validateKey(key);
        Node<K, V> node = root;

        V string = null;

        while (node != null) {

            if (shouldCheckOnTheLeft(key, node)) {
                node = node.getLeft();

            } else if (shouldCheckOnTheRight(key, node)) {
                node = node.getRight();

            } else {
                string = node.getValue();
                break;
            }
        }
        return string;
    }

    public void put(K key, V value) {
        count = 0;
        validateParams(key, value);
        root = put(root, key, value);
        root.setColor(BLACK);
    }

    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
    }

    private boolean shouldCheckOnTheLeft(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    private boolean shouldCheckOnTheRight(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    private void validateParams(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Input params (key, value) cannot be null.");
        }
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        count++;

        if (node == null) {
            return new Node(key, value);
        }

        if (isKeyBiggerThanNode(key, node)) {
            putOnTheRight(node, key, value);

        } else if (isKeySmallerThanNode(key, node)) {
            putOnTheLeft(node, key, value);

        } else {
            node.setValue(value);
        }

        node = reorganizeTree(node);

        return node;
    }

    private boolean isKeyBiggerThanNode(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    private void putOnTheRight(Node<K, V> node, K key, V value) {
        Node<K, V> rightChild = put(node.getRight(), key, value);
        node.setRight(rightChild);
    }

    private boolean isKeySmallerThanNode(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    private void putOnTheLeft(Node<K, V> node, K key, V value) {
        Node<K, V> leftChild = put(node.getLeft(), key, value);
        node.setLeft(leftChild);
    }

    private Node<K, V> reorganizeTree(Node<K, V> node) {
        node = rotateLeftIfNeeded(node);
        node = rotateRightIfNeeded(node);
        changeColorsIfNeeded(node);

        return node;
    }

    private Node<K, V> rotateLeftIfNeeded(Node<K, V> node) {
        if (isBlack(node.getLeft()) && isRed(node.getRight())) {
            node = rotateLeft(node);
        }
        return node;
    }

    private Node<K, V> rotateLeft(Node<K, V> node) {
        Node<K, V> head = node.getRight();
        node.setRight(head.getLeft());
        head.setLeft(node);
        head.setColor(node.getColor());
        node.setColor(RED);

        return head;
    }

    private Node<K, V> rotateRightIfNeeded(Node<K, V> node) {
        if (isRed(node.getLeft()) && isRed(node.getLeft().getLeft())) {
            node = rotateRight(node);
        }
        return node;
    }

    private Node<K, V> rotateRight(Node<K, V> node) {
        Node<K, V> head = node.getLeft();
        node.setLeft(head.getRight());
        head.setRight(node);
        head.setColor(node.getColor());
        node.setColor(RED);

        return head;
    }

    private void changeColorsIfNeeded(Node<K, V> node) {
        if (isRed(node.getLeft()) && isRed(node.getRight())) {
            changeColors(node);
        }
    }

    private void changeColors(Node<K, V> node) {
        node.setColor(node.isRed() ? BLACK : RED);
        node.getLeft().setColor(node.getLeft().isRed() ? BLACK : RED);
        node.getRight().setColor(node.getRight().isRed() ? BLACK : RED);
    }

    private boolean isBlack(Node<K, V> node) {
        return !isRed(node);
    }

    private boolean isRed(Node<K, V> node) {
        return node == null
                ? false
                : node.isRed();
    }

    int getCount() {
        return count;
    }


    public void deleteMax() {
        if (root == null) {
            return;
        }
        deleteMaxRecursion(root);
        if (root != null) {
            root.setColor(BLACK);
        }
    }

    public Node<K, V> deleteMaxRecursion(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        if (isRed(node.getLeft()) == true) {
            node = rotateRight(node);
        }
        if (node.getRight() == null) {
            return null;
        }
        if (!isRed(node.getRight()) && !isRed(node.getRight().getLeft())) {
            changeColors(node);
            if (isRed(node.getLeft().getLeft())) {
                node = rotateRight(node);
                changeColors(node);
            }
        }
        node.setRight(deleteMaxRecursion(node.getRight()));
        return reorganizeTree(node);
    }

    public String getPreOrder() {
        if (root == null) {
            return null;
        }
        StringBuilder string = new StringBuilder();
        preOrder(root, string);
        String result = string.toString();
        return result;
    }

    private StringBuilder preOrder(Node<K, V> node, StringBuilder string) {
        if (node == null) {
            return string;
        }
        string = string.append(node.getKey() + ":" + node.getValue() + " ");
        preOrder(node.getLeft(), string);
        preOrder(node.getRight(), string);
        return string;
    }

    public String getInOrder() {
        if (root == null) {
            return null;
        }
        StringBuilder string = new StringBuilder();
        inOrder(root, string);
        String result = string.toString();
        return result;
    }

    private StringBuilder inOrder(Node<K, V> node, StringBuilder string) {
        if (node == null) {
            return string;
        }
        inOrder(node.getLeft(), string);
        string = string.append(node.getKey() + ":" + node.getValue() + " ");
        inOrder(node.getRight(), string);
        return string;
    }

    public String getPostOrder() {
        if (root == null) {
            return null;
        }
        StringBuilder string = new StringBuilder();
        postOrder(root, string);
        String result = string.toString();
        return result;
    }

    private StringBuilder postOrder(Node<K, V> node, StringBuilder string) {
        if (node == null) {
            return string;
        }
        postOrder(node.getLeft(), string);
        postOrder(node.getRight(), string);
        string = string.append(node.getKey() + ":" + node.getValue() + " ");
        return string;
    }
}
