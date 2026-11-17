
package train;

public class TrainNode {

    Train data;
    TrainNode left;
    TrainNode right;

    public TrainNode(Train data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
