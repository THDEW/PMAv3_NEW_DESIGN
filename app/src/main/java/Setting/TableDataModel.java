package Setting;

import java.util.ArrayList;

/**
 * Created by Toshiba on 5/2/2016.
 */
public class TableDataModel {
    private String headerTitle;
    private ArrayList<ItemDataModel> allItemsInSection;

    public TableDataModel() {

    }
    public TableDataModel(String headerTitle, ArrayList<ItemDataModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<ItemDataModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<ItemDataModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
}
