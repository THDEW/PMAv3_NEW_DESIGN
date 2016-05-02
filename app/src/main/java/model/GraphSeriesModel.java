package model;

/**
 * Created by Toshiba on 4/30/2016.
 */
public class GraphSeriesModel {

    String device;
    String location;
    double value;
    boolean isLocation = false;

    public GraphSeriesModel(String device, String location, double value, boolean isLocation){
        this.device = device;
        this.location = location;
        this.value = value;
        this.isLocation = isLocation;
    }

    public double getValue(){
        return value;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public String getDevice() {
        return device;
    }

    public String getLocation() {
        return location;
    }

    public boolean getIsLocation() {
        return isLocation;
    }
}
