package fragments;

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
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import java.util.ArrayList;
import java.util.Collections;

import activity.Home;
import adapter.ApplianceAdapter;
import adapter.DividerItemDecoration;
import model.ApplianceModel;


public class MonthlyFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentActivity myContext;
    private ArrayList<ApplianceModel> applianceModelListeList = new ArrayList<ApplianceModel>();
    private RecyclerView recyclerView;
    private ApplianceAdapter mAdapter;
    private int onspinnerselected;
    TextView buttonToChange;
    public MonthlyFragment() {
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
        prepareApplianceData(20);
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
    private void prepareApplianceData(int numSize) {
        int start, start0;
        long elecusages = 0;
        long elecusageLocation = 0;
        if (Home.menu_statistic==0) {
            for (start = 0; start < numSize; start++) {
                ApplianceModel appliances = new ApplianceModel("Name: Air-Conditioner " + start, elecusages++, "03/02/2016", "Electricity's Usage: ");
                applianceModelListeList.add(appliances);
            }
        } else if (Home.menu_statistic==1) {
            for (start0 = 0; start0 < numSize - 15; start0++) {
                ApplianceModel locationapp = new ApplianceModel("Name: Location " + start0, elecusageLocation++, "05/12/2016", "Electricity's Usage: ");
                applianceModelListeList.add(locationapp);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    public void changeMonthly(int numSize){

        if(recyclerView!=null) {
            applianceModelListeList.clear();
            int start, start0;
            long elecusages = 0;
            long elecusageLocation = 0;
            if (Home.menu_statistic == 0) {
                for (start = 0; start < numSize; start++) {
                    ApplianceModel appliances = new ApplianceModel("Name: Air-Conditioner " + start, elecusages++, "03/02/2016", "Electricity's Usage: ");
                    applianceModelListeList.add(appliances);
                }
                buttonToChange.setText("Devices");
            } else if (Home.menu_statistic == 1) {
                for (start0 = 0; start0 < numSize - 15; start0++) {
                    ApplianceModel locationapp = new ApplianceModel("Name: Location " + start0, elecusageLocation++, "05/12/2016", "Electricity's Usage: ");
                    applianceModelListeList.add(locationapp);
                }
                buttonToChange.setText("Locations");
            }
            if (onspinnerselected == 0) {
                Collections.sort(applianceModelListeList, ApplianceModel.electusageMaxComparator);
            } else {
                Collections.sort(applianceModelListeList, ApplianceModel.electusageMinComparator);
            }
            //mAdapter = new ApplianceAdapter(applianceModelListeList);

            recyclerView.setAdapter(mAdapter);
        }

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