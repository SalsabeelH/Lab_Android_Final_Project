package birzeit.edu.labandroidfinalproject.Models;

import java.util.List;

public class Continent {

    private String continentName;
    private List<Destination> destinations;

    public Continent() {
    }

    public Continent(String continentName, List<Destination> destinations) {
        this.continentName = continentName;
        this.destinations = destinations;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "continentName='" + continentName + '\'' +
                ", destinations=" + destinations +
                '}';
    }
}
