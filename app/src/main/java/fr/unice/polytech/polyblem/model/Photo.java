package fr.unice.polytech.polyblem.model;

/**
 * Created by user on 01/05/2018.
 */

public class Photo {

    private Integer idPhoto;
    private Integer idIssue;
    private String url;

    public Photo(Integer idPhoto, Integer idIssue, String url) {
        this.idPhoto = idPhoto;
        this.idIssue = idIssue;
        this.url = url;
    }

    public Photo(String url) {
        this.idIssue = idIssue;
        this.url = url;
    }

    public Integer getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(Integer idPhoto) {
        this.idPhoto = idPhoto;
    }

    public Integer getIdIssue() {
        return idIssue;
    }

    public void setIdIssue(Integer idIssue) {
        this.idIssue = idIssue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "idPhoto=" + idPhoto +
                ", idIssue=" + idIssue +
                ", url='" + url + '\'' +
                '}';
    }
}
