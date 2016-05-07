package Statistic;

import java.util.Comparator;

/**
 * Created by my131 on 29/4/2559.
 */
public class ApplianceModel {
    private String appliname, date, frontelectusage, locationname;
    private long elecusage, elecusageLocation;
    private boolean isLocation = false;

    public ApplianceModel() {
    }

    public ApplianceModel(String appliname, long elecusage, String date, String frontelectusage) {
        this.appliname = appliname;
        this.elecusage = elecusage;
        this.date = date;
        this.frontelectusage = frontelectusage;
    }
    public ApplianceModel(String locationname, long elecusageLocation, String date, String frontelectusage,boolean isLocation){
        this.locationname = locationname;
        this.elecusageLocation = elecusageLocation;
        this.date = date;
        this.frontelectusage = frontelectusage;
        this.isLocation = isLocation;

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

    public long getElect() {
        return elecusage;
    }

    public String getFrontElect() {
        return frontelectusage;
    }

    public void setElect(long Elect) {
        this.elecusage = Elect;
    }

    public static Comparator<ApplianceModel> electusageMaxComparator = new Comparator<ApplianceModel>() {

        public int compare(ApplianceModel e1, ApplianceModel e2) {
            long electusage1 = e1.getElect();
            long electusage2 = e2.getElect();

            //ascending order
            //return appliname1.compareTo(appliname2);

            //descending order
            return (int) (electusage2-electusage1);
        }};

    public static Comparator<ApplianceModel> electusageMinComparator = new Comparator<ApplianceModel>() {

        public int compare(ApplianceModel e1, ApplianceModel e2) {
            long electusage1 = e1.getElect();
            long electusage2 = e2.getElect();

            //ascending order
            return (int) (electusage1-electusage2);

            //descending order
            //return (int) (electusage2-electusage1);
        }};


}
