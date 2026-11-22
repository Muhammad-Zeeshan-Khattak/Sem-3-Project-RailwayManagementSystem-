package Schedule;

import java.time.LocalDateTime;

public class ScheduleMinHeap {

    private Schedule[] heap;
    private int size;
    private int capacity;

    // Constructor
    public ScheduleMinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        heap = new Schedule[capacity];
    }

    // Return current number of schedules
public int getSize() {
    return size;
}

// Return schedule at a particular index
public Schedule getScheduleAt(int index) {

    if (index < 0 || index >= size) {
        return null;
    }

    return heap[index];
}


// Clear all schedules from the heap
public void clearHeap() {

    for (int i = 0; i < size; i++) {
        heap[i] = null;
    }

    size = 0;
}
    // Compare two schedules by date and departure time
    private boolean isEarlier(Schedule s1, Schedule s2) {

        LocalDateTime dateTime1 = LocalDateTime.of(
            s1.getTravelDate(),
            s1.getDepartureTime()
        );

        LocalDateTime dateTime2 = LocalDateTime.of(
            s2.getTravelDate(),
            s2.getDepartureTime()
        );

        return dateTime1.isBefore(dateTime2);
    }


    // Insert a new schedule  O(n) --> Time Complexity
    public void insertSchedule(Schedule schedule) {

        if (size == capacity) {
            System.out.println("Heap is full!");
            return;
        }

        // Insert schedule at the end
        heap[size] = schedule;

        // Restore Min-Heap property
        heapifyUp(size);

        size++;
        
    }

    // Restore Min-Heap property from bottom to top   O(n) --> Time Complexity
    private void heapifyUp(int index) {

        while (index > 0) {

            int parent = (index - 1) / 2;

            // Check whether child has higher priority
            if (isEarlier(heap[index], heap[parent])) {

                // Swap child and parent
                Schedule temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;

                // Move to parent position
                index = parent;

            } else {
                break;
            }
        }
    }


    // Return the earliest schedule without removing it  O(n)--> Time Complexity
    public Schedule getEarliestSchedule() {

        if (size == 0) {
            System.out.println("No schedules available!");
            return null;
        }

        return heap[0];
    }


    // Remove the earliest schedule O(n) --> Time Complexity
    public Schedule removeEarliestSchedule() {

        if (size == 0) {
            System.out.println("No schedules available!");
            return null;
        }

        // Store the root schedule
        Schedule earliest = heap[0];

        // Move the last element to the root
        heap[0] = heap[size - 1];

        // Remove the last array element
        heap[size - 1] = null;

        size--;

        // Restore Min-Heap property
        if (size > 0) {
            heapifyDown(0);
        }

        return earliest;
    }


    // Restore Min-Heap property from top to bottom O(n) --> Time Complexity
    private void heapifyDown(int index) {

        while (true) {

            int leftChild = (2 * index) + 1;
            int rightChild = (2 * index) + 2;

            int smallest = index;

            // Check left child
            if (leftChild < size &&
                isEarlier(heap[leftChild], heap[smallest])) {

                smallest = leftChild;
            }

            // Check right child
            if (rightChild < size &&
                isEarlier(heap[rightChild], heap[smallest])) {

                smallest = rightChild;
            }

            // Stop if parent is already the smallest
            if (smallest == index) {
                break;
            }

            // Swap parent with smallest child
            Schedule temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;

            // Continue from child position
            index = smallest;
        }
    }


    



}