package activity;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.android.service.sample.MqttCallbackHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import About.AboutFragment;
import ElectricityCost.ElectricityBillFragment;
import History_OnOff.fragments.LocationFragment;
import History_OnOff.fragments.OnOffFragment;
import Setting.SettingFragments;
import Statistic.StatisticFragment;


public class Home extends  AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    //private MqttAndroidClient client;
    private String clientHandle = null;
    private Connection connection;
    private ChangeListener changeListener;

    private Home home = this;

    private int show = 0;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    public static int page = 0;
    public static int menu_statistic = 0;

    public static boolean login = false;

    public static boolean[] expanded = new boolean[1];

    OnOffFragment onOffFragment = null;
    LocationFragment locationFragment = null;
    StatisticFragment statisticFragment = null;
    ElectricityBillFragment electricityBillFragment = null;
    SettingFragments settingFragments = null;
    AboutFragment aboutFragment = null;
    public static FragmentManager fragmentManagerCancel;

    //String title = getString(R.string.app_name);
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        changeListener = new ChangeListener();
        clientHandle = getIntent().getStringExtra("handle");

        connection = Connections.getInstance(this).getConnection(clientHandle);
        connection.registerChangeListener(changeListener);

        try {

            connection.getClient().subscribe("server/#",1);

        } catch (MqttException e) {
            e.printStackTrace();
        }


        mToolbar = (Toolbar) findViewById(R.id.toolbars);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fragmentManagerCancel = getSupportFragmentManager();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        // display the first navigation drawer view on app launch
        onOffFragment = new OnOffFragment(clientHandle);
        locationFragment = new LocationFragment(clientHandle);
        statisticFragment = new StatisticFragment(clientHandle);
        electricityBillFragment = new ElectricityBillFragment(clientHandle);
        settingFragments = new SettingFragments(clientHandle);
        aboutFragment = new AboutFragment();



        displayView(5);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(page == 2){
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_statistic_devices){
            menu_statistic = 0;
            if(StatisticFragment.bundle != null) {
                statisticFragment.updateStatistic(this, statisticFragment.getCurrentPosition(), StatisticFragment.bundle);
            }
            return true;
        }
        if (id == R.id.action_statistic_location){
            menu_statistic = 1;
            if(StatisticFragment.bundle != null) {
                statisticFragment.updateStatistic(this, statisticFragment.getCurrentPosition(), StatisticFragment.bundle);
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {


        displayView(position);
    }

    private void displayView(int position) {

        if(position != 0 && position != 1) {
            LocationFragment.data.clear();
        }

        String title = getString(R.string.app_name);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String topic = null;
        String message = null;
        int qos = 0;
        boolean retained = false;

        String[] args = new String[2];

        switch (position) {
            case 0:
                if (page != 0){
                    onOffFragment.update(home);
                }
                topic = "android/currentStatus";
                message = "getCurrentStatus";
                qos = 0;
                retained = false;

                args = new String[2];
                args[0] = message;
                args[1] = topic+";qos:"+qos+";retained:"+retained;

                fragmentTransaction.replace(R.id.container_body, onOffFragment);
                title = getString(R.string.title_home);
                getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                page = 0;

                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(this, ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }


                break;
            case 1:
                if(page != 1) {
                    LocationFragment.data = new ArrayList<>();
                }
                fragmentTransaction.replace(R.id.container_body, locationFragment);
                title = getString(R.string.title_Location);
                page = 1;
                getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                topic = "android/history";
                message = "getHistory";
                qos = 0;
                retained = false;

                args = new String[2];
                args[0] = message;
                args[1] = topic+";qos:"+qos+";retained:"+retained;

                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(this, ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }




                break;
            case 2:

                topic = "android/statistic";
                message = "getStatistic";
                qos = 0;
                retained = false;

                args = new String[2];
                args[0] = message;
                args[1] = topic+";qos:"+qos+";retained:"+retained;



                if (page != 2){
                    statisticFragment.updateStatistic(this, statisticFragment.getCurrentPosition());
                }
                fragmentTransaction.replace(R.id.container_body, statisticFragment);
                title = getString(R.string.title_statistic);
                page = 2;
                getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(this, ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                break;
            case 3:

                topic = "android/electricityBill";
                message = "getElectricityCost";
                qos = 0;
                retained = false;

                args = new String[2];
                args[0] = message;
                args[1] = topic+";qos:"+qos+";retained:"+retained;

                fragmentTransaction.replace(R.id.container_body, electricityBillFragment);
                title = "ElectricBill";
                page = 3;

                getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(this, ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                break;
            case 4:
                topic = "android/settings";
                message = "getSettings";
                qos = 0;
                retained = false;

                args = new String[2];
                args[0] = message;
                args[1] = topic+";qos:"+qos+";retained:"+retained;

                fragmentTransaction.replace(R.id.container_body, settingFragments);
                title = "Settings";
                page = 4;

                getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(this, ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                break;

            case 5:
                fragmentTransaction.replace(R.id.container_body, aboutFragment);
                title = "About";
                page = 5;
                getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }


    }



    @Override
    public void onBackPressed()
    {

    }


    private class ChangeListener implements PropertyChangeListener {


        String title = null;
        @Override
        public void propertyChange(PropertyChangeEvent event) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            View view = null;
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;
            JSONParser parser = new JSONParser();
            if(event.getPropertyName().equals("authenticate"))
            {
                //creates bundle to get bundle from connection
                Bundle bundle = new Bundle();
                String[] types = new String[]{"device_type","device_detail","power_node","location","group_of_device","device"};
                String jall = connection.getBundle().getString("settings/authenticate");
                bundle = connection.getBundle();
                int checkLogin = 0;


                    try {
                        jsonObject = new JSONObject(jall);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsonArray =(JSONArray) jsonObject.get("countid");
                        jsonObject = (JSONObject) jsonArray.get(0);
                        checkLogin = Integer.parseInt(jsonObject.getString("countid"));
                        Log.v(checkLogin+" asdf","working");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                try {
                    jsonObject = new JSONObject(jall);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonArray =(JSONArray) jsonObject.get("device_detail");
                    jsonObject = (JSONObject) jsonArray.get(0);

                    Log.v(jsonObject.getString("brand"),"working222");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(checkLogin==1) login = true;
                else login = false;

                fragmentTransaction.replace(R.id.container_body, settingFragments);
                title = "Settings";
                home.getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                view = settingFragments.getView();

                if (Home.login) {
                    loggedIn(view,bundle);
                } else {
                    notLoggedIn(view);
                    Toast.makeText(home,"Wrong username or password",Toast.LENGTH_SHORT).show();
                }

            }

            else if(event.getPropertyName().equals("settings"))
            {

                String[] types = new String[]{"device_type","device_detail","power_node","location","group_of_device","device"};
                String jall = connection.getBundle().getString("settings/authenticate");
                Bundle bundle = connection.getBundle();
                fragmentTransaction.replace(R.id.container_body, settingFragments);
                title = "Settings";
                home.getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                view = settingFragments.getView();

                if (Home.login) {
                    loggedIn(view,bundle);
                } else {
                    notLoggedIn(view);
                }
            }

            else if(event.getPropertyName().equals("electricityBill"))
            {
                Bundle bundle;
                bundle = connection.getBundle();
                Log.v("home","efrag");
                fragmentTransaction.replace(R.id.container_body, electricityBillFragment);
                fragmentTransaction.commit();

                electricityBillFragment.prepareListData(bundle);
            }
            else if(event.getPropertyName().equals("currentStatus"))
            {
                Bundle bundle;
                bundle = connection.getBundle();
                Log.v("currentStatus", "efrag");


                //Log.d("Home", bundle.toString());
                fragmentTransaction.replace(R.id.container_body, onOffFragment);
                fragmentTransaction.commit();
                Log.v("currentStatus", "efrag1");
                onOffFragment.update(home, bundle);
                Log.v("currentStatus", "efrag3");

            }
            else if(event.getPropertyName().equals("statistic"))
            {
                Bundle bundle;
                bundle = connection.getBundle();
                Log.v("statistic", "efrag");


                //Log.d("Home", bundle.toString());
                fragmentTransaction.replace(R.id.container_body, statisticFragment);
                fragmentTransaction.commit();
                Log.v("statistic", "efrag1");
                statisticFragment.updateStatistic(home, statisticFragment.getCurrentPosition(), bundle);
                Log.v("statistic", "efrag3");

            }
            else if(event.getPropertyName().equals("history"))
            {
                Bundle bundle;
                bundle = connection.getBundle();
                Log.v("history", "efrag");


                //Log.d("Home", bundle.toString());
                fragmentTransaction.replace(R.id.container_body, locationFragment);
                fragmentTransaction.commit();
                Log.v("history", "efrag1");
                locationFragment.createDummyData(bundle);
                Log.v("history", "efrag2");
            }




        }

        public void loggedIn(View view, Bundle bundle)
        {
            //Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
            RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
            my_recycler_view.setVisibility(View.VISIBLE);
            Button logout = (Button) view.findViewById(R.id.logout);
            logout.setVisibility(View.VISIBLE);

            Button relogin = (Button) view.findViewById(R.id.reloginbt);
            relogin.setVisibility(View.INVISIBLE);
            TextView plslogin = (TextView) view.findViewById(R.id.plsLogin);
            plslogin.setVisibility(View.INVISIBLE);

            try {
                settingFragments.createDummyData(bundle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void notLoggedIn(View view)
        {
            RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
            my_recycler_view.setVisibility(View.INVISIBLE);
            Button logout = (Button) view.findViewById(R.id.logout);
            logout.setVisibility(View.INVISIBLE);

            Button relogin = (Button) view.findViewById(R.id.reloginbt);
            relogin.setVisibility(View.VISIBLE);
            TextView plslogin = (TextView) view.findViewById(R.id.plsLogin);
            plslogin.setVisibility(View.VISIBLE);
        }

    }



}
