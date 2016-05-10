package Setting;

import java.util.ArrayList;

/**
 * Created by Toshiba on 5/2/2016.
 */
public class ItemDataModel {
    private String dataName;
    private String type;
    private ArrayList<String> dataRow;
    private int id;

    public ItemDataModel(String dataName, String type){
        this.dataName = dataName;
        this.type = type;
        dataRow = new ArrayList<>();
    }

    public void addData(String data){
        dataRow.add(data);
    }

    public String getData(int i){
        return dataRow.get(i);
    }


    public String getName() {
        return dataName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
