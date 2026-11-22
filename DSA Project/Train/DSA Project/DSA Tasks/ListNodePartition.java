 // Question is: A list is given and we just try to divide the list such that we getNodes those 1st which is less than value ..
 
 class Node {
 int data;
 Node next;

 Node(int data){
    this.data = data;
    this.next = null;

 }
    
}

class List{
    Node head = null;
    
    void insert(int data){
        Node New = new Node(data);

        if(head == null){
           head = New ;
           return;

        }

        New.next = head;
        head = New;
    }

    Node partition(int value){
        
        Node dummy1 = new Node(-1);
        Node dummy2 = new Node(-1);

        Node d1 = dummy1;   //  List which is less than value
        Node d2 = dummy2;   // List which is greater that value

        Node t = head;

        while(t != null){

            if(t.data < value){
                d1.next = t;
                d1 = d1.next;

            }

            else{
                d2.next = t;
                d2 = d2.next;

            }
            t = t.next;    
        }

        d1.next = dummy2.next;
        d2.next = null;

        return dummy1.next;


    }

      void display(Node head){

        Node temp = head;

        while(temp != null){

            System.out.print(temp.data + " ");
            temp = temp.next;

        }
        System.out.println();
    }
}


 class Main{
    public static void main(String[] args) {
        

        List m1 = new List();

        m1.insert(2);
        m1.insert(10);
        m1.insert(4);
        m1.insert(7);
        m1.insert(12);
        m1.insert(22);
        m1.insert(13);
        m1.insert(8);

        Node n = m1.partition(9);
        m1.display(n);

    }
}