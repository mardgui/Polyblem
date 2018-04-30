package fr.unice.polytech.polyblem.model;

/**
 * Created by Marion on 16/04/2018
 */

public class Issue {

    private int idIssue;
    private String titleIssue; //obli
    private String category;
    private String description;
    private String pictureUrl;
    private String location;
    private String locationDetails;
    private String urgency;
    private String email; //obli
    private String date;

    public Issue(int idIssue, String titleIssue, String category,  String description, String pictureUrl, String location, String locationDetails, String urgency, String email, String date) {
        this.idIssue = idIssue;
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

    public Issue(String titleIssue, String category, String description, String pictureUrl, String location, String locationDetails, String urgency, String email, String date) {
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

    public int getIdIssue() {
        return idIssue;
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

    @Override
    public String toString() {
        return "Issue{" +
                "idIssue=" + idIssue +
                ", titleIssue='" + titleIssue + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", location='" + location + '\'' +
                ", locationDetails='" + locationDetails + '\'' +
                ", urgency='" + urgency + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
