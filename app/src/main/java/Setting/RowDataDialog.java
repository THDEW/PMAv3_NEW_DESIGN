package Setting;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    private View rootView;

    private Connection connection;

    public RowDataDialog(String typeOfData, int type, boolean isAdd, FragmentManager fm,
                         ArrayList<ItemDataModel>[] dataFromDatabase, Connection connection){
        this.typeOfData = typeOfData;
        this.type = type;
        this.isAdd = isAdd;
        this.fm = fm;
        this.dataFromDatabase = dataFromDatabase;
        this.connection = connection;
    }

    public RowDataDialog(String typeOfData, int type, boolean isAdd, FragmentManager fm,
                         ArrayList<ItemDataModel>[] dataFromDatabase, ItemDataModel ownData, Connection connection){
        this.typeOfData = typeOfData;
        this.type = type;
        this.isAdd = isAdd;
        this.fm = fm;
        this.dataFromDatabase = dataFromDatabase;
        this.ownData = ownData;
        this.connection = connection;
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        rootView = null;
        getDialog().setTitle(typeOfData);


        EditText edit;
        Spinner spinner;
        ArrayAdapter<String> dataAdapter;
        List<String> item;

        switch (type){
            case 0:
                rootView = inflater.inflate(R.layout.dialog_row_data_type, container, false);
                addBt = (Button) rootView.findViewById(R.id.addBt_1);
                deleteBt = (Button) rootView.findViewById(R.id.deleteBt_1);
                cancelBt = (Button) rootView.findViewById(R.id.cancelBt_1);

                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_type_1);
                    edit.setText(ownData.getData(0));
                    edit = (EditText) rootView.findViewById(R.id.data_type_2);
                    edit.setText(ownData.getData(1));
                }
                break;
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
                    edit = (EditText) rootView.findViewById(R.id.data_node_2);
                    edit.setText(ownData.getData(0));
                    edit = (EditText) rootView.findViewById(R.id.data_node_3);
                    edit.setText(ownData.getData(1));
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

                // Spinner element group
                spinner = (Spinner) rootView.findViewById(R.id.on_off_spinner);
                item = new ArrayList<String>();
                // Spinner Drop down elements
                item.add("OFF");
                item.add("ON");
                dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, item);
                spinner.setAdapter(dataAdapter);

                //set edi text by old data
                if(ownData != null) {

                    spinner.setSelection(Integer.parseInt(ownData.getData(3)));

                    edit = (EditText) rootView.findViewById(R.id.data_group_1);
                    edit.setText(ownData.getData(0));
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
                    //Toast.makeText(getContext(), "Add data", Toast.LENGTH_SHORT).show();

                    addData(type);

                    dismiss();
                } else {
                    //Toast.makeText(getContext(), "data updated", Toast.LENGTH_SHORT).show();

                    editData(type);

                    dismiss();
                }
            }

        });

        deleteBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DeleteConfirmDialog dialog = new DeleteConfirmDialog(typeOfData,ownData.getId());
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


    public void addData(int type){
        EditText edit;
        Spinner spinner;
        String[] addData;

        String clientHandle = connection.handle();
        String topic = null;
        String message = null;
        int qos = 0;
        boolean retained = false;

        String[] args = new String[2];

        int location_id,power_node_id,device_type_id,device_detail_id = 0;




        switch (type){
            case 0:
                //device type insertion
                    addData = new String[2];
                    edit = (EditText) rootView.findViewById(R.id.data_type_1);
                    addData[0] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_type_2);
                    addData[1] = edit.getText().toString();

                    if(addData[0].isEmpty()||addData[1].isEmpty())
                    {
                        Toast.makeText(getActivity(),"Please insert all the parameters",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        topic = "android/settings/addData/device_type";
                        message = addData[0]+","+addData[1];

                        args[0] = message;
                        args[1] = topic+";qos:"+qos+";retained:"+retained;

                        try {
                            connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }




                break;
            case 1:
                //device detail insertion
                addData = new String[6];
                // Spinner element type
                spinner = (Spinner) rootView.findViewById(R.id.data_type_spinner);
                addData[0] = spinner.getSelectedItem().toString();
                device_type_id = dataFromDatabase[0].get(spinner.getSelectedItemPosition()).getId();
                Log.v("device-detail",""+device_type_id);

                edit = (EditText) rootView.findViewById(R.id.data_detail_1);
                addData[1] = edit.getText().toString();
                edit = (EditText) rootView.findViewById(R.id.data_detail_2);
                addData[2] = edit.getText().toString();
                edit = (EditText) rootView.findViewById(R.id.data_detail_3);
                addData[3] = edit.getText().toString();
                edit = (EditText) rootView.findViewById(R.id.data_detail_4);
                addData[4] = edit.getText().toString();
                edit = (EditText) rootView.findViewById(R.id.data_detail_5);
                addData[5] = edit.getText().toString();

                if(addData[0].isEmpty()||addData[1].isEmpty()||addData[2].isEmpty()||addData[3].isEmpty()||addData[4].isEmpty()||addData[5].isEmpty())
                {
                    Toast.makeText(getActivity(),"Please insert all the parameters",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    topic = "android/settings/addData/device_detail";
                    message = device_type_id+","+addData[1]+","+addData[2]+","+addData[3]+","+addData[4]+","+addData[5];

                    args[0] = message;
                    args[1] = topic+";qos:"+qos+";retained:"+retained;

                    try {
                        connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 2:
                //power node insertion
                addData = new String[4];

                spinner = (Spinner) rootView.findViewById(R.id.location_spinner);
                location_id = dataFromDatabase[3].get(spinner.getSelectedItemPosition()).getId();
                Log.v("location id",""+location_id);

                edit = (EditText) rootView.findViewById(R.id.data_node_2);
                addData[0] = edit.getText().toString();
                edit = (EditText) rootView.findViewById(R.id.data_node_3);
                addData[1] = edit.getText().toString();
                edit = (EditText) rootView.findViewById(R.id.data_node_4);
                addData[2] = edit.getText().toString();

                if(addData[0].isEmpty()||addData[1].isEmpty()||addData[2].isEmpty())
                {
                    Toast.makeText(getActivity(),"Please insert all the parameters",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    topic = "android/settings/addData/power_node";
                    message = addData[0]+","+addData[1]+","+addData[2]+","+location_id;

                    args[0] = message;
                    args[1] = topic+";qos:"+qos+";retained:"+retained;

                    try {
                        connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 3:
                //location insertion
                addData = new String[2];
                //set edi text by old data

                    edit = (EditText) rootView.findViewById(R.id.data_location_1);
                    addData[0] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_location_2);
                    addData[1] = edit.getText().toString();

                    if(addData[0].isEmpty()||addData[1].isEmpty())
                    {
                        Toast.makeText(getActivity(),"Please insert all the parameters",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        topic = "android/settings/addData/location";
                        message = addData[0]+","+addData[1];

                        args[0] = message;
                        args[1] = topic+";qos:"+qos+";retained:"+retained;

                        try {
                            connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }

                break;
            case 4:
                String status = "";
                //group of device insertion
                addData = new String[5];

                // PIN
                edit = (EditText) rootView.findViewById(R.id.data_group_1);
                addData[0] = edit.getText().toString();

                // Spinner element node
                spinner = (Spinner) rootView.findViewById(R.id.node_spinner);
                power_node_id = dataFromDatabase[2].get(spinner.getSelectedItemPosition()).getId();

                //take out
                // Spinner element location
                spinner = (Spinner) rootView.findViewById(R.id.location_spinner_2);
                location_id = dataFromDatabase[3].get(spinner.getSelectedItemPosition()).getId();


                spinner = (Spinner) rootView.findViewById(R.id.on_off_spinner);
                addData[2] = spinner.getSelectedItem().toString();
                if(addData[2].compareToIgnoreCase("on")==0)
                {
                    status = "1";

                }
                else
                {
                    status = "0";
                }
                Log.v(status,"god");

                edit = (EditText) rootView.findViewById(R.id.data_group_3);
                addData[1] = edit.getText().toString();
                /*
                if(addData[0].isEmpty()||addData[1].isEmpty())
                {
                    Toast.makeText(getActivity(),"Please insert all the parameters",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    topic = "android/settings/addData/group_of_device";
                    message = addData[0]+","+power_node_id+","+location_id+","+status+","+addData[1];

                    args[0] = message;
                    args[1] = topic+";qos:"+qos+";retained:"+retained;

                    try {
                        connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                */



                break;
            case 5:
                addData = new String[4];

                // Spinner element detail
                spinner = (Spinner) rootView.findViewById(R.id.detail_spinner);
                addData[1] = spinner.getSelectedItem().toString();

                // Spinner element group
                spinner = (Spinner) rootView.findViewById(R.id.group_spinner);
                addData[2] = spinner.getSelectedItem().toString();


                if(ownData != null) {
                    //Device name
                    edit = (EditText) rootView.findViewById(R.id.data_device_1);
                    addData[0] = edit.getText().toString();
                    //Description
                    edit = (EditText) rootView.findViewById(R.id.data_device_2);
                    addData[3] = edit.getText().toString();
                }
                break;
        }

        //addData[] store data follow attribute as String.


    }

    public void editData(int type){

        EditText edit;

        Spinner spinner;

        String[] editData = null;

        int location_id,power_node_id,device_type_id,device_detail_id = 0;

        switch (type){
            case 0:
                editData = new String[2];
                edit = (EditText) rootView.findViewById(R.id.data_type_1);
                editData[0] = edit.getText().toString();
                edit = (EditText) rootView.findViewById(R.id.data_type_2);
                editData[1] = edit.getText().toString();

            case 1:
                editData = new String[6];
                // Spinner element type
                spinner = (Spinner) rootView.findViewById(R.id.data_type_spinner);
                editData[0] = spinner.getSelectedItem().toString();

                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_detail_1);
                    editData[1] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_detail_2);
                    editData[2] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_detail_3);
                    editData[3] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_detail_4);
                    editData[4] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_detail_5);
                    editData[5] = edit.getText().toString();
                }

                break;
            case 2:
                editData = new String[5];

                spinner = (Spinner) rootView.findViewById(R.id.location_spinner);
                editData[4] = spinner.getSelectedItem().toString();

                //set edi text by old data
                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_node_2);
                    editData[1] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_node_3);
                    editData[2] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_node_4);
                    editData[3] = edit.getText().toString();
                }

                break;
            case 3:
                editData = new String[2];
                //set edi text by old data
                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_location_1);
                    editData[0] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_location_2);
                    editData[1] = edit.getText().toString();
                }
                break;
            case 4:
                editData = new String[5];

                // PIN
                edit = (EditText) rootView.findViewById(R.id.data_group_1);
                editData[0] = edit.getText().toString();

                // Spinner element node
                spinner = (Spinner) rootView.findViewById(R.id.node_spinner);
                power_node_id = dataFromDatabase[2].get(spinner.getSelectedItemPosition()).getId();
                editData[1] = spinner.getSelectedItem().toString();

                // Spinner element location
                spinner = (Spinner) rootView.findViewById(R.id.location_spinner_2);
                editData[2] = spinner.getSelectedItem().toString();
                location_id = dataFromDatabase[3].get(spinner.getSelectedItemPosition()).getId();

                spinner = (Spinner) rootView.findViewById(R.id.on_off_spinner);
                editData[3] = spinner.getSelectedItem().toString();
                Log.d("aaa", editData[3]);

                edit = (EditText) rootView.findViewById(R.id.data_group_3);
                editData[4] = edit.getText().toString();

                break;
            case 5:
                editData = new String[4];

                // Spinner element detail
                spinner = (Spinner) rootView.findViewById(R.id.detail_spinner);
                editData[1] = spinner.getSelectedItem().toString();

                // Spinner element group
                spinner = (Spinner) rootView.findViewById(R.id.group_spinner);
                editData[2] = spinner.getSelectedItem().toString();


                if(ownData != null) {
                    edit = (EditText) rootView.findViewById(R.id.data_device_1);
                    editData[0] = edit.getText().toString();
                    edit = (EditText) rootView.findViewById(R.id.data_device_2);
                    editData[3] = edit.getText().toString();
                }
                break;
        }

        //addData[] store data follow attribute as String.

    }




}
