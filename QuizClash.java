
class Node {
    int score;
    int height;
    int size;

    Node left, right;

    Node(int score) {
        this.score = score;
        height = 1;
        size = 1;
    }
}

public class QuizClash {

    // Height
    static int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Subtree Size
    static int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

    // Update Height and Size
    static void update(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left),
                                       height(node.right));

            node.size = 1 + size(node.left)
                          + size(node.right);
        }
    }

    // Balance Factor
    static int getBalance(Node node) {
        if (node == null)
            return 0;

        return height(node.left) - height(node.right);
    }

    // Right Rotation
    static Node rightRotate(Node y) {

        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        update(y);
        update(x);

        return x;
    }

    // Left Rotation
    static Node leftRotate(Node x) {

        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        update(x);
        update(y);

        return y;
    }

    // Insert
    static Node insert(Node root, int score) {

        if (root == null)
            return new Node(score);

        if (score < root.score)
            root.left = insert(root.left, score);

        else if (score > root.score)
            root.right = insert(root.right, score);

        else
            return root;

        update(root);

        int balance = getBalance(root);

        // LL
        if (balance > 1 && score < root.left.score)
            return rightRotate(root);

        // RR
        if (balance < -1 && score > root.right.score)
            return leftRotate(root);

        // LR
        if (balance > 1 && score > root.left.score) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RL
        if (balance < -1 && score < root.right.score) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Minimum Value Node
    static Node minValueNode(Node node) {

        Node current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    // Delete
    static Node deleteNode(Node root, int score) {

        if (root == null)
            return root;

        if (score < root.score)
            root.left = deleteNode(root.left, score);

        else if (score > root.score)
            root.right = deleteNode(root.right, score);

        else {

            // One child or no child
            if (root.left == null || root.right == null) {

                Node temp;

                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            }

            // Two children
            else {

                Node temp = minValueNode(root.right);

                root.score = temp.score;

                root.right = deleteNode(root.right,
                                        temp.score);
            }
        }

        if (root == null)
            return root;

        update(root);

        int balance = getBalance(root);

        // LL
        if (balance > 1 &&
            getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR
        if (balance > 1 &&
            getBalance(root.left) < 0) {

            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR
        if (balance < -1 &&
            getBalance(root.right) <= 0)
            return leftRotate(root);

        // RL
        if (balance < -1 &&
            getBalance(root.right) > 0) {

            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Rank Query
    static int getRank(Node root, int score) {

        if (root == null)
            return 0;

        if (score < root.score) {
            return getRank(root.left, score);
        }

        else if (score > root.score) {

            return size(root.left) + 1
                   + getRank(root.right, score);
        }

        else {
            return size(root.left) + 1;
        }
    }

    // Top K Players
    static void topK(Node root, int[] k) {

        if (root == null || k[0] <= 0)
            return;

        topK(root.right, k);

        if (k[0] > 0) {
            System.out.print(root.score + " ");
            k[0]--;
        }

        topK(root.left, k);
    }

    // Inorder Traversal
    static void inorder(Node root) {

        if (root == null)
            return;

        inorder(root.left);

        System.out.println(root.score
                           + "[" + root.size + "]");

        inorder(root.right);
    }

    public static void main(String[] args) {

        Node root = null;

        int[] scores = {
            820, 540, 910, 770, 880,
            460, 990, 600, 730, 950, 510
        };

        // Insert Scores
        for (int score : scores) {
            root = insert(root, score);
        }

        System.out.println("Initial AVL Tree:");
        inorder(root);

        // Update 540 → 815
        root = deleteNode(root, 540);
        root = insert(root, 815);

        // Update 910 → 685
        root = deleteNode(root, 910);
        root = insert(root, 685);

        System.out.println("\nAfter Updates:");
        inorder(root);

        // Rank Query
        int score = 730;

        System.out.println("\nRank of "
                           + score + " = "
                           + getRank(root, score));

        // Top K Players
        int[] k = {5};

        System.out.println("\nTop 5 Scores:");
        topK(root, k);
    }
}