package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.senoir.newpmatry1.R;
import adapter.ExpandableListAdapter;
import model.ElectricityBillModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by my131 on 29/4/2559.
 */
public class ElectricityBillFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    ArrayList<ElectricityBillModel> electricitybillList;
    HashMap<String, List<String>> listDataChild;
    private FragmentActivity myContext;
    public ElectricityBillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_electricitybill, container, false);


        // get the listview

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        // preparing list data

        prepareListData();

        listAdapter = new ExpandableListAdapter(myContext, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        return rootView;
    }
    private void prepareListData() {
        ArrayList<String> expandevices = null;

        electricitybillList = new ArrayList<ElectricityBillModel>();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        putdataToelectricityBill();

        for(int start = 0; start<electricitybillList.size(); start++) {
            expandevices = new ArrayList<String>();
            listDataHeader.add(electricitybillList.get(start).getLocationName() +":  "+ electricitybillList.get(start).getCostLocation() + "  Baht");
            for (int begin = 0; begin < electricitybillList.get(start).getDevicesName().size(); begin++) {
                expandevices.add(begin, electricitybillList.get(start).getDevicesNamestring(begin)+": "+electricitybillList.get(start).getCostDevicesint(begin)+"  Baht");

            }
            listDataChild.put(listDataHeader.get(start), expandevices);
            //expandevices.clear();
            Log.d("Size", expandevices.size()+"");
        }


    }
    public void putdataToelectricityBill() {

        for (int start = 0; start < 3; start++) {
            ArrayList<String> devicesname = new ArrayList<String>();
            ArrayList<Integer> costdevice = new ArrayList<Integer>();
            for (int insides = 0; insides < 3; insides++) {

                devicesname.add(insides, "Devices: " + insides);
                costdevice.add(insides, insides * 100);
            }
            ElectricityBillModel electbill = new ElectricityBillModel("Location: " + start, start * 200, devicesname, costdevice);
            electricitybillList.add(start, electbill);
            //Log.d("TestLoop", electricitybillList.size()+"");
        }
    }
    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
}
