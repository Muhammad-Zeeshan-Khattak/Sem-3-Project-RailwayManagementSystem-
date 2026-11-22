
class Node{
    int val;
    Node left;
    Node right;

    Node(int val){
        this.val = val;

    }


}

class Tree{

    Node root;

    public static Node insert(Node root,int val){

        if(root == null){
            return new Node(val);

        }

       else if(root.val < val){
            root.right = insert(root.right,val);

        }

       else{

        root.left = insert(root.left,val);
       }
      
       return root;
    }

    public static void inOrder(Node root){
        if(root == null){
            return;
        }

        inOrder(root.left);

        System.out.print(root.val + " ");

        inOrder(root.right);


    }
    public static void preOrder(Node root){
        if(root == null){
            return;
        }


        System.out.print(root.val + " ");

        inOrder(root.left);
        inOrder(root.right);


    }
    public static void postOrder(Node root){
        if(root == null){
            return;
        }

        inOrder(root.left);
        inOrder(root.right);
        System.out.print(root.val + " ");



    }

}

class Main{
    public static void main(String[] args){
   
        Tree t = new Tree();

      t.root = Tree.insert(t.root,12);
       Tree.insert(t.root,30);
       Tree.insert(t.root,67);
       Tree.insert(t.root,78);
       Tree.insert(t.root,11);
       Tree.insert(t.root,89);
       Tree.insert(t.root,66);

System.out.println("*** InOrder Print ***");
Tree.inOrder(t.root);


System.out.println("*** PreOrder Print ***");
Tree.preOrder(t.root);


System.out.println("*** PostOrder Print ***");
Tree.postOrder(t.root);



    }
}
