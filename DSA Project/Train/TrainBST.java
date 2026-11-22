
package train;

public class TrainBST {

    private TrainNode root;

    public TrainBST() {
        root = null;
    }

    // Insert a train into BST
    public void insert(Train train) {
        root = insertNode(root, train);
    }


    private TrainNode insertNode(TrainNode current, Train train) {

        if (current == null) {
            return new TrainNode(train);
        }

        if (train.getTrainId() < current.data.getTrainId()) {
            current.left = insertNode(current.left, train);
        }
        else if (train.getTrainId() > current.data.getTrainId()) {
            current.right = insertNode(current.right, train);
        }

        return current;
    }

  public void delete(int trainId) {
      root = deleteNode(root, trainId);
}


// Method to delete Train data from Database
private TrainNode deleteNode(TrainNode current, int trainId) {

    if (current == null) {
        return null;
    }

    if (trainId < current.data.getTrainId()) {
        current.left = deleteNode(current.left, trainId);
    } 
    else if (trainId > current.data.getTrainId()) {
        current.right = deleteNode(current.right, trainId);
    } 
    else {

        // Case 1: No child
        if (current.left == null && current.right == null) {
            return null;
        }

        // Case 2: One child
        if (current.left == null) {
            return current.right;
        }

        if (current.right == null) {
            return current.left;
        }

        // Case 3: Two children
        TrainNode successor = findMin(current.right);

        current.data = successor.data;

        current.right = deleteNode(
            current.right,
            successor.data.getTrainId()
        );
    }

    return current;
}



private TrainNode findMin(TrainNode current) {

    while (current.left != null) {
        current = current.left;
    }

    return current;
}

    // Display trains in ascending order of ID
    public void displayAll() {
        if (root == null) {
            System.out.println("No trains available.");
            return;
        }

        inorder(root);
    }

    private void inorder(TrainNode current) {

        if (current != null) {
            inorder(current.left);
            current.data.displayTrain();
            inorder(current.right);
        }
    }

    public Train search(int trainId) {
        TrainNode current = root;

        while (current != null) {
          if (trainId == current.data.getTrainId()) {
            return current.data;
        }
          if (trainId < current.data.getTrainId()) {
            current = current.left;
        } 
          else {
            current = current.right;
        }
    }

    return null;
}

public boolean contains(int trainId) {
    TrainNode current = root;

    while (current != null) {
        if (trainId == current.data.getTrainId()) {
            return true;
        }
        else if (trainId < current.data.getTrainId()) {
            current = current.left;
        } 
        else {
            current = current.right;
        }
    }

    return false;
}
}
