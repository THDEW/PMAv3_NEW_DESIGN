package ElectricityCost;

import java.util.ArrayList;

/**
 * Created by my131 on 1/5/2559.
 */
public class ElectricityBillModel {
    private String locationName;
    private double unitLocation;
    private double onPeakLocation;
    private double offPeakLocation;
    private double cost;
    private double costOnPeak;
    private double costOffPeak;
    private ArrayList<String> devicesName;
    private ArrayList<Double> unitDevices;
    private ArrayList<Double> costDevice;
    private ArrayList<Double> onPeakDevices;
    private ArrayList<Double> offPeakDevices;
    private ArrayList<Double> onPeakCostDevices;
    private ArrayList<Double> offPeakCostDevices;

    public ElectricityBillModel(){

    }
    public ElectricityBillModel(String locationName, ArrayList<String> devicesName, ArrayList<Double> unitDevices) {
        this.locationName = locationName;
        this.devicesName = devicesName;
        this.unitDevices = unitDevices;
        this.unitLocation = getSumunit(unitDevices);
        this.costDevice = new ArrayList<>();
        this.cost = 0d;
    }

    public ElectricityBillModel(String locationName, ArrayList<String> devicesName,
                                ArrayList<Double> onPeakDevices, ArrayList<Double> offPeakDevices) {
        this.locationName = locationName;
        this.devicesName = devicesName;
        this.onPeakDevices = onPeakDevices;
        this.offPeakDevices = offPeakDevices;
        this.onPeakLocation = getSumunit(onPeakDevices);
        this.offPeakLocation = getSumunit(offPeakDevices);
        this.unitLocation =  this.onPeakLocation + this.onPeakLocation;
        this.onPeakCostDevices = new ArrayList<>();
        this.offPeakCostDevices = new ArrayList<>();
        this.cost = 0d;
    }

    public double getCostLocation(){
        return cost;
    }
    public double getCostDevice(int index){
        return costDevice.get(index);
    }
    public String getLocationName(){
        return locationName;
    }
    public double getUnitLocation(){
        return unitLocation;
    }
    public ArrayList<String> getDevicesName(){
        return  devicesName;
    }
    public ArrayList<Double> getUnitDevices() {
        return unitDevices;
    }
    public Double getUnitDevicesdou(int position){
        return  unitDevices.get(position);
    }
    public String getDevicesNamestring(int position){
        return  devicesName.get(position);
    }
    public double getSumunit(ArrayList<Double> data){
        double sumunit=0;
        for(int i = 0; i < data.size(); i++){
            sumunit += data.get(i);
        }
        return sumunit;
    }

    public void setAllCost(double locationCost){
        cost = locationCost;

        for(int i = 0; i < getUnitDevices().size(); i++){
            costDevice.add(getPercent(getUnitDevicesdou(i),unitLocation)*cost);
        }

    }


    public void setAllCost(double locationOnPeakCost, double locationOffPeakCost){

        costOnPeak = locationOnPeakCost;
        costOffPeak = locationOffPeakCost;
        cost = costOnPeak + costOffPeak;
        costDevice = new ArrayList<>();
        unitDevices = new ArrayList<>();

        for(int i = 0; i < devicesName.size(); i++){
            onPeakCostDevices.add(getPercent(getOnPeakUnitDevices(i),onPeakLocation)*costOnPeak);
            offPeakCostDevices.add(getPercent(getOffPeakUnitDevices(i),offPeakLocation)*costOffPeak);
            costDevice.add((onPeakCostDevices.get(i) + offPeakCostDevices.get(i)));
            unitDevices.add(getOffPeakUnitDevices(i) + getOnPeakUnitDevices(i));
        }
    }

    private double getOnPeakUnitDevices(int i) {

        return onPeakDevices.get(i);
    }

    private double getOnPeakCostDevices(int i) {

        return onPeakCostDevices.get(i);
    }

    private double getOffPeakUnitDevices(int i) {
        return offPeakDevices.get(i);
    }
    private double getOffPeakCostDevices(int i) {

        return offPeakCostDevices.get(i);
    }
    public double getPercent(double a, double b){

        return a/b;
    }

    public double getCostOnPeak() {
        return costOnPeak;
    }

    public double getOnPeakLocation() {
        return onPeakLocation;
    }

    public double getCostOffPeak() {
        return costOffPeak;
    }

    public double getOffPeakLocation() {
        return offPeakLocation;
    }

    public double getOnPeakDevice(int begin) {
        return onPeakDevices.get(begin);
    }

    public double getOnPeakCostDevice(int begin) {
        return onPeakCostDevices.get(begin);
    }

    public double getOffPeakDevice(int begin) {
        return offPeakDevices.get(begin);
    }

    public double getOffPeakCostDevice(int begin) {
        return offPeakCostDevices.get(begin);
    }
}
