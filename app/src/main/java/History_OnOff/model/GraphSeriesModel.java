package History_OnOff.model;

import android.app.DialogFragment;

import java.util.ArrayList;

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


    public void setValue(double temp) {
        value = temp;
    }

    public double getValue() {
        return value;
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
