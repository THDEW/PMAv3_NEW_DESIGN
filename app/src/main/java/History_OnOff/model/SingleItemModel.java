package History_OnOff.model;

/**
 * Created by my131 on 28/4/2559.
 */
public class SingleItemModel {


    private String name;
    private double[] power;
    private String usageTime;
    private String description;


    public SingleItemModel() {
    }

    public SingleItemModel(String name, double[] power, String usageTime) {
        this.name = name;
        this.power = power;
        this.usageTime = usageTime;
    }


    public double getSumPower() {
        double temp = 0;

        for(int i = 0; i < power.length; i++){
            temp += power[i];
        }

        return temp;
    }

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


    public String getUsageTime() {
        return usageTime;
    }

    public double[] getAllPower() {
        return power;
    }
}
