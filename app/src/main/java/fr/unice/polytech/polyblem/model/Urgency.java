package fr.unice.polytech.polyblem.model;

import fr.unice.polytech.polyblem.R;

public enum Urgency {
    HIGH(R.drawable.higurg, "Forte"),
    MEDIUM(R.drawable.medurg, "Moyen"),
    LOW(R.drawable.lowurg, "Faible");

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Urgency getFromName(String name) {
        for (Urgency urgency : Urgency.values()) {
            if (urgency.name.equals(name)) return urgency;
        }
        throw new IllegalArgumentException("This urgency does not exist!");
    }

    Urgency(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
