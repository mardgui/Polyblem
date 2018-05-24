package fr.unice.polytech.polyblem.model;

/**
 * Created by Florian on 14/05/2018.
 */

public enum Category {
    PLACEHOLDER("Catégorie"),
    MANQUE("Manque"),
    CASSE("Casse"),
    DYSFONCTIONNEMENT("Dysfonctionnement"),
    PROPRETE("Propreté"),
    AUTRE("Autre");

    String name;

    Category(String name) {
        this.name = name;
    }

    public static Category getFromName(String name) {
        for (Category category : Category.values()) {
            if (category.name.equals(name)) return category;
        }
        throw new IllegalArgumentException("This category does not exist!");
    }

    public String getName() {
        return name;
    }
}
