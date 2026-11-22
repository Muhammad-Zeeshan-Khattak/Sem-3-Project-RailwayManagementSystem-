
package Route;

public class Route {

    private int routeId;
    private String routeName;

    public Route(int routeId, String routeName) {
        this.routeId = routeId;
        this.routeName = routeName;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void displayRoute() {
        System.out.println("Route ID: " + routeId);
        System.out.println("Route Name: " + routeName);
    }
}
