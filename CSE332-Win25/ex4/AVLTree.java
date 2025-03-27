public class AVLTree  <K extends Comparable<K>, V> extends BinarySearchTree<K,V> {

    public AVLTree(){
        super();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        V old = root == null ? null : find(key);
        if (old == null) {
            size++;
        }

        root = insertHelper(key, value, root);
        root.updateHeight();
        return old;
    }

    private TreeNode<K, V> insertHelper(K key, V value, TreeNode<K, V> node) {
        if (node == null) { // Base case when current root node is null
            return new TreeNode<>(key, value);
        }

        if (key.compareTo(node.key) < 0) { //key smaller than root
            node.left = insertHelper(key, value, node.left); //x = change(x)
        } else if (key.compareTo(node.key) > 0){ //key larget than root
            node.right = insertHelper(key, value, node.right);
        } else {
            node.value = value;
        }
        node.updateHeight();

        return balance(node);
    }

    private int getBalance(TreeNode<K, V> node) {
        if (node == null)
            return 0;
        int leftHeight = (node.left != null) ? node.left.height : -1;
        int rightHeight = (node.right != null) ? node.right.height : -1;
        return leftHeight - rightHeight;// 0 if null, 1 is left too much, -1 if right too much
    }

    private TreeNode<K, V> balance(TreeNode<K, V> node) {
        int balance = getBalance(node);

        if (balance > 1) { // left heavy
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left); // correct Right child of left subtree
            }
            return rotateRight(node); // correct left child of left subtree
        }

        if (balance < -1) { //right heavy
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right); //correct left child of right subtree
            }
            return rotateLeft(node); // correct right child of right subtree
        }

        return node;
    }

    private TreeNode<K, V> rotateLeft(TreeNode<K, V> node) {
        TreeNode<K, V> newRoot = node.right;
        TreeNode<K, V> leftSub = newRoot.left;

        newRoot.left = node;
        node.right = leftSub;

        node.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    private TreeNode<K, V> rotateRight(TreeNode<K, V> node) {
        TreeNode<K, V> newRoot = node.left;
        TreeNode<K, V> rightSub = newRoot.right;

        newRoot.right = node;
        node.left = rightSub;

        node.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }
}

