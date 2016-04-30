package fragments;

/**
 * Created by my131 on 28/4/2559.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import java.util.ArrayList;
import java.util.List;

import activity.Home;
import adapter.ApplianceAdapter;
import adapter.DividerItemDecoration;
import model.ApplianceModel;


public class DailyFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentActivity myContext;
    private List<ApplianceModel> applianceModelListeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ApplianceAdapter mAdapter;
    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.sort_by_spinner);

        ArrayAdapter<CharSequence> SpinnerAdapter = ArrayAdapter.createFromResource(myContext, R.array.sort_by_array, android.R.layout.simple_spinner_item);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(SpinnerAdapter);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_daily);
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
        spinner.setOnItemSelectedListener(this);

        return rootView;
    }
    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
    private void prepareApplianceData(int numSize) {
        int start;
        for(start = 0; start<numSize; start++){
            ApplianceModel movie = new ApplianceModel("Name: Air-Conditioner "+ start, "Electricity's usage: 200 Units", "03/02/2016");
            applianceModelListeList.add(movie);
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}