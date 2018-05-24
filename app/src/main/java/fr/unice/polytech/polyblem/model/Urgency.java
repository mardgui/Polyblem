package fr.unice.polytech.polyblem.model;

import fr.unice.polytech.polyblem.R;

public enum Urgency {
    HIGH(R.drawable.higurg, "Forte", 2),
    MEDIUM(R.drawable.medurg, "Moyen", 1),
    LOW(R.drawable.lowurg, "Faible", 0);

    private int id;
    private String name;
    private int number;

    Urgency(int id, String name, int number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public static Urgency getFromName(String name) {
        for (Urgency urgency : Urgency.values()) {
            if (urgency.name.equals(name)) return urgency;
        }
        throw new IllegalArgumentException("This urgency does not exist!");
    }

    public static String getFromId(int number) {
        for (Urgency urgency : Urgency.values()) {
            if (urgency.number == number) return urgency.name;
        }
        throw new IllegalArgumentException("This urgency does not exist!");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
