public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given BTree (2-3-4) TREE. */
    public RedBlackTree(BTree<T> tree) {
        Node<T> btreeRoot = tree.root;
        root = buildRedBlackTree(btreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3-4 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {

        // HINT: Having a case for each number of items in r might help
        if (r == null) {
            return null;
        }

        RBTreeNode<T> rootNode = null;
        if (r.getItemCount() == 1){
            rootNode = new RBTreeNode(true, r.getItemAt(0));
            rootNode.left = buildRedBlackTree(r.getChildAt(0));
            rootNode.right = buildRedBlackTree(r.getChildAt(1));
        } else if (r.getItemCount() == 2) {
            rootNode = new RBTreeNode<> (true, r.getItemAt(0));
            rootNode.right = new RBTreeNode<> (false, r.getItemAt(1));
            rootNode.left = buildRedBlackTree(r.getChildAt(0));
            rootNode.right.left = buildRedBlackTree(r.getChildAt(1));
            rootNode.right.right = buildRedBlackTree(r.getChildAt(2));
        } else if (r.getItemCount() == 3) {
            rootNode = new RBTreeNode<> (true, r.getItemAt(1));
            rootNode.left = new RBTreeNode<> (false, r.getItemAt(0));
            rootNode.right = new RBTreeNode<> (false, r.getItemAt(2));
            rootNode.left.left = buildRedBlackTree(r.getChildAt(0));
            rootNode.left.right = buildRedBlackTree(r.getChildAt(1));
            rootNode.right.left = buildRedBlackTree(r.getChildAt(2));
            rootNode.right.right = buildRedBlackTree(r.getChildAt(3));
        }
        return rootNode;
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        if (node.left != null) {
            node.left.isBlack = !node.left.isBlack;
        }
        if (node.right != null) {
            node.right.isBlack = !node.right.isBlack;
        }
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {

        if (node.left == null) {
            return null;
        }
        RBTreeNode<T> temp = new RBTreeNode<>(node.isBlack, node.item, node.left, node.right);
        if (findParent(node, root) != null) {
            RBTreeNode<T> parent = findParent(node, root);
            if (parent.left == node) {
                parent.left = temp.left;
            } else if (parent.right == node) {
                parent.right = temp.left;
            }
        }
        node.left = temp.left.right;
        temp.left.right = node;
        temp.left.isBlack = temp.isBlack;
        node.isBlack = false;
        return temp.left;
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        if (node.right == null) {
            return null;
        }
        RBTreeNode<T> temp = new RBTreeNode<>(node.isBlack, node.item, node.left, node.right);
        if (findParent(node, root) != null) {
            RBTreeNode<T> parent = findParent(node, root);
            if (parent.left == node) {
                parent.left = temp.right;
            } else if (parent.right == node) {
                parent.right = temp.right;
            }
        }
        node.right = temp.right.left;
        temp.right.left = node;
        temp.right.isBlack = temp.isBlack;
        node.isBlack = false;

        return temp.right;
    }

    void insert(T item) {

        if (root == null) {
            root = new RBTreeNode<>(true, item);
            return;
        }
        insert(root, item, null);
        root.isBlack = true;
    }

    private RBTreeNode<T> insert(RBTreeNode<T> node, T item, RBTreeNode<T> parent) {
    	// Optional helper method
    	// HINT: Remember to handle each of the cases from the spec
        if (node == null) {
            node = new RBTreeNode<>(false, item);

            if (parent.isBlack && parent.right == null) {
                boolean oldcolor = parent.isBlack;
                RBTreeNode<T> newHead = rotateRight(parent);
                newHead.isBlack = oldcolor;
                newHead.right.isBlack = false;
                return node;
            } else if (parent.isBlack && parent.left == null) {
                return node;
            }

            if (!parent.isBlack && parent.left == node) {
                rotateRight(parent);
            }

            if (!parent.isBlack && parent.right == node) {
                rotateLeft(findParent(parent, root));
            }

            if (parent.isBlack && !node.isBlack) {
                flipColors(parent);
            }

            return node;
        }

        if (node.item.compareTo(item) == 0) {
            return node;
        } else if (node.item.compareTo(item) > 0) {
            return insert(node.left, item, node);
        } else {
            return insert(node.right, item, node);
        }
    }

    private RBTreeNode<T> findParent(RBTreeNode<T> node, RBTreeNode<T> psudoP) {
        if (psudoP == null) {
            return null;
        }
        if (psudoP.left == node) {
            return psudoP;
        } else if (psudoP.right ==node) {
            return psudoP;
        }
        RBTreeNode<T> a = findParent(node, psudoP.left);
        RBTreeNode<T> b = findParent(node, psudoP.right);
        if (a == null) {
            return b;
        } else {
            return a;
        }
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Main method to help test constructor. Feel free to modify */
    public static void main(String[] args) {
        BTree<Integer> bTree = new BTree<>();
        bTree.root = new BTree.TwoThreeFourNode<>(3, 4);
        RedBlackTree<Integer> rbTree = new RedBlackTree<>(bTree);
        System.out.println((rbTree.root != null));
        System.out.println((rbTree.root.left == null));
        System.out.println((rbTree.root.right != null));
        System.out.println((rbTree.root.isBlack));
        System.out.println((!rbTree.root.right.isBlack));
        System.out.println(3 == rbTree.root.item);
        System.out.println(4 == rbTree.root.right.item);
    }

}
