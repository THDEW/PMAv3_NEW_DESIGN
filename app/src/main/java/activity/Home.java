package activity;

import android.app.ListActivity;
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.regex.Pattern;

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
        Toast.makeText(this,clientHandle,Toast.LENGTH_SHORT).show();
        connection = Connections.getInstance(this).getConnection(clientHandle);
        //connection.getClient().setCallback(new MqttCallbackHandler(this,clientHandle));
        connection.registerChangeListener(changeListener);
        //if(connection== null) Toast.makeText(this,"null",Toast.LENGTH_SHORT).show();


        try {

            connection.getClient().subscribe("server/#",1);
            Toast.makeText(this,"subscribe",Toast.LENGTH_SHORT).show();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        //Toast.makeText(this,clientHandle,Toast.LENGTH_SHORT).show();




        mToolbar = (Toolbar) findViewById(R.id.toolbars);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fragmentManagerCancel = getSupportFragmentManager();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        // display the first navigation drawer view on app launch
        onOffFragment = new OnOffFragment();
        locationFragment = new LocationFragment();
        statisticFragment = new StatisticFragment();
        electricityBillFragment = new ElectricityBillFragment();
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
            statisticFragment.updateStatistic(this, statisticFragment.getCurrentPosition());
            return true;
        }
        if (id == R.id.action_statistic_location){
            menu_statistic = 1;
            statisticFragment.updateStatistic(this,statisticFragment.getCurrentPosition());
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

        switch (position) {
            case 0:
                onOffFragment.update(this);
                fragmentTransaction.replace(R.id.container_body, onOffFragment);
                title = getString(R.string.title_home);
                page = 0;
                break;
            case 1:
                fragmentTransaction.replace(R.id.container_body, locationFragment);
                title = getString(R.string.title_Location);
                page = 1;
                break;
            case 2:
                if (page != 2){
                    statisticFragment.updateStatistic(this, statisticFragment.getCurrentPosition());
                }
                fragmentTransaction.replace(R.id.container_body, statisticFragment);
                title = getString(R.string.title_statistic);
                page = 2;
                break;
            case 3:
                fragmentTransaction.replace(R.id.container_body, electricityBillFragment);
                title = "Electricity Bill";
                page = 3;
                break;
            case 4:
                String topic = "android/settings";
                String message = "getSettings";
                int qos = 0;
                boolean retained = false;

                String[] args = new String[2];
                args[0] = message;
                args[1] = topic+";qos:"+qos+";retained:"+retained;

                try {
                    connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(this, ActionListener.Action.PUBLISH, clientHandle, args));
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                fragmentTransaction.replace(R.id.container_body, settingFragments);
                title = "Settings";
                page = 4;
                break;
            case 5:
                aboutFragment = new AboutFragment(clientHandle);
                fragmentTransaction.replace(R.id.container_body, aboutFragment);
                title = "About";
                page = 5;
                break;
            default:
                break;
        }
        //connection = Connections.getInstance(this).getConnection(clientHandle);
        //Toast.makeText(this,connection.getTest(),Toast.LENGTH_SHORT).show();

        getSupportActionBar().setTitle(title);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();


        //Register receivers again
        if(connection!= null)
        {
            connection.getClient().registerResources(this);
            connection.getClient().setCallback(new MqttCallbackHandler
                    (this, connection.getClient().getServerURI() + connection.getClient().getClientId()));
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

            if(event.getPropertyName().equals("authenticate"))
            {
                Bundle bundle = new Bundle();
                String[] types = new String[]{"device_type","device_detail","power_node","location","group_of_device","device"};
                String jall = connection.getBundle().getString("authenticate");
                int checkLogin = 0;
                /*
                String[] split = jall.split(Pattern.quote("|"));

                JSONArray jsonArray = null;
                JSONObject jsonObject = null;


                try {
                     jsonArray  = new JSONArray(new String(split[0]));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    jsonObject = (JSONObject) jsonArray.get(0);
                    String countid = jsonObject.getString("countid");

                    checkLogin = Integer.parseInt(countid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i =0; i < types.length ;i++)
                {
                    bundle.putString(types[i],split[i+1]);
                    Toast.makeText(home,"asdfds"+i,Toast.LENGTH_SHORT).show();
                }
                */
               checkLogin = Integer.parseInt(jall);



                if(checkLogin==1) login = true;
                else login = false;


                fragmentTransaction.replace(R.id.container_body, settingFragments);
                title = "Settings";
                home.getSupportActionBar().setTitle(title);
                fragmentTransaction.commit();

                view = settingFragments.getView();

                //login = true;

                if (Home.login) {
                    loggedIn(view,bundle);
                } else {
                    notLoggedIn(view);
                }
            }



        }

        public void loggedIn(View view, Bundle bundle)
        {
            Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
            RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
            my_recycler_view.setVisibility(View.VISIBLE);
            Button logout = (Button) view.findViewById(R.id.logout);
            logout.setVisibility(View.VISIBLE);


            Button relogin = (Button) view.findViewById(R.id.reloginbt);
            relogin.setVisibility(View.INVISIBLE);
            TextView plslogin = (TextView) view.findViewById(R.id.plsLogin);
            plslogin.setVisibility(View.INVISIBLE);


            settingFragments.createDummyData();
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
