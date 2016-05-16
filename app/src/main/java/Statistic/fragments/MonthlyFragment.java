package Statistic.fragments;

/**
 * Created by my131 on 28/4/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import Statistic.ApplianceAdapter;
import Statistic.ApplianceModel;
import Statistic.StatisticFragment;
import activity.Home;
import adapter.DividerItemDecoration;


public class MonthlyFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentActivity myContext;
    private ArrayList<ApplianceModel> applianceModelListeList = new ArrayList<ApplianceModel>();
    private RecyclerView recyclerView;
    private ApplianceAdapter mAdapter;
    private int onspinnerselected;
    TextView buttonToChange;

    private Bundle bundle = null;

    public MonthlyFragment() {
        // Required empty public constructor
    }

    public MonthlyFragment(Bundle bundle) {
        this.bundle = bundle;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monthly, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.sort_by_spinner_monthly);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> SpinnerAdapter = ArrayAdapter.createFromResource(myContext, R.array.sort_by_array, android.R.layout.simple_spinner_item);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(SpinnerAdapter);
        buttonToChange = (TextView) rootView.findViewById(R.id.telling_location_device_monthly);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_monthly);
        mAdapter = new ApplianceAdapter(applianceModelListeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        if(applianceModelListeList!=null){
            applianceModelListeList.clear();
        }

        prepareApplianceData(StatisticFragment.bundle);

        if(Home.menu_statistic==0){
            buttonToChange.setText("Devices");
        } else if(Home.menu_statistic==1){
            buttonToChange.setText("Locations");
        }
        return rootView;
    }
    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
    /*
    //numsizeนี่คือจำนวนของดีไว้เมิงอะหรือโลเคชั่น
    private void prepareApplianceData(int numSize) {
        int start, start0;
        long elecusages = 0;
        long elecusageLocation = 0;
        if (Home.menu_statistic==0) {
            for (start = 0; start < numSize; start++) {
                //ตรงนี้นะสัสApplianceModel(device name, กำลังไฟฟ้าที่ใช้,วันที่เปนสตริงนะ,constant string)
                ApplianceModel appliances = new ApplianceModel("Name: Air-Conditioner " + start, elecusages++, "03/02/2016", "Electricity's Usage: ");
                applianceModelListeList.add(appliances);
            }
        } else if (Home.menu_statistic==1) {
            for (start0 = 0; start0 < numSize - 15; start0++) {
                //ตรงนี้นะสัสApplianceModel(location name, กำลังไฟฟ้าที่ใช้,วันที่เปนสตริงนะ,constant string)
                ApplianceModel locationapp = new ApplianceModel("Name: Location " + start0, elecusageLocation++, "05/12/2016", "Electricity's Usage: ");
                applianceModelListeList.add(locationapp);
            }
        }
        mAdapter.notifyDataSetChanged();

    }
    */

    private void prepareApplianceData(Bundle bundle) {
        Log.v("preparemonthly", "month");

        String jall = bundle.getString("statistic");

        JSONObject jsonObject = null;
        JSONArray this_month_god = null;
        JSONArray this_month_location = null;

        try {
            jsonObject = new JSONObject(jall);
            this_month_god = jsonObject.getJSONArray("this_month_group_of_device_statistic");
            this_month_location = jsonObject.getJSONArray("this_month_location_statistic");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int numsizeGoD = this_month_god.length();
        int numsizeLocation = this_month_location.length();

        Log.v("monthlyfrag","size god "+numsizeGoD);
        Log.v("monthlyfrag","size loc "+numsizeLocation);



        // แล้วเมิง ก็เอาข้อมูล ของ location กับ device มา split บลาๆๆๆๆๆๆๆ

        // Bundle ที่ส่งมา ก็ เอามาทั้ง Device กะ Location เลย

        // แล้ว เอาไปใส่ตาม พารามิเตอร์ ใน ลูป ข้างล่าง


        // อันนี้ กุ สมมุติ ก็ แแยกๆ ใส่เข้าไป
        String[] deviceName = new String[numsizeGoD];
        String[] devicePower = new String[numsizeGoD];

        for(int i = 0; i< numsizeGoD ; i++)
        {
            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = (JSONObject) this_month_god.get(i);
                deviceName[i] = "Location : "+jsonObject1.getString("location_name")+"\nIP address : "+jsonObject1.getString("ip_address")+"\nPin : "+jsonObject1.getString("pin");
                devicePower[i] = jsonObject1.getString("sum_energy");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.v("monthlyfrag","yay1");


        String[] locationName = new String[numsizeLocation];
        String[] locationPower = new String[numsizeLocation];

        for(int i = 0; i< numsizeLocation ; i++)
        {
            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = (JSONObject) this_month_location.get(i);
                locationName[i] = "Location : "+jsonObject1.getString("location_name");
                locationPower[i] = jsonObject1.getString("sum_energy");
                Log.v("monthlyfrag","yay12 "+locationPower[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.v("monthlyfrag","yay2");


        int start, start0;
        if (Home.menu_statistic==0) {
            for (start = 0; start < numsizeGoD; start++) {
                //ตรงนี้นะสัสApplianceModel(device name, กำลังไฟฟ้าที่ใช้,วันที่เปนสตริงนะ,constant string)
                ApplianceModel appliances = new ApplianceModel(deviceName[start], Double.parseDouble(devicePower[start]), "Electricity Usage (W/h) : ");
                applianceModelListeList.add(appliances);
                Log.v("monthlyfrag", "yay3");
            }
        } else if (Home.menu_statistic==1) {
            for (start0 = 0; start0 < numsizeLocation ; start0++) {
                //ตรงนี้นะสัสApplianceModel(location name, กำลังไฟฟ้าที่ใช้,วันที่เปนสตริงนะ,constant string)
                ApplianceModel locationapp = new ApplianceModel(locationName[start0], Double.parseDouble(locationPower[start0]), "Electricity Usage (W/h) : ");
                applianceModelListeList.add(locationapp);
                Log.v("monthlyfrag", "yay4");
            }
        }
        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        switch (item){
            case "Max Power usage":
                Collections.sort(applianceModelListeList, ApplianceModel.electusageMaxComparator);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                onspinnerselected=0;
                //Toast.makeText(parent.getContext(), "Selected 0: " + item, Toast.LENGTH_LONG).show();
                break;
            case "Min Power usage":
                Collections.sort(applianceModelListeList, ApplianceModel.electusageMinComparator);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                onspinnerselected=1;
                //Toast.makeText(parent.getContext(), "Selected 1: " + item, Toast.LENGTH_LONG).show();
                break;
            /*case 2:
                Toast.makeText(parent.getContext(), "Selected 2: " + item, Toast.LENGTH_LONG).show();
                break;*/
        }


        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}