package activity;

import android.app.Activity;
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

import com.jjoe64.graphview.GraphView;

import com.example.senoir.newpmatry1.R;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

import adapter.RecyclerViewDataAdapter;
import model.SectionDataModel;
import model.SingleItemModel;

/**
 * Created by my131 on 26/4/2559.
 */
public class LocationFragment extends Fragment{

    private FragmentActivity myContext;
    ArrayList<SectionDataModel> allSampleData;
    FragmentManager fm;

    public static DataPoint[] dataPoint;
    public static ArrayList<Double> data = new ArrayList<>();
    public static ArrayList<Double> data2 = new ArrayList<>();

    BarGraphSeries<DataPoint> series;

    GraphView graph;

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
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        allSampleData = new ArrayList<>();
        createDummyData();

        fm = getFragmentManager();

        RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

        //graph view section
        graph = (GraphView) rootView.findViewById(R.id.graph);

        series = new BarGraphSeries<>();

        graph.addSeries(series);

        series.setSpacing(20);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
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

                setDataPoint();
                if(dataPoint != null) {
                    series.resetData(dataPoint);
                }
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(getMax() + 5);

                for(int i = 0; i < data.size(); i++){
                    if(data.get(i) > data2.get(i)) {
                        data2.set(i, data2.get(i) + (data.get(i)/100)*3);
                    }
                }

                mHandler.postDelayed(this, 1);
            }
        };
        mHandler.postDelayed(mTimer1, 300);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Location " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "a"+j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    public static void setDataPoint(){

        int count = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i) != -1d){
                count++;
            }
        }
        dataPoint = new DataPoint[count];

        count = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i) != -1d){
                dataPoint[count] = new DataPoint(count,data2.get(i));
                count++;
            }
        }
    }

    public double getMax(){

        double max = 0;

        for(int i = 0; i < data.size(); i++){
            if(data.get(i) > max){
                max = data.get(i);
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
