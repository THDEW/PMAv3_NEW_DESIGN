package History_OnOff.model;

/**
 * Created by my131 on 28/4/2559.
 */
public class SingleItemModel {

    private int id;
    private String name;
    private double[] power;
    private String description;
    private String lastTime;
    private double lastRecord;
    private int locationId;
    private int powernodeId;


    public SingleItemModel() {
    }

    public SingleItemModel(int id, String name, double[] power, String lastTime, double lastRecord) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.lastTime = lastTime;
        this.lastRecord = lastRecord;
    }

    public SingleItemModel( int id, String name, int locationId, int powernodeId) {
        this.id = id;
        this.name = name;
        this.locationId = locationId;
        this.powernodeId = powernodeId;
    }


    public double getSumPower() {
        double temp = 0;

        for(int i = 0; i < power.length; i++){
            temp += power[i];
        }

        return temp;
    }

    public int getId() {return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double[] getAllPower() {
        return power;
    }

    public void setFirstPower(double d){power[0] = d;}

    public String getLastTime() {
        return lastTime;
    }

    public double getLastRecord() {
        return lastRecord;
    }

    public int getLocationId(){
        return locationId;
    }

    public int getPowernodeId(){
        return powernodeId;
    }
}
