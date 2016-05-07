package Setting;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toshiba on 5/1/2016.
 */
public class RowDataDialog extends DialogFragment  {

    private String typeOfData = "";
    private int type;
    private boolean isAdd;

    private Context mContext;

    private Button addBt;
    private Button deleteBt;
    private Button cancelBt;

    private FragmentManager fm;

    private ArrayList<ItemDataModel>[] dataFromDatabase;
    private ItemDataModel ownData;

    private int oldData = 0;

    public RowDataDialog(String typeOfData, int type, boolean isAdd, FragmentManager fm,
                         ArrayList<ItemDataModel>[] dataFromDatabase){
        this.typeOfData = typeOfData;
        this.type = type;
        this.isAdd = isAdd;
        this.fm = fm;
        this.dataFromDatabase = dataFromDatabase;
    }

    public RowDataDialog(String typeOfData, int type, boolean isAdd, FragmentManager fm,
                         ArrayList<ItemDataModel>[] dataFromDatabase, ItemDataModel ownData){
        this.typeOfData = typeOfData;
        this.type = type;
        this.isAdd = isAdd;
        this.fm = fm;
        this.dataFromDatabase = dataFromDatabase;
        this.ownData = ownData;
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.dialog_row_data_type, container, false);
        getDialog().setTitle(typeOfData);

        addBt = (Button) rootView.findViewById(R.id.addBt_1);
        deleteBt = (Button) rootView.findViewById(R.id.deleteBt_1);
        cancelBt = (Button) rootView.findViewById(R.id.cancelBt_1);
        EditText edit;
        if(ownData != null) {
            edit = (EditText) rootView.findViewById(R.id.data_type_1);
            edit.setText(ownData.getData(0));
            edit = (EditText) rootView.findViewById(R.id.data_type_2);
            edit.setText(ownData.getData(1));
        }

        Spinner spinner;
        ArrayAdapter<String> dataAdapter;
        List<String> item;

        switch (type){
            case 1:
                rootView = inflater.inflate(R.layout.dialog_row_data_detail, container, false);
                getDialog().setTitle(typeOfData);

                addBt = (Button) rootView.findViewById(R.id.addBt_2);
                deleteBt = (Button) rootView.findViewById(R.id.deleteBt_2);
                cancelBt = (Button) rootView.findViewById(R.id.cancelBt_2);

                // Spinner element type
                spinner = (Spinner) rootView.findViewById(R.id.data_type_spinner);
                item = new ArrayList<String>();
                for(int i = 0; i < dataFromDatabase[0].size(); i++){
                    item.add(dataFromDatabase[0].get(i).getName());
                    if(ownData != null) {
                        if (ownData.getData(0).equals(dataFromDatabase[0].get(i).getName())) {
                            oldData = i;
                        }
                    }
                }
                dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(oldData);

                //set edi text by old data
                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_detail_1);
                    edit.setText(ownData.getData(1));
                    edit = (EditText) rootView.findViewById(R.id.data_detail_2);
                    edit.setText(ownData.getData(2));
                    edit = (EditText) rootView.findViewById(R.id.data_detail_3);
                    edit.setText(ownData.getData(3));
                    edit = (EditText) rootView.findViewById(R.id.data_detail_4);
                    edit.setText(ownData.getData(4));
                    edit = (EditText) rootView.findViewById(R.id.data_detail_5);
                    edit.setText(ownData.getData(5));
                }

                break;
            case 2:
                rootView = inflater.inflate(R.layout.dialog_row_data_node, container, false);
                getDialog().setTitle(typeOfData);

                addBt = (Button) rootView.findViewById(R.id.addBt_3);
                deleteBt = (Button) rootView.findViewById(R.id.deleteBt_3);
                cancelBt = (Button) rootView.findViewById(R.id.cancelBt_3);

                // Spinner element location
                spinner = (Spinner) rootView.findViewById(R.id.location_spinner);
                item = new ArrayList<String>();
                for(int i = 0; i < dataFromDatabase[3].size(); i++){
                    item.add(dataFromDatabase[3].get(i).getName());
                    if(ownData != null) {
                        if (ownData.getData(4).equals(dataFromDatabase[3].get(i).getName())) {
                            oldData = i;
                        }
                    }
                }
                dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(oldData);

                //set edi text by old data
                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_node_1);
                    edit.setText(ownData.getData(0));
                    edit = (EditText) rootView.findViewById(R.id.data_node_2);
                    edit.setText(ownData.getData(1));
                    edit = (EditText) rootView.findViewById(R.id.data_node_3);
                    edit.setText(ownData.getData(2));
                    edit = (EditText) rootView.findViewById(R.id.data_node_4);
                    edit.setText(ownData.getData(3));
                }

                break;
            case 3:
                rootView = inflater.inflate(R.layout.dialog_row_data_location, container, false);
                getDialog().setTitle(typeOfData);

                addBt = (Button) rootView.findViewById(R.id.addBt_4);
                deleteBt = (Button) rootView.findViewById(R.id.deleteBt_4);
                cancelBt = (Button) rootView.findViewById(R.id.cancelBt_4);

                //set edi text by old data
                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_location_1);
                    edit.setText(ownData.getData(0));
                    edit = (EditText) rootView.findViewById(R.id.data_location_2);
                    edit.setText(ownData.getData(1));
                }
                break;
            case 4:
                rootView = inflater.inflate(R.layout.dialog_row_data_group_of_device, container, false);
                getDialog().setTitle(typeOfData);

                addBt = (Button) rootView.findViewById(R.id.addBt_5);
                deleteBt = (Button) rootView.findViewById(R.id.deleteBt_5);
                cancelBt = (Button) rootView.findViewById(R.id.cancelBt_5);

                // Spinner element node
                spinner = (Spinner) rootView.findViewById(R.id.node_spinner);
                item = new ArrayList<String>();
                for(int i = 0; i < dataFromDatabase[2].size(); i++){
                    item.add(dataFromDatabase[2].get(i).getName());
                    if(ownData != null) {
                        if (ownData.getData(1).equals(dataFromDatabase[2].get(i).getName())) {
                            oldData = i;
                        }
                    }
                }
                dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(oldData);

                // Spinner element location
                spinner = (Spinner) rootView.findViewById(R.id.location_spinner_2);
                item = new ArrayList<String>();
                for(int i = 0; i < dataFromDatabase[3].size(); i++){
                    item.add(dataFromDatabase[3].get(i).getName());
                    if(ownData != null) {
                        if (ownData.getData(2).equals(dataFromDatabase[3].get(i).getName())) {
                            oldData = i;
                        }
                    }
                }
                dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(oldData);

                //set edi text by old data
                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_group_1);
                    edit.setText(ownData.getData(0));
                    edit = (EditText) rootView.findViewById(R.id.data_group_2);
                    edit.setText(ownData.getData(3));
                    edit = (EditText) rootView.findViewById(R.id.data_group_3);
                    edit.setText(ownData.getData(4));
                }

                break;
            case 5:
                rootView = inflater.inflate(R.layout.dialog_row_data_device, container, false);
                getDialog().setTitle(typeOfData);

                addBt = (Button) rootView.findViewById(R.id.addBt_6);
                deleteBt = (Button) rootView.findViewById(R.id.deleteBt_6);
                cancelBt = (Button) rootView.findViewById(R.id.cancelBt_6);

                // Spinner element detail
                spinner = (Spinner) rootView.findViewById(R.id.detail_spinner);
                item = new ArrayList<String>();
                for(int i = 0; i < dataFromDatabase[1].size(); i++){
                    item.add(dataFromDatabase[1].get(i).getName());
                    if(ownData != null) {

                        if (ownData.getData(1).equals(dataFromDatabase[1].get(i).getName())) {
                            Log.d("aaa",ownData.getData(1) + " " + dataFromDatabase[1].get(i).getName());
                            oldData = i;
                        }
                    }
                }
                dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(oldData);

                // Spinner element group
                spinner = (Spinner) rootView.findViewById(R.id.group_spinner);
                item = new ArrayList<String>();
                // Spinner Drop down elements
                for(int i = 0; i < dataFromDatabase[4].size(); i++){
                    item.add(dataFromDatabase[4].get(i).getName());
                    if(ownData != null) {
                        if (ownData.getData(2).equals(dataFromDatabase[4].get(i).getName())) {
                            oldData = i;
                        }
                    }
                }
                dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item);
                spinner.setAdapter(dataAdapter);
                spinner.setSelection(oldData);

                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_device_1);
                    edit.setText(ownData.getData(0));
                    edit = (EditText) rootView.findViewById(R.id.data_device_2);
                    edit.setText(ownData.getData(3));
                }
                break;
        }


        if(isAdd){
            addBt.setText("Add");
            deleteBt.setVisibility(View.INVISIBLE);
        }else{
            addBt.setText("Update");
            deleteBt.setVisibility(View.VISIBLE);
        }

        addBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isAdd){
                    Toast.makeText(getContext(), "Add data", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "data updated", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

        });

        deleteBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DeleteConfirmDialog dialog = new DeleteConfirmDialog(typeOfData);
                dialog.show(fm, "deleteItem");
                dismiss();
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        return rootView;
    }


}
