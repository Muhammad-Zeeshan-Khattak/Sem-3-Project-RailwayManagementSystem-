 // Question is: A list is given and we just try to Sort it by changing nodes links
 
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

    Node mergeSort(Node head){
        
    if(head.next == null) return head;

        Node slow = head;
        Node fast = head;

        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next;

        } 

        Node head2 = slow.next;
        slow.next = null;

      head =  mergeSort(head);     // First list became sorted upon recursive calls 
      head2 =   mergeSort(head2);   // 2nd list became also sorted upon recursive calls

        return merge(head,head2);    // here we just merge both lists
    }

     Node merge(Node head1,Node head2){

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

        m1.insert(2);
        m1.insert(10);
        m1.insert(4);
        m1.insert(7);
        m1.insert(12);
        m1.insert(22);
        m1.insert(13);
        m1.insert(8);
     
       Node n = m1.mergeSort(m1.head);
       m1.display(n);


    }
}
