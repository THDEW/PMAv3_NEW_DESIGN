package Setting;

/**
 * Created by my131 on 1/5/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.eclipse.paho.android.service.sample.MqttCallbackHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import activity.Home;
import adapter.DividerItemDecoration;


public class SettingFragments extends Fragment {

    private FragmentActivity myContext;
    ArrayList<TableDataModel> allSampleData;
    FragmentManager fm;
    String[] type;

    View rootView;

    RecyclerView my_recycler_view;
    SettingFragments settingFragments = this;

    private String clientHandle = null;
    private Connection connection = null;
    private ChangeListener changeListener = new ChangeListener();


    private Bundle temp = null;


    public SettingFragments() {
        // Required empty public constructor

    }

    public SettingFragments(String clientHandle) {
        this.clientHandle = clientHandle;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = Connections.getInstance(getActivity()).getConnection(clientHandle);
        connection.registerChangeListener(changeListener);
        //connection.registerChangeListener(changeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Log.d("MonthlyFragment", "Monthly was created again");


        rootView = inflater.inflate(R.layout.fragment_setting, container, false);


        allSampleData = new ArrayList<>();
        Log.v("setting", "create");

        my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        Button reloginbt = (Button) rootView.findViewById(R.id.reloginbt);
        Button logout = (Button) rootView.findViewById(R.id.logout);

        fm = getFragmentManager();

        reloginbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginDialog dialog = new LoginDialog(rootView, clientHandle);
                dialog.show(fm, "Login");
                dialog.setCancelable(false);

            }
        });

        type = new String[]{"device_type","device_detail","power_node","location","group_of_device","device"};

        if (!Home.login) {

            LoginDialog dialog = new LoginDialog(rootView, clientHandle);
            dialog.show(fm, "Login");
            dialog.setCancelable(false);

        } else {
            my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            my_recycler_view.setVisibility(View.VISIBLE);

            logout.setVisibility(View.VISIBLE);
        }


        //createDummyData(this.getArguments());

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewForSettingAdapter adapter = new RecyclerViewForSettingAdapter(myContext, allSampleData, fm,connection);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));

        my_recycler_view.setAdapter(adapter);


        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
                my_recycler_view.setVisibility(View.INVISIBLE);
                Button logoutinside = (Button) rootView.findViewById(R.id.logout);
                logoutinside.setVisibility(View.INVISIBLE);


                Button relogin = (Button) rootView.findViewById(R.id.reloginbt);
                relogin.setVisibility(View.VISIBLE);
                TextView plslogin = (TextView) rootView.findViewById(R.id.plsLogin);
                plslogin.setVisibility(View.VISIBLE);

                Home.login = false;

            }
        });

        return rootView;
    }


    public void createDummyData(Bundle bundle) throws JSONException {
        Log.v("settings/authenticate", "createdummy");

        String[] types = new String[]{"device_type","device_detail","power_node","location","group_of_device","device"};
        String jall = bundle.getString("settings/authenticate");
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        //Toast.makeText(getActivity(),jall,Toast.LENGTH_SHORT).show();

        allSampleData = new ArrayList<>();
        allSampleData.clear();

        try {
            jsonObject = new JSONObject(jall);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0 ; i < types.length; i++) {

            TableDataModel dm = new TableDataModel();

            dm.setHeaderTitle(types[i]);

            ArrayList<ItemDataModel> singleItem = new ArrayList<>();

            //Toast.makeText(getActivity(),""+i,Toast.LENGTH_SHORT).show();

            JSONArray jsonArray1 = null;
            try {
                jsonArray1 =(JSONArray) jsonObject.get(types[i]);

                //Toast.makeText(getActivity(),""+jsonArray1.length(),Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(),jsonArray1.toString() +" "+ jsonObject.get(types[i]),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < jsonArray1.length(); j++) {
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 =(JSONObject) jsonArray1.get(j);
                    //Toast.makeText(getActivity(),jsonObject1.toString() +" "+ j,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // ใส่ข้อมูลตาม ชื่อ column ตามเคส ไล่ตาม "Data Type", "Data Detail", "Power Node" ,"Location" ,"Group of Device", "Device"
                switch (i) {
                    case 0:
                        //Log.v(jsonObject.getString("device_type_id"),""+i);
                        singleItem.add(new ItemDataModel(jsonObject1.getString("type_name"), type[i]));
                        singleItem.get(j).setId(Integer.parseInt(jsonObject1.getString("device_type_id")));
                        singleItem.get(j).addData(jsonObject1.getString("type_name"));
                        singleItem.get(j).addData(jsonObject1.getString("descr"));
                        break;
                    case 1:
                        singleItem.add(new ItemDataModel(jsonObject1.getString("brand")+" "+jsonObject1.getString("model"), type[i]));
                        singleItem.get(j).setId(Integer.parseInt(jsonObject1.getString("device_detail_id")));
                        singleItem.get(j).addData(jsonObject1.getString("type_name"));
                        singleItem.get(j).addData(jsonObject1.getString("brand"));
                        singleItem.get(j).addData(jsonObject1.getString("model"));
                        singleItem.get(j).addData(jsonObject1.getString("power_watt"));
                        singleItem.get(j).addData(jsonObject1.getString("btu"));
                        singleItem.get(j).addData(jsonObject1.getString("descr"));
                        break;
                    case 2:
                        singleItem.add(new ItemDataModel(jsonObject1.getString("name") + ":" + jsonObject1.getString("ip_address"), type[i]));
                        singleItem.get(j).setId(Integer.parseInt(jsonObject1.getString("power_node_id")));
                        singleItem.get(j).addData(jsonObject1.getString("ip_address"));
                        singleItem.get(j).addData(jsonObject1.getString("name"));
                        singleItem.get(j).addData(jsonObject1.getString("location_name"));
                        singleItem.get(j).addData(jsonObject1.getString("descr"));
                        break;
                    case 3:
                        singleItem.add(new ItemDataModel(jsonObject1.getString("location_name"), type[i]));
                        singleItem.get(j).setId(Integer.parseInt(jsonObject1.getString("location_id")));
                        singleItem.get(j).addData(jsonObject1.getString("location_name"));
                        singleItem.get(j).addData(jsonObject1.getString("descr"));
                        break;
                    case 4:
                        singleItem.add(new ItemDataModel(jsonObject1.getString("name") + ":" + jsonObject1.getString("pin"), type[i]));
                        singleItem.get(j).setId(Integer.parseInt(jsonObject1.getString("group_of_device_id")));
                        singleItem.get(j).addData(jsonObject1.getString("pin"));
                        singleItem.get(j).addData(jsonObject1.getString("name"));
                        singleItem.get(j).addData(jsonObject1.getString("status"));
                        singleItem.get(j).addData(jsonObject1.getString("descr"));
                        break;
                    case 5:
                        singleItem.add(new ItemDataModel(jsonObject1.getString("device_name"), type[i]));
                        singleItem.get(j).setId(Integer.parseInt(jsonObject1.getString("device_id")));
                        singleItem.get(j).addData(jsonObject1.getString("device_name"));
                        singleItem.get(j).addData(jsonObject1.getString("brand")+jsonObject1.getString("model"));
                        singleItem.get(j).addData(jsonObject1.getString("pin"));
                        singleItem.get(j).addData(jsonObject1.getString("descr"));
                        break;
                }

            }

            dm.setAllItemsInSection(singleItem);
            allSampleData.add(dm);

        }
        RecyclerViewForSettingAdapter adapter = new RecyclerViewForSettingAdapter(myContext, allSampleData, fm, connection);

        my_recycler_view.setAdapter(adapter);

    }


    public View getView() {

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myContext = (FragmentActivity) activity;
    }



    private class ChangeListener implements PropertyChangeListener {


        String title = null;
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            View view = null;
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            JSONParser parser = new JSONParser();

            String topic = "android/settings";
            String message = "getSettings";
            int qos = 0;
            boolean retained = false;

            String[] args = new String[2];
            args[0] = message;
            args[1] = topic+";qos:"+qos+";retained:"+retained;

            if(event.getPropertyName().equals("addDeviceType"))
            {
                String result = connection.getBundle().getString("settings/addData/device_type");
                Log.v("adddevice","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
            if(event.getPropertyName().equals("addDeviceDetail"))
            {
                String result = connection.getBundle().getString("settings/addData/device_detail");
                Log.v("adddevicedetail","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
            if(event.getPropertyName().equals("addPowerNode"))
            {
                String result = connection.getBundle().getString("settings/addData/power_node");
                Log.v("addpowernode","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
            if(event.getPropertyName().equals("addLocation"))
            {
                String result = connection.getBundle().getString("settings/addData/location");
                Log.v("addlocation","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
            if(event.getPropertyName().equals("addGroupOfDevice"))
            {
                String result = connection.getBundle().getString("settings/addData/group_of_device");
                Log.v("addgroupofdevice","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
            if(event.getPropertyName().equals("addDevice"))
            {
                String result = connection.getBundle().getString("settings/addData/device");
                Log.v("adddevice","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            if(event.getPropertyName().equals("editDeviceType"))
            {
                String result = connection.getBundle().getString("settings/editData/device_type");
                Log.v("editdevicetype","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
            if(event.getPropertyName().equals("editDeviceDetail"))
            {
                String result = connection.getBundle().getString("settings/editData/device_detail");
                Log.v("editdevicedetail","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
            if(event.getPropertyName().equals("editLocation"))
            {
                String result = connection.getBundle().getString("settings/editData/location");
                Log.v("editlocation","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
            if(event.getPropertyName().equals("editGroupOfDevice"))
            {
                String result = connection.getBundle().getString("settings/editData/group_of_device");
                Log.v("editgroupofdevice","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
            if(event.getPropertyName().equals("editDevice"))
            {
                String result = connection.getBundle().getString("settings/editData/device");
                Log.v("editdevice","rowdata");
                Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }






        }



        }

}

