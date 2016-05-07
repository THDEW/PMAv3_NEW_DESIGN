package History_OnOff.model;

import android.app.DialogFragment;

import java.util.ArrayList;

/**
 * Created by Toshiba on 4/30/2016.
 */
public class GraphSeriesModel {

    String device;
    String location;
    ArrayList<Double> value;
    boolean isLocation = false;

    public GraphSeriesModel(String device, String location, ArrayList<Double> value, boolean isLocation){
        this.device = device;
        this.location = location;
        this.value = value;
        this.isLocation = isLocation;
    }

    public double getSumValue(){
        double temp = 0d;

        for(int i = 0; i < value.size(); i++){
            temp += value.get(i);
        }
        return temp;
    }


    public void setValue(int i) {
        value.set(i, -1d);
    }

    public double getValue(int i) {
        return value.get(i);
    }

    public int getSize() {
        return value.size();
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
