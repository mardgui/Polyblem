package fr.unice.polytech.polyblem.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marion on 16/04/2018
 */

public class Issue implements Parcelable {

    private int idIssue;
    private String titleIssue; //obli
    private String category;
    private String description;
    private String location;
    private String locationDetails;
    private Urgency urgency;
    private String email; //obli
    private String date;

    public Issue(int idIssue, String titleIssue, String category,  String description, String location, String locationDetails, String urgency, String email, String date) {
        this.idIssue = idIssue;
        this.category = category;
        this.titleIssue = titleIssue;
        this.description = description;
        this.location = location;
        this.locationDetails = locationDetails;
        this.urgency = Urgency.getFromName(urgency);
        this.email = email;
        this.date = date;
    }

    public Issue(String titleIssue, String category, String description, String location, String locationDetails, String urgency, String email, String date) {
        this.category = category;
        this.titleIssue = titleIssue;
        this.description = description;
        this.location = location;
        this.locationDetails = locationDetails;
        this.urgency = Urgency.getFromName(urgency);
        this.email = email;
        this.date = date;
    }

    public int getIdIssue() {
        return idIssue;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return titleIssue;
    }

    public String getDescription() {
        return description;
    }


    public String getLocation() {
        return location;
    }

    public String getLocationDetails() {
        return locationDetails;
    }

    public String getUrgencyName() {
        return urgency.getName();
    }

    public Urgency getUrgency() {
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
                ", location='" + location + '\'' +
                ", locationDetails='" + locationDetails + '\'' +
                ", urgency='" + urgency + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idIssue);
        dest.writeString(titleIssue);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(locationDetails);
        dest.writeString(email);
        dest.writeString(date);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Issue createFromParcel(Parcel in) {
            return new Issue(in);
        }

        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };

    public Issue(Parcel in) {
        this.idIssue = in.readInt();
        this.category = in.readString();
        this.titleIssue = in.readString();
        this.description = in.readString();
        this.location = in.readString();
        this.locationDetails = in.readString();
        this.email = in.readString();
        this.date = in.readString();
    }
}
