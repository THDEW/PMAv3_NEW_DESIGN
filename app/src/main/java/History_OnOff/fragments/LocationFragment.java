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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import com.example.senoir.newpmatry1.R;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.util.ArrayList;

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
    public static ArrayList<Double> data2 = new ArrayList<>();

    public static boolean[] open;

    public static boolean isBarGraph = true;

    public static boolean isBarGraphSet;

    BarGraphSeries<DataPoint> series;

    ArrayList<LineGraphSeries<DataPoint>> lineSeries;

    public static boolean addNew;

    static GraphView graph;

    static TextView tv;

    LegendAdapter legendAdapter;

    public static RecyclerView legendView;

    RecyclerView my_recycler_view;

    public LocationFragment() {
        // Required empty public constructor
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
        createDummyData();

        fm = getFragmentManager();

        addNew = false;

        my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));

        my_recycler_view.setAdapter(adapter);

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

            series.setTitle("Location XXX"); // ไม่เกี่ยว

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
        series.setValuesOnTopColor(Color.RED);

        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Energy Consumption (WATTs)");
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);

        final Handler mHandler = new Handler();

        Runnable mTimer1 = new Runnable() {
            @Override
            public void run() {
                if(isBarGraph) {
                    if(!isBarGraphSet) {
                        graph.removeAllSeries();

                        graph.addSeries(series);

                        isBarGraphSet = true;

//                        graph.getLegendRenderer().setVisible(false);

                    }
                    if(addNew) {
                        setDataPoint();

                        legendAdapter = new LegendAdapter(dataPointName, dataPointColor);
                        legendView.setAdapter(legendAdapter);
                    }

                    if (dataPoint != null) {
                        series.resetData(dataPoint);
                    }
                    graph.getViewport().setMinY(0);
                    graph.getViewport().setMaxY(getMax() + 5);

                    graph.getSecondScale().setMinY(0);
                    graph.getSecondScale().setMaxY(getMax() + 5);


                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getSumValue() > data2.get(i)) {
                            data2.set(i, data2.get(i) + (data.get(i).getSumValue() / 100) * 3);
                        }
                    }


                } else {
                    if(addNew){
                        if(data.size() != 0){
                            setLineSeries();
                            addNew = false;

                            legendAdapter = new LegendAdapter(dataPointName, dataPointColor);
                            legendView.setAdapter(legendAdapter);
//                            graph.getLegendRenderer().setVisible(true);
//                            graph.getLegendRenderer().setFixedPosition(0, 0);
                        }
                    }
                    graph.getViewport().setMinY(0);
                    graph.getViewport().setMaxY(getMaxLine() + 5);

                    graph.getSecondScale().setMinY(0);
                    graph.getSecondScale().setMaxY(getMaxLine() + 5);

                }
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

                TimeSelectionDialog dialog = new TimeSelectionDialog(rootView);
                dialog.show(fm, "Time Selection");

            }
        });

        Button graphSelection = (Button) rootView.findViewById(R.id.graph_button);
        graphSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                GraphSelection dialog = new GraphSelection();
                dialog.show(fm, "Graph Type Selection");

            }
        });

        DatePicker dp = new DatePicker(myContext);

        TextView periodOfTime = (TextView) rootView.findViewById(R.id.periodOfTime);

        periodOfTime.setText(dp.getDayOfMonth() + "/" + (dp.getMonth()+1) + "/" + dp.getYear());

        // Inflate the layout for this fragment
        return rootView;
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            double[] test = new double[5];

            //declare size == amount of location
            open = new boolean[5];
            open[i-1] = false;

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Location " + i); // ชื่อ โลเคชัน

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, test, "a"));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    public void createDummyData(Bundle bundle) {

        int amountOfLocation = 3; // get size location มา
        int[] amountOfDevice = new int[amountOfLocation];

        for(int i = 0; i < amountOfLocation; i++){
            amountOfDevice[i] = 5; // get size ของ device ในแต่ละ location มา
        }

        String[] locationName = new String[amountOfLocation]; // get location name มา

        ArrayList<double[]> locationPower = new ArrayList<>();

        ArrayList<String[]> deviceName = new ArrayList<>();
        ArrayList<ArrayList<double[]>> devicePower = new ArrayList<>();
        ArrayList<String[]> deviceUsageTime = new ArrayList<>();


        for(int i = 0; i < amountOfLocation; i++){

            double[] locationPowerTemp = new double[amountOfDevice[i]];// get location power  มา
            String[] deviceNameTemp = new String[amountOfDevice[i]];// get device name in each location มา
            String[] deviceUsageTimeTemp = new String[amountOfDevice[i]];// get device time  มา

            devicePower.add(new ArrayList<double[]>());

            for(int j = 0; j < amountOfDevice[i]; j++) {

                int amountOfSeries = 0; // get device power size มา (จุดบน แกน X)

                double[] devicePowerTemp = new double[amountOfSeries];// get device power   มา

                devicePower.get(i).add(devicePowerTemp);
            }

            locationPower.add(locationPowerTemp);
            deviceName.add(deviceNameTemp);
            deviceUsageTime.add(deviceUsageTimeTemp);
        }


        for (int i = 1; i <= amountOfLocation; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(locationName[i-1]);

            dm.setPowerOfLocation(locationPower.get(i-1));

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j < amountOfDevice[i]; j++) {
                singleItem.add(new SingleItemModel(deviceName.get(i)[j], devicePower.get(i).get(j),  deviceUsageTime.get(i)[j]));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);
        }

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm, true);

        my_recycler_view.setAdapter(adapter);
    }

    public static void setDataPoint(){

        int count = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getValue(0) != -1d){
                count++;
            }
        }
        if(isBarGraph) {
            dataPoint = new DataPoint[count];
            dataPointName = new String[count];
            dataPointColor = new int[count];
            count = 0;

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getValue(0) != -1d) {
                    dataPoint[count] = new DataPoint(count, data.get(i).getSumValue());
                    if(data.get(i).getIsLocation())
                        dataPointName[count] =  data.get(i).getLocation();
                    else
                        dataPointName[count] =  data.get(i).getDevice();

                    dataPointColor[count] = Color.rgb(count*255/4, (int) Math.abs(data.get(count).getSumValue()*255/6), 100);

                    count++;
                }
            }
        }
    }

    public void setLineSeries(){

        lineSeries.clear();
        graph.removeAllSeries();

        int count = 0;

        for(int i = 0; i < data.size(); i++) {

            int size = data.get(i).getSize();

            if(data.get(i).getValue(0) != -1d) {
                DataPoint[] tempData = new DataPoint[size];
                for(int j = 0; j < size; j++) {
                    tempData[j] = new DataPoint(j, data.get(i).getValue(j));
                }
                lineSeries.add(new LineGraphSeries<>(tempData));

                graph.addSeries(lineSeries.get(count));
                if(data.get(i).getIsLocation()) {
                    lineSeries.get(count).setTitle(data.get(i).getLocation());
                }else{
                    lineSeries.get(count).setTitle(data.get(i).getDevice());
                }

                lineSeries.get(count).setColor(Color.rgb(count * 255 / 4, (int) Math.abs(data.get(count).getSumValue() * 255 / 6), 100));

                count++;
            }
        }

        dataPointName = new String[count];
        dataPointColor = new int[count];
        for(int i = 0; i < count; i++){
            dataPointName[i] = lineSeries.get(i).getTitle();
            dataPointColor[i] = lineSeries.get(i).getColor();
        }

    }

    public double getMaxLine(){

        double max = 0;

        for(int i = 0; i < data.size(); i++){
            for(int j = 0; j < data.get(i).getSize(); j++){
                if(data.get(i).getValue(j) > max){
                    max = data.get(i).getValue(j);
                }
            }
        }

        return max;
    }

    public double getMax(){

        double max = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getSumValue() > max){
                max = data.get(i).getSumValue();
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
