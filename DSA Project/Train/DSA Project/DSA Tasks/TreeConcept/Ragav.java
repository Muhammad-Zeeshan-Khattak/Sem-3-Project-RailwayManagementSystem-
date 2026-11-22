
import java.util.*;

// ==========================================
// NODE CLASS
// ==========================================

class Node {

    int data;
    Node left;
    Node right;
   static boolean flag ;

    // Constructor to initialize a node
    Node(int data) {
        this.data = data;
    }

public static int level(Node root){
    if (root == null){
        return 0;
    }

    int l = level(root.left);
    int r = level(root.right);
    return 1 + Math.max(l ,r);


}

public static int level2(Node root){
    if (root == null){
        return 0;
    }

    int l = level(root.left);
    int r = level(root.right);
        if(Math.abs(l-r) > 1){
             flag = false;
        }


    return 1 + Math.max(l ,r);


}




public static int size(Node root){
    if(root == null){
        return 0;
    }
    return 1 +size(root.left) + size(root.right);
}

public static void PerfectTree(Node root){
    if(Math.unsignedPowExact(2, level(root) ) -1 == size(root) ){
        System.out.println("It is a perfect Tree!");
    }

    else{
        System.out.println("Not a perfect tree!");
    }
}

public static boolean balance(Node root){
   if(root == null){
    return true;

   }
   flag = true;
   level2(root);
   return false;

}

}


// ==========================================
// MAIN CLASS
// ==========================================

class Main {

    public static void main(String[] args) {

        // Creating nodes
        Node n1 = new Node(3);
        Node n2 = new Node(11);
        Node n3 = new Node(45);
        Node n4 = new Node(67);
        Node n5 = new Node(45);
        Node n6 = new Node(32);
        Node n7 = new Node(41);
        Node n8 = new Node(31);


        // Connecting nodes to create binary tree

        n1.left = n2;
        n1.right = n3;

        n2.left = n4;
        n2.right = n5;

        n3.left = n6;
        n3.right = n7;

        n4.left = n8;

    Node.PerfectTree(n1);

    System.out.println("Balance Tree: "+Node.balance(n1));

    }
}
