
class Node {
    int key;
    Node left;
    Node right;

    Node(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }
}

class Tree {

    Node root = null;

    // =================================
    // TASK 1: INSERTION
    // =================================

    static Node insert(Node root, int key) {

        if (root == null) {
            return new Node(key);
        }

        if (key < root.key) {
            root.left = insert(root.left, key);
        }

        else if (key > root.key) {
            root.right = insert(root.right, key);
        }

        // Ignore duplicates

        return root;
    }


    // =================================
    // TASK 1: SEARCHING
    // =================================

    static boolean search(Node root, int key) {

        if (root == null) {
            return false;
        }

        if (root.key == key) {
            return true;
        }

        else if (key < root.key) {
            return search(root.left, key);
        }

        else {
            return search(root.right, key);
        }
    }


    // Optional: Count search comparisons

    static int searchComparisons(Node root, int key) {

        if (root == null) {
            return 0;
        }

        // One comparison with current node
        if (root.key == key) {
            return 1;
        }

        if (key < root.key) {
            return 1 + searchComparisons(root.left, key);
        }

        else {
            return 1 + searchComparisons(root.right, key);
        }
    }


    // =================================
    // TASK 1: INORDER
    // Left -> Root -> Right
    // =================================

    static void inorder(Node root) {

        if (root == null) {
            return;
        }

        inorder(root.left);

        System.out.print(root.key + " ");

        inorder(root.right);
    }


    // =================================
    // TASK 1: PREORDER
    // Root -> Left -> Right
    // =================================

    static void preorder(Node root) {

        if (root == null) {
            return;
        }

        System.out.print(root.key + " ");

        preorder(root.left);

        preorder(root.right);
    }


    // =================================
    // TASK 1: POSTORDER
    // Left -> Right -> Root
    // =================================

    static void postorder(Node root) {

        if (root == null) {
            return;
        }

        postorder(root.left);

        postorder(root.right);

        System.out.print(root.key + " ");
    }


    // =================================
    // TASK 2: COUNT TOTAL NODES
    // =================================

    static int countNodes(Node root) {

        if (root == null) {
            return 0;
        }

        return 1 + countNodes(root.left)
                 + countNodes(root.right);
    }


    // =================================
    // TASK 2: COUNT LEAF NODES
    // =================================

    static int countLeaves(Node root) {

        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        return countLeaves(root.left)
             + countLeaves(root.right);
    }


    // =================================
    // TASK 2: HEIGHT
    // =================================

    static int height(Node root) {

        if (root == null) {
            return -1;
        }

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        return 1 + Math.max(leftHeight, rightHeight);
    }


    // =================================
    // TASK 2: DEPTH OF A NODE
    // =================================

    static int depthOf(Node root, int key, int depth) {

        if (root == null) {
            return -1;
        }

        if (root.key == key) {
            return depth;
        }

        if (key < root.key) {
            return depthOf(root.left, key, depth + 1);
        }

        else {
            return depthOf(root.right, key, depth + 1);
        }
    }


    // =================================
    // TASK 2: SUM AT A PARTICULAR LEVEL
    // =================================

    static int sumAtLevel(Node root, int level) {

        if (root == null || level < 0) {
            return 0;
        }

        if (level == 0) {
            return root.key;
        }

        return sumAtLevel(root.left, level - 1)
             + sumAtLevel(root.right, level - 1);
    }


    // =================================
    // EXTRA: FIND MINIMUM
    // =================================

    static Node findMin(Node root) {

        if (root == null) {
            return null;
        }

        while (root.left != null) {
            root = root.left;
        }

        return root;
    }


    // =================================
    // EXTRA: FIND MAXIMUM
    // =================================

    static Node findMax(Node root) {

        if (root == null) {
            return null;
        }

        while (root.right != null) {
            root = root.right;
        }

        return root;
    }


    // =================================
    // TASK 3: DELETE NODE
    // =================================

    static Node delete(Node root, int key) {

        // Node not found
        if (root == null) {
            return null;
        }

        // Search in left subtree
        if (key < root.key) {
            root.left = delete(root.left, key);
        }

        // Search in right subtree
        else if (key > root.key) {
            root.right = delete(root.right, key);
        }

        // Node found
        else {

            // CASE A and B:
            // No left child (leaf or one right child)
            if (root.left == null) {
                return root.right;
            }

            // CASE B:
            // Only left child
            if (root.right == null) {
                return root.left;
            }

            // CASE C:
            // Node has two children

            // Find inorder successor
            Node successor = findMin(root.right);

            // Replace current node's value
            root.key = successor.key;

            // Delete original successor
            root.right = delete(root.right, successor.key);
        }

        return root;
    }


    // =================================
    // VERIFY BST PROPERTY AFTER DELETION
    // =================================

    static boolean isBST(Node root, long min, long max) {

        if (root == null) {
            return true;
        }

        if (root.key <= min || root.key >= max) {
            return false;
        }

        return isBST(root.left, min, root.key)
            && isBST(root.right, root.key, max);
    }


    // =================================
    // MAIN METHOD
    // =================================

    public static void main(String[] args) {

        Tree t = new Tree();

        // Insert values
        t.root = insert(t.root, 45);
        t.root = insert(t.root, 25);
        t.root = insert(t.root, 65);
        t.root = insert(t.root, 15);
        t.root = insert(t.root, 35);
        t.root = insert(t.root, 55);
        t.root = insert(t.root, 75);
        t.root = insert(t.root, 35);  // Duplicate ignored


        // TASK 1: TRAVERSALS

        System.out.println("===== TASK 1 =====");

        System.out.print("Inorder: ");
        inorder(t.root);

        System.out.print("\nPreorder: ");
        preorder(t.root);

        System.out.print("\nPostorder: ");
        postorder(t.root);


        // Searching
        System.out.println("\n\nSearch 55: " + search(t.root, 55));
        System.out.println("Search 99: " + search(t.root, 99));

        System.out.println("Comparisons for 55: "
                + searchComparisons(t.root, 55));


        // TASK 2: ADVANCED OPERATIONS

        System.out.println("\n\n===== TASK 2 =====");

        System.out.println("Total nodes: " + countNodes(t.root));

        System.out.println("Leaf nodes: " + countLeaves(t.root));

        System.out.println("Height: " + height(t.root));

        System.out.println("Depth of 35: "
                + depthOf(t.root, 35, 0));

        System.out.println("Depth of 99: "
                + depthOf(t.root, 99, 0));

        System.out.println("Sum at level 0: "
                + sumAtLevel(t.root, 0));

        System.out.println("Sum at level 1: "
                + sumAtLevel(t.root, 1));

        System.out.println("Sum at level 2: "
                + sumAtLevel(t.root, 2));


        // Minimum and maximum
        System.out.println("Minimum: " + findMin(t.root).key);

        System.out.println("Maximum: " + findMax(t.root).key);


        // TASK 3: DELETION

        System.out.println("\n\n===== TASK 3 =====");


        // CASE A: Delete leaf node 15

        System.out.println("\nCase A: Delete leaf 15");

        t.root = delete(t.root, 15);

        System.out.print("Inorder: ");
        inorder(t.root);

        System.out.println("\nBST valid: "
                + isBST(t.root, Long.MIN_VALUE, Long.MAX_VALUE));


        // CASE C: Delete node 45 (two children)

        System.out.println("\n\nCase C: Delete node 45");

        t.root = delete(t.root, 45);

        System.out.print("Inorder: ");
        inorder(t.root);

        System.out.println("\nBST valid: "
                + isBST(t.root, Long.MIN_VALUE, Long.MAX_VALUE));


        // CASE B: Separate tree for one-child deletion

        System.out.println("\n\nCase B: Delete node with one child");

        Tree t2 = new Tree();

        t2.root = insert(t2.root, 50);
        t2.root = insert(t2.root, 30);
        t2.root = insert(t2.root, 20);

        System.out.print("Before deletion: ");
        inorder(t2.root);

        t2.root = delete(t2.root, 30);

        System.out.print("\nAfter deleting 30: ");
        inorder(t2.root);

        System.out.println("\nBST valid: "
                + isBST(t2.root, Long.MIN_VALUE, Long.MAX_VALUE));
    }
}
