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
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import org.w3c.dom.Text;

import adapter.ExpandableListAdapter;
import billcalculate.BillCalculate;
import model.ElectricityBillModel;

import java.text.DecimalFormat;
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
    TextView totalCost;
    ArrayList<ElectricityBillModel> electricitybillList;
    HashMap<String, List<String>> listDataChild;
    private FragmentActivity myContext;
    float electricityType = 1.31f;

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

        double sumUnitOfLocation = 0d;
        double sumOnPeakUnit = 0d;
        double sumOffPeakUnit = 0d;

        for(int i =0; i<electricitybillList.size(); i++){
            if (electricityType == 1.31f || electricityType == 1.32f){
                sumOnPeakUnit += electricitybillList.get(i).getUnitLocation();
                sumOffPeakUnit += electricitybillList.get(i).getUnitLocation();
            } else {
                sumUnitOfLocation += electricitybillList.get(i).getUnitLocation();
            }
        }

        BillCalculate bill = new BillCalculate();

        DecimalFormat df = new DecimalFormat("#0.00");

        double costOfLocation = 0d;

        if(electricityType == 1.1f) {
            costOfLocation = bill.getBillOfType1_1(sumUnitOfLocation);
        } else if (electricityType == 1.2f){
            costOfLocation = bill.getBillOfType1_2(sumUnitOfLocation);
        } else if (electricityType == 1.31f) {
            costOfLocation = bill.getBillOfType1_3(sumOnPeakUnit, sumOffPeakUnit, 0); // แรงดันอันที่ 1
            sumUnitOfLocation = sumOffPeakUnit + sumOnPeakUnit;
        } else if (electricityType == 1.32f) {
            costOfLocation = bill.getBillOfType1_3(sumOnPeakUnit, sumOffPeakUnit, 1); // แรงดันอันที่ 2
            sumUnitOfLocation = sumOffPeakUnit + sumOnPeakUnit;
        }

        totalCost.setText("Total: "  + df.format(costOfLocation) + " Baht/   " + df.format(sumUnitOfLocation) +" Units");

        for(int start = 0; start < electricitybillList.size(); start++) {
            expandevices = new ArrayList<String>();
            double eachCostLocation = costOfLocation * (electricitybillList.get(start).getUnitLocation()/sumUnitOfLocation);

            if (electricityType == 1.31f || electricityType == 1.32f){
                double percentOfTypeWatt = sumOffPeakUnit/sumOnPeakUnit;
                electricitybillList.get(start).setAllCost(eachCostLocation, eachCostLocation*percentOfTypeWatt);
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
            //expandevices.clear();
            Log.d("Size", expandevices.size()+"");
        }


    }
    public void putdataToelectricityBill() {

        if (electricityType == 1.31f || electricityType == 1.32f){
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
}
