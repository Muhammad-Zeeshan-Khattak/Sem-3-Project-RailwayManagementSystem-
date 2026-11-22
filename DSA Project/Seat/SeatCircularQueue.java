package Seat;

public class SeatCircularQueue {

    private Seat[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public SeatCircularQueue(int capacity) {

        this.capacity = capacity;
        queue = new Seat[capacity];

        front = 0;
        rear = -1;
        size = 0;
    }

   //  O(1) --> Time Complexity
    public void enqueue(Seat seat) {

    if (size == capacity) {
        System.out.println("Seat Queue is full!");
        return;
    }

    rear = (rear + 1) % capacity;

    queue[rear] = seat;

    size++;

}

//O(1) --> Time complexity
public Seat dequeue() {

    if (size == 0) {
        System.out.println("No available seats!");
        return null;
    }

    Seat removedSeat = queue[front];

    queue[front] = null;

    front = (front + 1) % capacity;

    size--;

    return removedSeat;
}

public boolean isEmpty() {
    return size == 0;
}

public boolean isFull() {
    return size == capacity;
}

// O(1) --> Time Complexity
public Seat peek() {

    if (isEmpty()) {
        System.out.println("No available seats!");
        return null;
    }

    return queue[front];
}

// Display all the seats  O(n)  --> Time Complexity
public void displayQueue() {

    if (isEmpty()) {
        System.out.println("No available seats!");
        return;
    }

    System.out.println("\n===== AVAILABLE SEATS =====");

    for (int i = 0; i < size; i++) {

        int index = (front + i) % capacity;

        queue[index].displaySeat();

        System.out.println("--------------------");
    }
}


public int getSize() {
    return size;
}

public int getCapacity() {
    return capacity;
}

// O(1) --> Time Complexity
  public Seat getSeatAt(int index) {

    if (index < 0 || index >= size) {
        return null;
    }

    int actualIndex = (front + index) % capacity;

    return queue[actualIndex];
}


public boolean deleteSeat(int seatId) {

    int index = -1;

    for (int i = 0; i < size; i++) {

        int actualIndex = (front + i) % capacity;

        if (queue[actualIndex].getSeatId() == seatId) {
            index = i;
            break;
        }
    }

    if (index == -1) {
        return false;
    }

    for (int i = index; i < size - 1; i++) {

        int current = (front + i) % capacity;
        int next = (front + i + 1) % capacity;

        queue[current] = queue[next];
    }

    int lastIndex = (front + size - 1) % capacity;

    queue[lastIndex] = null;
    size--;

    return true;
}

public void clearQueue() {

    while (!isEmpty()) {
        dequeue();
    }
}
    
}