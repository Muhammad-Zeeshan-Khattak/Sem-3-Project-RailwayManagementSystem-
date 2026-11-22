
package Ticket;

public class TicketQueue {

    private Ticket[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    // Constructor
    public TicketQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }

        this.capacity = capacity;
        queue = new Ticket[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Add ticket - O(1)
    public void enqueue(Ticket ticket) {
        if (isFull()) {
            System.out.println("Ticket Queue is full!");
            return;
        }

        rear = (rear + 1) % capacity;
        queue[rear] = ticket;
        size++;
    }

    // Remove ticket - O(1)
    public Ticket dequeue() {
        if (isEmpty()) {
            System.out.println("Ticket Queue is empty!");
            return null;
        }

        Ticket removedTicket = queue[front];
        queue[front] = null;
        front = (front + 1) % capacity;
        size--;

        return removedTicket;
    }

    // View first ticket - O(1)
    public Ticket peek() {
        if (isEmpty()) {
            return null;
        }

        return queue[front];
    }

    // Check empty - O(1)
    public boolean isEmpty() {
        return size == 0;
    }

    // Check full - O(1)
    public boolean isFull() {
        return size == capacity;
    }

    // Get size - O(1)
    public int getSize() {
        return size;
    }

    // Get ticket by index - O(1)
    public Ticket getTicketAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        int actualIndex = (front + index) % capacity;
        return queue[actualIndex];
    }

    // Display all tickets - O(n)
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No tickets available!");
            return;
        }

        System.out.println("\n========== ALL TICKETS ==========");

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            queue[index].displayTicket();
        }
    }

    // Clear queue - O(n)
    public void clearQueue() {
        while (!isEmpty()) {
            dequeue();
        }
    }
}
