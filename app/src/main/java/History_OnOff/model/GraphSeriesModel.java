package History_OnOff.model;

import android.app.DialogFragment;

import java.util.ArrayList;

/**
 * Created by Toshiba on 4/30/2016.
 */
public class GraphSeriesModel {

    String device;
    String location;
    double[] value;
    boolean isLocation = false;

    public GraphSeriesModel(String device, String location, double[] value, boolean isLocation){
        this.device = device;
        this.location = location;
        this.value = value;
        this.isLocation = isLocation;
    }

    public double getSumValue(){
        double temp = 0d;

        for(int i = 0; i < value.length; i++){
            temp += value[i];
        }
        return temp;
    }


    public void setValue(int i) {
        value[i] = -1d;
    }

    public double getValue(int i) {
        return value[i];
    }

    public int getSize() {
        return value.length;
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
