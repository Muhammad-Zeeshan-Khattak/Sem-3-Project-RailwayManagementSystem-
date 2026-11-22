 // Question is: Two List were given and we have to merge those sorted list simply
 
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

    static Node merge(Node head1,Node head2){
        Node i = head1;
        Node j = head2;
        Node dummy = new Node(-1);
        Node k = dummy;

        while(i != null && j != null){

            if(i.data < j.data){
                k.next = i;
                i = i.next;

            }
            else{
                k.next = j;
                j = j.next;

            }

            k  = k.next;
        }

        if(i == null){
            k.next = j;
        }

        else{
             k.next = i;
        }

        return dummy.next;


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
        List m2 = new List();
        List m3 = new List();

        m1.insert(80);
        m1.insert(60);
        m1.insert(50);
        m1.insert(30);
        m1.insert(10);

        m2.insert(200);
        m2.insert(55);
        m2.insert(23);       
        m2.insert(19);
        m2.insert(1);

    
        Node n = m3.merge(m1.head,m2.head);
        m3.display(n);



    }
}
