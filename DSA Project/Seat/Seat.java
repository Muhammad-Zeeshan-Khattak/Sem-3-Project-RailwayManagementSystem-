package Seat;

public class Seat {

    private int seatId;
    private int seatNo;
    private String seatClass;
    private int trainId;

    public Seat(int seatId, int seatNo,
                String seatClass, int trainId) {

        this.seatId = seatId;
        this.seatNo = seatNo;
        this.seatClass = seatClass;
        this.trainId = trainId;
    }


    public void setSeatNo(int seatNo) {
    this.seatNo = seatNo;
}

public void setSeatClass(String seatClass) {
    this.seatClass = seatClass;
}
    public int getSeatId() {
        return seatId;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public int getTrainId() {
        return trainId;
    }

    public void displaySeat() {
        System.out.println("Seat ID: " + seatId);
        System.out.println("Seat Number: " + seatNo);
        System.out.println("Class: " + seatClass);
        System.out.println("Train ID: " + trainId);
    }
}