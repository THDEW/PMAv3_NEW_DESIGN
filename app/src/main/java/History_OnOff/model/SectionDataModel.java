package History_OnOff.model;
import java.util.ArrayList;
/**
 * Created by my131 on 28/4/2559.
 */
public class SectionDataModel {

    private String headerTitle;
    private ArrayList<SingleItemModel> allItemsInSection;
    private double[] powerOfLocation;
    private int id;

    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle, ArrayList<SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }

    public SectionDataModel(int id, String headerTitle) {
        this.id = id;
        this.headerTitle = headerTitle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public void setPowerOfLocation(double[] power) {
        this.powerOfLocation = power;
    }

    public void setFirstPower(double d){powerOfLocation[0] = d;}

    public int getId(){
        return id;
    }

    public double[] getAllPower() {
        return this.powerOfLocation;
    }
}
