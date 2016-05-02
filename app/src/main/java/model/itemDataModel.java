package model;

import java.util.ArrayList;

/**
 * Created by Toshiba on 5/2/2016.
 */
public class ItemDataModel {
    private String dataID;
    private String type;
    private ArrayList<String> dataRow;

    public ItemDataModel(String dataID, String type){
        this.dataID = dataID;
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
        return dataID;
    }
}
