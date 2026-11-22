package Station;

public class Station {

    private int stationId;
    private String stationName;
    private String city;

    public Station(int stationId, String stationName, String city) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.city = city;
    }

    public int getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getCity() {
        return city;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void displayStation() {
        System.out.println("Station ID: " + stationId);
        System.out.println("Station Name: " + stationName);
        System.out.println("City: " + city);
    }
}