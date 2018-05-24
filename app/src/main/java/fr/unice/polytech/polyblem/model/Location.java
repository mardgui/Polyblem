package fr.unice.polytech.polyblem.model;

/**
 * Created by Florian on 14/05/2018.
 */

public enum Location {
    PLACEHOLDER("Lieu"),
    PARKING("Parking"),
    TOILETS("Toilettes"),
    STUDENT_COMMON_ROOM("Foyer"),
    O_BUILDING("Bâtiment O"),
    E_BUILDING("Bâtiment E"),
    OTHER("Autre");

    String name;

    Location(String name) {
        this.name = name;
    }

    public static Location getFromName(String name) {
        for (Location location : Location.values()) {
            if (location.name.equals(name)) return location;
        }
        throw new IllegalArgumentException("This location does not exist!");
    }

    public String getName() {
        return name;
    }
}
