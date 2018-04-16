package fr.unice.polytech.polyblem.model;

/**
 * Created by user on 16/04/2018.
 */

public class Issue {

    private String category;
    private String titleIssue;
    private String description;
    private String pictureUrl;
    private String location;
    private String locationDetails;
    private String urgency;
    private String email;
    private String date;

    public Issue(String category, String titleIssue, String description, String pictureUrl, String location, String locationDetails, String urgency, String email, String date) {
        this.category = category;
        this.titleIssue = titleIssue;
        this.description = description;
        this.pictureUrl = pictureUrl;
        this.location = location;
        this.locationDetails = locationDetails;
        this.urgency = urgency;
        this.email = email;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getTitleIssue() {
        return titleIssue;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationDetails() {
        return locationDetails;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }
}
