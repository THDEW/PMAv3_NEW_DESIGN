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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;

import java.util.ArrayList;

import History_OnOff.adapter.RecyclerViewDataAdapter;
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
        allSampleData = new ArrayList<>();

        createDummyData();

        fm = getFragmentManager();

        my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm, false);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));

        my_recycler_view.setAdapter(adapter);

        return rootView;
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            double[] test = new double[5];

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Location " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, test));
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

        ArrayList<String[]> deviceName = new ArrayList<>();
        ArrayList<double[]> devicePower = new ArrayList<>();


        for(int i = 0; i < amountOfLocation; i++){

            String[] deviceNameTemp = new String[amountOfDevice[i]];// get device name in each location มา
            double[] devicePowerTemp = new double[amountOfDevice[i]];// get device power   มา

            deviceName.add(deviceNameTemp);
            devicePower.add(devicePowerTemp);
        }


        for (int i = 1; i <= amountOfLocation; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(locationName[i - 1]);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j < amountOfDevice[i]; j++) {
                singleItem.add(new SingleItemModel(deviceName.get(i)[j], new double[]{devicePower.get(i)[j]}));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);
        }

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm, false);

        my_recycler_view.setAdapter(adapter);
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
