
package train;

public class Train {

    private int trainId;
    private String trainName;
    private String trainType;

    public Train(int trainId, String trainName, String trainType) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.trainType = trainType;
    }

    public void setTrainName(String trainName) {
       this.trainName = trainName;
    }

   public void setTrainType(String trainType) {
       this.trainType = trainType;
    }

    public int getTrainId() {
        return trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainType() {
        return trainType;
    }

    public void displayTrain() {
        System.out.println("Train ID: " + trainId);
        System.out.println("Train Name: " + trainName);
        System.out.println("Train Type: " + trainType);
        System.out.println("------------------------");
    }
}
