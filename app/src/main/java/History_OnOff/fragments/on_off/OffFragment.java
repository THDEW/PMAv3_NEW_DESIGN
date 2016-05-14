package History_OnOff.fragments.on_off;

/**
 * Created by my131 on 26/4/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import History_OnOff.adapter.RecyclerViewDataAdapter;
import History_OnOff.fragments.OnOffFragment;
import History_OnOff.model.SectionDataModel;
import History_OnOff.model.SingleItemModel;
import adapter.DividerItemDecoration;


public class OffFragment extends Fragment{
    private FragmentActivity myContext;
    ArrayList<SectionDataModel> allSampleData;
    FragmentManager fm;

    RecyclerView my_recycler_view;

    private String clientHandle;
    private Connection connection;

    private String jall;
    private Bundle bundleoff = null;

    public OffFragment() {
        // Required empty public constructor
    }


    public OffFragment(String clientHandle) {
        this.clientHandle = clientHandle;
        connection = Connections.getInstance(getActivity()).getConnection(clientHandle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_off, container, false);

        fm = getFragmentManager();

        my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        createDummyData(OnOffFragment.bundle);

        my_recycler_view.setHasFixedSize(true);

//        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm, false);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));


        return rootView;
    }

    public void createDummyData() {
        allSampleData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {

            double[] test = new double[5];

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Location " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel(1,"Item " + j, test, "a"));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    public void createDummyData(Bundle bundle) {
        Log.v("offFrag", "dummydata");
        JSONArray jsonArray = null;


        try {
            jsonArray = new JSONArray(bundle.getString("currentStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v(""+jsonArray.length(),jsonArray.toString()+"");


        Log.v("offFrag","dummydata");
        int amountOfLocation = jsonArray.length(); // get size location มา
        int[] amountOfDevice = new int[amountOfLocation];
        String[] locationName = new String[amountOfLocation]; // get location name มา
        Log.v("offFrag","dummydata1");
        for(int i = 0; i < amountOfLocation; i++){
            JSONObject jsonObject = null;
            JSONArray jsonArray1 = null;
            try {
                jsonObject = (JSONObject) jsonArray.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                locationName[i] = jsonObject.getString("location_name");
                jsonArray1 = jsonObject.getJSONArray("off");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            amountOfDevice[i] = jsonArray1.length(); // get size ของ device ในแต่ละ location มา
        }
        Log.v("offFrag","dummydata2");


        allSampleData = new ArrayList<>();
        allSampleData.clear();

        ArrayList<int[]> deviceID = new ArrayList<>();
        ArrayList<String[]> deviceName = new ArrayList<>();
        ArrayList<double[]> devicePower = new ArrayList<>();
        ArrayList<String[]> deviceUsageTime = new ArrayList<>();


        for(int i = 0; i < amountOfLocation; i++){

            JSONArray jsonArray1 = null;
            JSONObject jsonObject = null;

            try {
                jsonArray1 = new JSONArray(bundle.getString("currentStatus"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                jsonObject = (JSONObject) jsonArray1.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int[] deviceIDTemp = new int[amountOfDevice[i]];
            String[] deviceNameTemp = new String[amountOfDevice[i]];// get device name in each location มา
<<<<<<< HEAD
            double[] devicePowerTemp = new double[amountOfDevice[i]];// get device energy   มา
            String[] deviceUsageTimeTemp = new String[amountOfDevice[i]];// get device time  มา
=======
            double[] devicePowerTemp = new double[amountOfDevice[i]];// get device power   มา
>>>>>>> 6b5d0a63f2c49749dcfdd8fd76d7d5be58c46089

            JSONArray jsonArray2 = null;
            try {
                jsonArray2 = jsonObject.getJSONArray("off");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int j=0; j<amountOfDevice[i];j++)
            {
                JSONObject jsonObject1 = null;

                try {

                    jsonObject1 = (JSONObject) jsonArray2.get(j);
                    deviceIDTemp[j] = Integer.parseInt(jsonObject1.getString("group_of_device_id"));
                    deviceNameTemp[j] = jsonObject1.getString("name")+ ":"+jsonObject1.getString("pin");
                    devicePowerTemp[j] = (Double.parseDouble(jsonObject1.getString("sum_energy_today"))/1000)/3600;
                    deviceUsageTimeTemp[j] = "0";
                    Log.v("offfrag",jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            deviceID.add(deviceIDTemp);
            deviceName.add(deviceNameTemp);
            devicePower.add(devicePowerTemp);
<<<<<<< HEAD
            deviceUsageTime.add(deviceUsageTimeTemp);
            Log.v("offFrag", "dummydata3");
=======
>>>>>>> 6b5d0a63f2c49749dcfdd8fd76d7d5be58c46089
        }


        for (int i = 0; i < amountOfLocation; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(locationName[i]);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            Log.v("offFrag", "dummydata4");
            for (int j = 0; j < amountOfDevice[i]; j++) {
<<<<<<< HEAD
                singleItem.add(new SingleItemModel(deviceID.get(i)[j],deviceName.get(i)[j], new double[]{devicePower.get(i)[j]},  deviceUsageTime.get(i)[j]));
=======
                singleItem.add(new SingleItemModel(deviceName.get(i)[j], new double[]{devicePower.get(i)[j]}));
>>>>>>> 6b5d0a63f2c49749dcfdd8fd76d7d5be58c46089
            }
            Log.v("offFrag", "dummydata5");
            dm.setAllItemsInSection(singleItem);
            Log.v("offFrag", "dummydata6");
            allSampleData.add(dm);
            Log.v("offFrag", "dummydata7");
        }

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm, true,connection);
        Log.v("offFrag", "dummydata8");

        my_recycler_view.setAdapter(adapter);
        Log.v("offFrag", "dummydata9");



    }

    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
