package model;

/**
 * Created by my131 on 29/4/2559.
 */
public class ApplianceModel {
    private String appliname, genre, date;

    public ApplianceModel() {
    }

    public ApplianceModel(String appliname, String genre, String date) {
        this.appliname = appliname;
        this.genre = genre;
        this.date = date;
    }

    public String getTitle() {
        return appliname;
    }

    public void setAppliname(String name) {
        this.appliname = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
