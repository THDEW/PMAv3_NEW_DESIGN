package ElectricityCost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;
import org.eclipse.paho.android.service.sample.MqttCallbackHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import billcalculate.BillCalculate;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    Bundle bundle = null;

    ArrayList<ElectricityBillModel> electricitybillList;
    HashMap<String, List<String>> listDataChild;
    private FragmentActivity myContext;
    float electricityType = 0.00f;
    private int onspinnerselected = 0;

    private String clientHandle;
    private Connection connection;
    //private ChangeListener changeListener = new ChangeListener();

    private ElectricityBillFragment electricityBillFragment = this;

    public ElectricityBillFragment() {
        // Required empty public constructor
    }

    public ElectricityBillFragment(String clientHandle) {

        this.clientHandle = clientHandle;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        connection = Connections.getInstance(getActivity()).getConnection(clientHandle);
        //connection.registerChangeListener(changeListener);
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
                        break;
                    case "More than 150 Units/Month":
                        onspinnerselected = 1;
                        break;
//
                }

                if(bundle != null){
                    prepareListData(bundle);
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

        DecimalFormat df = new DecimalFormat("#0.000000");

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

        totalCost.setText("Total: " + df.format(costOfLocation) + " Baht/   " + df.format(sumUnitOfLocation) + " Units");

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

            for (int begin = 0; begin < electricitybillList.get(start).getDevicesName().size(); begin++) {

                expandevices.add(begin, electricitybillList.get(start).getDevicesNamestring(begin)+":\n"+
                        df.format(electricitybillList.get(start).getCostDevice(begin)) + "  Baht/ " +
                        df.format(electricitybillList.get(start).getUnitDevicesdou(begin)) + "  Units");
            }
            listDataChild.put(listDataHeader.get(start), expandevices);
            Log.d("Size", expandevices.size()+"");
        }


    }




    //ข้อมูลที่ใช้มี ชื่อดีไว้ ชื่อโลเคชั่น แล้วก็พลังงานที่ใช้แแต่ที่ใช้สูตรคิดมันเปนunitต้องแปลงก่อนเพราะเมิงดึงมาเปนwatt
    public void putdataToelectricityBill() {

        if (onspinnerselected == 2||onspinnerselected == 3){
            for (int start = 1; start <= 3; start++) {
                ArrayList<String> devicesname = new ArrayList<String>();
                ArrayList<Double> unitOnDevice = new ArrayList<Double>();
                ArrayList<Double> unitOffDevice = new ArrayList<Double>();

                //ลูปนี้ใส่ชื่อดีไว้
                for (int insides = 1; insides <= 5; insides++) {

                    devicesname.add("Devices: " + insides);
                    unitOnDevice.add(insides * 100d);//เปนunitทีต้องดึงตามเวลาonpeak
                    unitOffDevice.add(insides * 100d);//เปนunitทีต้องดึงตามเวลาoffpeak ลองดุในเวปไฟฟ้าที่ส่งให้นะแบบที่สาม
                }
                // ตรงนี้ก็ดูบรรทัดล่างนะมันมีชื่อโลเคชี่นที่ต้องดึงมา start คือกุรันลุปให้มันเปนเลขเฉยๆ
                ElectricityBillModel electbill = new ElectricityBillModel("Location: " + start, devicesname, unitOnDevice, unitOffDevice);
                electricitybillList.add(electbill);

            }
        }else{
            for (int start = 1; start <= 3; start++) {
                ArrayList<String> devicesname = new ArrayList<String>();
                ArrayList<Double> unitDevice = new ArrayList<Double>();
                for (int insides = 1; insides <= 5; insides++) {

                    devicesname.add("Devices: " + insides);
                    unitDevice.add(insides * 100d);//unitของแต่ละดีไว้
                }
                // ตรงนี้ก็ดูบรรทัดล่างนะมันมีชื่อโลเคชี่นที่ต้องดึงมา start คือกุรันลุปให้มันเปนเลขเฉยๆ
                ElectricityBillModel electbill = new ElectricityBillModel("Location: " + start, devicesname, unitDevice);
                electricitybillList.add(electbill);

            }
        }
    }


    public void prepareListData(Bundle bundle) {

        this.bundle = bundle;

        ArrayList<String> expandevices = null;

        electricitybillList = new ArrayList<ElectricityBillModel>();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        putdataToelectricityBill(bundle);

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

        DecimalFormat df = new DecimalFormat("#0.000000");

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

            for (int begin = 0; begin < electricitybillList.get(start).getDevicesName().size(); begin++) {

                expandevices.add(begin, electricitybillList.get(start).getDevicesNamestring(begin)+":\n"+
                        df.format(electricitybillList.get(start).getCostDevice(begin)) + "  Baht/ " +
                        df.format(electricitybillList.get(start).getUnitDevicesdou(begin)) + "  Units");
            }
            listDataChild.put(listDataHeader.get(start), expandevices);
            Log.d("Size", expandevices.size()+"");
        }

        listAdapter = new ExpandableListAdapter(myContext, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }




    //ข้อมูลที่ใช้มี ชื่อดีไว้ ชื่อโลเคชั่น แล้วก็พลังงานที่ใช้แแต่ที่ใช้สูตรคิดมันเปนunitต้องแปลงก่อนเพราะเมิงดึงมาเปนwatt
    public void putdataToelectricityBill(Bundle bundle) {
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String jall = bundle.getString("electricityBill");

        try {
            jsonArray = new JSONArray(jall);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray1 = null;

        int amountOfLocation = jsonArray.length(); // get size location มา
        int[] amountOfDevice = new int[amountOfLocation];

        String[] locationName = new String[amountOfLocation]; // get location name มา

        for(int i = 0; i < amountOfLocation; i++){

            try {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                jsonArray1 = jsonObject1.getJSONArray("value");

                amountOfDevice[i] = jsonArray1.length(); // get size ของ device ในแต่ละ location มา
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        ArrayList<String[]> deviceName = new ArrayList<>();
        ArrayList<double[]> deviceUnits = new ArrayList<>();


        for(int i = 0; i < amountOfLocation; i++){
            JSONObject jsonObject1 = null;
            jsonArray1 = null;
            String[] deviceNameTemp = new String[amountOfDevice[i]];// get device name in each location มา
            double[] deviceUnitsTemp = new double[amountOfDevice[i]];// get device unit  มา


            try {
                jsonObject1 = (JSONObject) jsonArray.get(i);
                jsonArray1 = jsonObject1.getJSONArray("value");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                locationName[i] = jsonObject1.getString("location_name");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int j = 0; j < amountOfDevice[i]; j++)
            {

                JSONObject jsonObject2 = null;
                try {
                    jsonObject2 = (JSONObject) jsonArray1.get(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    deviceNameTemp[j] = jsonObject2.getString("oltp_ip_address")+" : "+jsonObject2.getString("oltp_pin");
                    deviceUnitsTemp[j] = (Double.parseDouble(jsonObject2.getString("sum_energy"))/1000)/3600;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            deviceName.add(deviceNameTemp);
            deviceUnits.add(deviceUnitsTemp);

        }


        if (onspinnerselected == 2||onspinnerselected == 3){
            for (int start = 1; start <= amountOfLocation; start++) {
                ArrayList<String> devicesname = new ArrayList<String>();
                ArrayList<Double> unitOnDevice = new ArrayList<Double>();
                ArrayList<Double> unitOffDevice = new ArrayList<Double>();
                //ลูปนี้ใส่ชื่อดีไว้
                for (int insides = 1; insides <= amountOfDevice[start-1]; insides++) {

                    devicesname.add(deviceName.get(start-1)[insides-1]);

                }
                // ตรงนี้ก็ดูบรรทัดล่างนะมันมีชื่อโลเคชี่นที่ต้องดึงมา start คือกุรันลุปให้มันเปนเลขเฉยๆ
                ElectricityBillModel electbill = new ElectricityBillModel(locationName[start-1], devicesname, unitOnDevice, unitOffDevice);
                electricitybillList.add(electbill);
            }
        }else{
            for (int start = 1; start <= amountOfLocation; start++) {
                ArrayList<String> devicesname = new ArrayList<String>();
                ArrayList<Double> unitDevice = new ArrayList<Double>();
                for (int insides = 1; insides <= amountOfDevice[start-1]; insides++) {

                    devicesname.add(deviceName.get(start-1)[insides-1]);
                    unitDevice.add(deviceUnits.get(start-1)[insides-1]);//unitของแต่ละดีไว้
                }
                // ตรงนี้ก็ดูบรรทัดล่างนะมันมีชื่อโลเคชี่นที่ต้องดึงมา start คือกุรันลุปให้มันเปนเลขเฉยๆ
                ElectricityBillModel electbill = new ElectricityBillModel(locationName[start-1], devicesname, unitDevice);
                electricitybillList.add(electbill);

            }
        }
        /*
        String topic = "android/electricityBill";
        String message = "getElectricityCost";
        int qos = 0;
        boolean retained = false;

        String[] args = new String[2];
        args[0] = message;
        args[1] = topic+";qos:"+qos+";retained:"+retained;

        try {
            connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
        } catch (MqttException e) {
            e.printStackTrace();
        }
        */


    }


    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    /*
    private class ChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {

            FragmentManager fragmentManager = electricityBillFragment.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if(event.getPropertyName().equals("electricityBill"))
            {
                final Bundle bundle;
                bundle = connection.getBundle();
                Log.v("electricfrag", "efrag");
                electricityBillFragment.prepareListData(bundle);


            }
        }
    }
    */

}
