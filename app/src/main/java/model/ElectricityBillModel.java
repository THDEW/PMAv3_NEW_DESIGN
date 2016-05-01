package model;

import java.util.ArrayList;

/**
 * Created by my131 on 1/5/2559.
 */
public class ElectricityBillModel {
    private String locationName;
    private int costLocation;
    private ArrayList<String> devicesName;
    private ArrayList<Integer> costDevices;

    public ElectricityBillModel(){

    }
    public ElectricityBillModel(String locationName, int costLocation, ArrayList<String> devicesName, ArrayList<Integer> costDevices) {
        this.locationName = locationName;
        this.costLocation = costLocation;
        this.devicesName = devicesName;
        this.costDevices = costDevices;
    }

    public String getLocationName(){
        return locationName;
    }
    public int getCostLocation(){
        return costLocation;
    }
    public ArrayList<String> getDevicesName(){
        return  devicesName;
    }
    public ArrayList<Integer> getCostDevices() {
        return costDevices;
    }
    public int getCostDevicesint(int position){
        return  costDevices.get(position);
    }
    public String getDevicesNamestring(int position){
        return  devicesName.get(position);
    }
}
