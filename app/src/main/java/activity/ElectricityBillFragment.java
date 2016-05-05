package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import org.w3c.dom.Text;

import adapter.ExpandableListAdapter;
import billcalculate.BillCalculate;
import model.ApplianceModel;
import model.ElectricityBillModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by my131 on 29/4/2559.
 */
public class ElectricityBillFragment extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    TextView totalCost;
    ArrayList<ElectricityBillModel> electricitybillList;
    HashMap<String, List<String>> listDataChild;
    private FragmentActivity myContext;
    float electricityType = 0.00f;
    private int onspinnerselected = 0;

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
        totalCost = (TextView) rootView.findViewById(R.id.total_cost);


        // get the listview

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);
        if(electricitybillList!=null){
            electricitybillList.clear();
        }
        // preparing list data


        Spinner spinner = (Spinner) rootView.findViewById(R.id.residential_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // TODO Auto-generated method stub
                String item = parent.getItemAtPosition(pos).toString();
                switch (item) {
                    case "Less than 150 Units/Month":
                        onspinnerselected = 0;
                        prepareListData();
                        listAdapter = new ExpandableListAdapter(myContext, listDataHeader, listDataChild);
                        listAdapter.notifyDataSetChanged();
                        expListView.setAdapter(listAdapter);

                        Toast.makeText(parent.getContext(), "Selected 0: " + item, Toast.LENGTH_LONG).show();
                        break;
                    case "More than 150 Units/Month":
                        onspinnerselected = 1;
                        prepareListData();
                        listAdapter = new ExpandableListAdapter(myContext, listDataHeader, listDataChild);
                        listAdapter.notifyDataSetChanged();
                        expListView.setAdapter(listAdapter);

                        Toast.makeText(parent.getContext(), "Selected 1: " + item, Toast.LENGTH_LONG).show();
                        break;
                    case "Time of Use Tariff(Voltage:12-24KV)":
                        onspinnerselected = 2;
                        prepareListData();
                        listAdapter = new ExpandableListAdapter(myContext, listDataHeader, listDataChild);
                        listAdapter.notifyDataSetChanged();
                        expListView.setAdapter(listAdapter);
                        Toast.makeText(parent.getContext(), "Selected : 2" + item, Toast.LENGTH_LONG).show();
                        break;
                    case "Time of Use Tariff(Voltage:less than 12KV)":
                        onspinnerselected = 3;
                        prepareListData();
                        listAdapter = new ExpandableListAdapter(myContext, listDataHeader, listDataChild);
                        listAdapter.notifyDataSetChanged();
                        expListView.setAdapter(listAdapter);
                        Toast.makeText(parent.getContext(), "Selected : 3" + item, Toast.LENGTH_LONG).show();
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
            });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(myContext, R.array.selected_type_calculate_electricitybill, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        prepareListData();
        listAdapter = new ExpandableListAdapter(myContext, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);



        return rootView;
    }
    private void prepareListData() {
        ArrayList<String> expandevices = null;

        electricitybillList = new ArrayList<ElectricityBillModel>();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        putdataToelectricityBill();

        double sumUnitOfLocation = 0d;
        double sumOnPeakUnit = 0d;
        double sumOffPeakUnit = 0d;

        for(int i =0; i<electricitybillList.size(); i++){
            if (onspinnerselected == 2||onspinnerselected == 3){
                sumOnPeakUnit += electricitybillList.get(i).getOnPeakLocation();
                sumOffPeakUnit += electricitybillList.get(i).getOffPeakLocation();
            } else {
                sumUnitOfLocation += electricitybillList.get(i).getUnitLocation();
            }
        }

        BillCalculate bill = new BillCalculate();

        DecimalFormat df = new DecimalFormat("#0.00");

        double costOfLocation = 0d;

        if(onspinnerselected == 0) {
            costOfLocation = bill.getBillOfType1_1(sumUnitOfLocation);
        } else if (onspinnerselected == 1){
            costOfLocation = bill.getBillOfType1_2(sumUnitOfLocation);
        } else if (onspinnerselected == 2) {
            costOfLocation = bill.getBillOfType1_3(sumOnPeakUnit, sumOffPeakUnit, 0); // แรงดันอันที่ 1
            sumUnitOfLocation = sumOffPeakUnit + sumOnPeakUnit;
        } else if (onspinnerselected == 3) {
            costOfLocation = bill.getBillOfType1_3(sumOnPeakUnit, sumOffPeakUnit, 1); // แรงดันอันที่ 2
            sumUnitOfLocation = sumOffPeakUnit + sumOnPeakUnit;
        }

        totalCost.setText("Total: "  + df.format(costOfLocation) + " Baht/   " + df.format(sumUnitOfLocation) +" Units");

        for(int start = 0; start < electricitybillList.size(); start++) {
            expandevices = new ArrayList<String>();
            double eachCostLocation = costOfLocation * (electricitybillList.get(start).getUnitLocation()/sumUnitOfLocation);

            if (onspinnerselected == 2||onspinnerselected == 3){
                double percentOfOffPeak =
                        electricitybillList.get(start).getOffPeakLocation()/electricitybillList.get(start).getUnitLocation();
                double percentOfOnPeak  =
                        electricitybillList.get(start).getOnPeakLocation()/electricitybillList.get(start).getUnitLocation();
                electricitybillList.get(start).setAllCost(eachCostLocation*percentOfOnPeak, eachCostLocation*percentOfOffPeak);
            } else {
                electricitybillList.get(start).setAllCost(eachCostLocation);
            }

            listDataHeader.add(electricitybillList.get(start).getLocationName() +":\n"+
                    df.format(electricitybillList.get(start).getCostLocation()) + "  Baht/ " +
                    df.format(electricitybillList.get(start).getUnitLocation()) + "  Units");

//            listDataHeader.add(electricitybillList.get(start).getLocationName() +":\n"+
//                    df.format(electricitybillList.get(start).getCostOnPeak()) + "  Baht/ " +
//                    df.format(electricitybillList.get(start).getOnPeakLocation()) + "  Units\n" +
//                    df.format(electricitybillList.get(start).getCostOffPeak()) + "  Baht/ " +
//                    df.format(electricitybillList.get(start).getOffPeakLocation()) + "  Units");

            for (int begin = 0; begin < electricitybillList.get(start).getDevicesName().size(); begin++) {

                expandevices.add(begin, electricitybillList.get(start).getDevicesNamestring(begin)+":\n"+
                        df.format(electricitybillList.get(start).getCostDevice(begin)) + "  Baht/ " +
                        df.format(electricitybillList.get(start).getUnitDevicesdou(begin)) + "  Units");
//                expandevices.add(begin, electricitybillList.get(start).getDevicesNamestring(begin)+":\n"+
//                        df.format(electricitybillList.get(start).getOnPeakDevice(begin)) + "  Baht/ " +
//                        df.format(electricitybillList.get(start).getOnPeakCostDevice(begin))+"  Units\n" +
//                        df.format(electricitybillList.get(start).getOffPeakDevice(begin)) + "  Baht/ " +
//                        df.format(electricitybillList.get(start).getOffPeakCostDevice(begin)) + "  Units");


            }
            listDataChild.put(listDataHeader.get(start), expandevices);
            //listAdapter.notifyDataSetChanged();
            //expandevices.clear();
            Log.d("Size", expandevices.size()+"");
        }


    }





    public void putdataToelectricityBill() {
        //electricityType == 1.31f || electricityType == 1.32f ||
        if (onspinnerselected == 2||onspinnerselected == 3){
            for (int start = 1; start <= 3; start++) {
                ArrayList<String> devicesname = new ArrayList<String>();
                ArrayList<Double> unitOnDevice = new ArrayList<Double>();
                ArrayList<Double> unitOffDevice = new ArrayList<Double>();

                for (int insides = 1; insides <= 5; insides++) {

                    devicesname.add("Devices: " + insides);
                    unitOnDevice.add(insides * 100d);
                    unitOffDevice.add(insides * 100d);
                }
                ElectricityBillModel electbill = new ElectricityBillModel("Location: " + start, devicesname, unitOnDevice, unitOffDevice);
                electricitybillList.add(electbill);
                //Log.d("TestLoop", electricitybillList.size()+"");
            }
        }else{
            for (int start = 1; start <= 3; start++) {
                ArrayList<String> devicesname = new ArrayList<String>();
                ArrayList<Double> unitDevice = new ArrayList<Double>();
                for (int insides = 1; insides <= 5; insides++) {

                    devicesname.add("Devices: " + insides);
                    unitDevice.add(insides * 100d);
                }
                ElectricityBillModel electbill = new ElectricityBillModel("Location: " + start, devicesname, unitDevice);
                electricitybillList.add(electbill);
                //Log.d("TestLoop", electricitybillList.size()+"");
            }
        }
    }



    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

//    public void onItemSelected(AdapterView<?> parent, View view,
//                               int pos, long id) {

//    }
//
//    public void onNothingSelected(AdapterView<?> parent) {
//        // Another interface callback
//    }



}
