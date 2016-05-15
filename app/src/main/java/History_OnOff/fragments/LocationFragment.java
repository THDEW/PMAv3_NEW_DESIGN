package History_OnOff.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;

import com.example.senoir.newpmatry1.R;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import History_OnOff.adapter.LegendAdapter;
import History_OnOff.adapter.RecyclerViewDataAdapter;
import History_OnOff.dialogs.GraphSelection;
import History_OnOff.dialogs.TimeSelectionDialog;
import History_OnOff.model.GraphSeriesModel;
import History_OnOff.model.SectionDataModel;
import History_OnOff.model.SingleItemModel;
import adapter.DividerItemDecoration;

/**
 * Created by my131 on 26/4/2559.
 */
public class LocationFragment extends Fragment {

    private FragmentActivity myContext;
    ArrayList<SectionDataModel> allSampleData;
    FragmentManager fm;

    public static DataPoint[] dataPoint;
    public static String[] dataPointName;
    public static int[] dataPointColor;
    public static ArrayList<GraphSeriesModel> data = new ArrayList<>();

    public static boolean[] open;

    public static boolean isBarGraph = true;

    public static boolean isBarGraphSet;

    BarGraphSeries<DataPoint> series;

    ArrayList<LineGraphSeries<DataPoint>> lineSeries;

    public static boolean addNew;

    static GraphView graph;

    static TextView tv;

    LegendAdapter legendAdapter;

    RecyclerViewDataAdapter adapter;

    public static RecyclerView legendView;

    RecyclerView my_recycler_view;

    private String clientHandle;
    private Connection connection;

    TextView amount;

    public static boolean canSelectData = false;

    public LocationFragment() {
        // Required empty public constructor
    }

    public LocationFragment(String clientHandle) {
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
        final View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        allSampleData = new ArrayList<>();
        //createDummyData();

        fm = getFragmentManager();

        amount = (TextView) rootView.findViewById(R.id.amountOfdata);

        addNew = false;

        my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));

        legendView = (RecyclerView) rootView.findViewById(R.id.legend);
        legendView.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));
        legendAdapter = new LegendAdapter(dataPointName, dataPointColor);
        legendView.setAdapter(legendAdapter);

        //graph view section
        graph = (GraphView) rootView.findViewById(R.id.graph);

        lineSeries = new ArrayList<>();

        if (isBarGraph) {
            series = new BarGraphSeries<>();

            graph.addSeries(series);

            series.setSpacing(20);

            isBarGraphSet = true;

            // styling
            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                }
            });

            graph.getSecondScale().setMinY(0);
            graph.getSecondScale().setMaxY(getMax() + 5);
        }

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.WHITE);

        final DecimalFormat d = new DecimalFormat("0");

        graph.getSecondScale().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(Double.parseDouble(d.format(value)), isValueX) + " kW/hr   x";
                }
            }
        });

        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Energy Consumption (kW/hr)");
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setBackgroundColor(Color.parseColor("#4e739e"));

        final Handler mHandler = new Handler();

        Runnable mTimer1 = new Runnable() {
            @Override
            public void run() {

                    if(addNew) {
                        setDataPoint();

                        legendAdapter = new LegendAdapter(dataPointName, dataPointColor);
                        legendView.setAdapter(legendAdapter);
                    }

                    if (dataPoint != null) {
                        series.resetData(dataPoint);
                    }
                    if(data.size() != 0) {
                        graph.getViewport().setMinY(0);
                        graph.getViewport().setMaxY(getMax() + getMax() * 0.2);
                        graph.getSecondScale().setMinY(0);
                        graph.getSecondScale().setMaxY(getMax() + getMax() * 0.2);
                    } else {
                        graph.getViewport().setMinY(0);
                        graph.getViewport().setMaxY(5);
                        graph.getSecondScale().setMinY(0);
                        graph.getSecondScale().setMaxY(5);
                    }
                    amount.setText("Amount of data is "+data.size());


                mHandler.postDelayed(this, 1);
            }
        };
        mHandler.postDelayed(mTimer1, 300);

        // Time selection
        tv = (TextView) rootView.findViewById(R.id.periodOfTime);

        Button timeSelection = (Button) rootView.findViewById(R.id.time_button);
        timeSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TimeSelectionDialog dialog = new TimeSelectionDialog(myContext, rootView, adapter);
                dialog.show(fm, "Time Selection");

            }
        });

//        Button graphSelection = (Button) rootView.findViewById(R.id.graph_button);
//        graphSelection.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                GraphSelection dialog = new GraphSelection();
//                dialog.show(fm, "Graph Type Selection");
//
//            }
//        });


        TextView periodOfTime = (TextView) rootView.findViewById(R.id.periodOfTime);

        periodOfTime.setText("Please! select time");

        // Inflate the layout for this fragment
        return rootView;
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            double[] test = new double[]{5,3,2,1,4};

            //declare size == amount of location
            open = new boolean[5];
            open[i-1] = false;

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Location " + i); // ชื่อ โลเคชัน

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel(1,"Item " + j, test,"",4));
            }

            dm.setPowerOfLocation(test);

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    public void createDummyData(Bundle bundle) {

        String jall = bundle.getString("history");

        JSONArray jsonArray = null;
        int amountOfLocation = 0; // get size location มา
        try {
            jsonArray = new JSONArray(jall);
            amountOfLocation = jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("location_size",""+amountOfLocation);


        int[] amountOfDevice = new int[amountOfLocation];

        for(int i = 0; i < amountOfLocation; i++){

            open = new boolean[amountOfLocation];
            open[i] = false;

            JSONObject jsonObject = null;
            JSONArray jsonArray1 = null;

            try {
                jsonObject = (JSONObject) jsonArray.get(i);
                jsonArray1 = jsonObject.getJSONArray("value");
                amountOfDevice[i] = jsonArray1.length(); // get size ของ device ในแต่ละ location มา
                Log.v("amountofdevice "+i," "+amountOfDevice[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        String[] locationName = new String[amountOfLocation]; // get location name มา


        ArrayList<String[]> deviceName = new ArrayList<>();
        ArrayList<int[]> deviceId = new ArrayList<>();
        ArrayList<int[]> powerNodeId = new ArrayList<>();
        ArrayList<int[]> locationId = new ArrayList<>();


        for(int i = 0; i < amountOfLocation; i++){
            JSONObject location_name = null;
            try {
                location_name = (JSONObject) jsonArray.get(i);
                locationName[i] = location_name.getString("location_name");
                Log.v("location_name "+i," "+locationName[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            String[] deviceNameTemp = new String[amountOfDevice[i]];// get device name in each location มา
            int[] deviceIdTemp = new int[amountOfDevice[i]];// get device id มา
            int[] powerNodeIdTemp = new int[amountOfDevice[i]];// get power node id มา
            int[] locationIdTemp = new int[amountOfDevice[i]]; // get location id  มา

            JSONObject jsonObject = null;
            JSONArray value = null;
            try {
                jsonObject = (JSONObject) jsonArray.get(i);
                value = jsonObject.getJSONArray("value");
                Log.v("first god", value.toString()+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int j = 0; j<amountOfDevice[i];j++)
            {
                JSONObject eachGoD = null;
                try {
                    eachGoD = (JSONObject) value.get(j);
                    deviceNameTemp[j] = eachGoD.getString("name")+" : "+eachGoD.getString("pin");
                    deviceIdTemp[j] = Integer.parseInt(eachGoD.getString("group_of_device_id"));
                    powerNodeIdTemp[j] = Integer.parseInt(eachGoD.getString("power_node_id"));
                    locationIdTemp[j] = Integer.parseInt(eachGoD.getString("location_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            deviceName.add(deviceNameTemp);
            deviceId.add(deviceIdTemp);
            powerNodeId.add(powerNodeIdTemp);
            locationId.add(locationIdTemp);
            Log.v("here", "after insert");

        }


        for (int i = 0; i < amountOfLocation; i++) {
            Log.v("here", "after insert11");
            SectionDataModel dm = new SectionDataModel(locationName[i]);
            Log.v("here", "after insert22");
            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j < amountOfDevice[i]; j++) {
                singleItem.add(new SingleItemModel(deviceId.get(i)[j] ,deviceName.get(i)[j],locationId.get(i)[j],powerNodeId.get(i)[j]));
                Log.v("here", "after insert33");
            }
            Log.v("here", "after insert44");
            dm.setAllItemsInSection(singleItem);
            Log.v("here", "after insert55");
            allSampleData.add(dm);
            Log.v("here", dm.getAllItemsInSection().size() + "");
            Log.v("here", allSampleData.size()+"");
        }

        adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm, true, connection);
        Log.v("here", "after insert77");
        my_recycler_view.setAdapter(adapter);
        Log.v("here", "after insert88");
    }

    public static void setDataPoint(){

        int count = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getValue() != -1d){
                count++;
            }
        }

        if(isBarGraph) {
            dataPoint = new DataPoint[count];
            dataPointName = new String[count];
            dataPointColor = new int[count];
            count = 0;

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getValue() != -1d) {
                    dataPoint[count] = new DataPoint(count, data.get(i).getValue());

                    if(data.get(i).getIsLocation())
                        dataPointName[count] =  data.get(i).getLocation();
                    else
                        dataPointName[count] =  data.get(i).getDevice();

                    dataPointColor[count] = Color.rgb(count*255/4, (int) Math.abs(data.get(count).getValue()*255/6), 100);

                    count++;
                }
            }
        }
    }

    public double getMax(){

        double max = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getValue() > max){
                max = data.get(i).getValue();
            }
        }

        return max;
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
