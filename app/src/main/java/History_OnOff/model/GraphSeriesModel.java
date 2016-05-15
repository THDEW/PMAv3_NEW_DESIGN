package History_OnOff.model;

import android.app.DialogFragment;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Toshiba on 4/30/2016.
 */
public class GraphSeriesModel {

    String device;
    String location;
    int deviceId;
    int powerNodeId;
    int locationId;

    double value;
    boolean isLocation = false;

    public GraphSeriesModel(int locationId, String location, double value, boolean isLocation){
        this.locationId = locationId;
        this.location = location;
        this.value = value;
        this.isLocation = isLocation;
    }

    public GraphSeriesModel(int deviceId, int powerNodeId, int locationId, String device, String location, double value, boolean isLocation){
        this.deviceId = deviceId;
        this.powerNodeId = powerNodeId;
        this.locationId = locationId;
        this.device = device;
        this.location = location;
        this.value = value;
        this.isLocation = isLocation;
    }

    public int getLocationId(){
        return locationId;
    }
    public int getPowerNodeId(){
        return powerNodeId;
    }
    public int getDeviceId(){
        return deviceId;
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
