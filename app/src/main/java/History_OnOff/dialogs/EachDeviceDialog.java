package History_OnOff.dialogs;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.eclipse.paho.android.service.sample.ActionListener;
import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

import ElectricityCost.ElectricityBillFragment;
import History_OnOff.model.SingleItemModel;
import billcalculate.BillCalculate;

/**
 * Created by Toshiba on 4/29/2016.
 */
public class EachDeviceDialog extends DialogFragment {

    private TextView whereTv;

    private TextView energyTv;

    private TextView timeTv;

    private TextView recordTv;

    private TextView billTv;

    private TextView statusTv;

    private GraphView graph;

    private LineGraphSeries<DataPoint> series;

    private DataPoint[] dataPoint;

    private double realValue;
    private boolean increase;

    private boolean deviceSelected;

    private double[] energyConsumption;

    private String name, location, energy, bill, lastTime, lastRecord;

    private boolean status;

    private int id;

    private String clientHandle;
    private Connection connection;
    private ChangeListener changeListener = new ChangeListener();

    private EachDeviceDialog eachDeviceDialog;

    private boolean onUpdate = false;

    final Handler mHandler = new Handler();

    private Runnable r;


    public EachDeviceDialog(){}

    public EachDeviceDialog(int id,String name, /*String location,*/String energy/*,String bill*/,String lastTime, String lastRecord/*,boolean status*/, final Connection connection){
        //        this.bill = bill;
        this.id = id;
        this.name = name;
        this.energy = energy;
        this.lastTime = lastTime;
        this.lastRecord = lastRecord;
        this.connection = connection;
        connection.registerChangeListener(changeListener);
        eachDeviceDialog = this;
        r = new Runnable() {
            private volatile boolean shutdown = false;
            @Override
            public void run() {


                        String topic = "android/currentStatus/group_of_device";
                        String message = ""+eachDeviceDialog.id;
                        int qos = 0;
                        boolean retained = false;

                        String[] args = new String[2];
                        args[0] = message;
                        args[1] = topic+";qos:"+qos+";retained:"+retained;

                        if(eachDeviceDialog.connection != null)
                        {
                            try {
                                eachDeviceDialog.connection.getClient().publish(topic, message.getBytes(), qos, retained, null, new ActionListener(getActivity(), ActionListener.Action.PUBLISH, clientHandle, args));
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {

                        }
                    }



        };

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_device, container, false);
        getDialog().setTitle(name);

        energyTv = (TextView) rootView.findViewById(R.id.energyTv);
        timeTv = (TextView) rootView.findViewById(R.id.lastReceiveTv);
        recordTv = (TextView) rootView.findViewById(R.id.lastRecordTv);

//        billTv = (TextView) rootView.findViewById(R.id.billTv);
//        statusTv = (TextView) rootView.findViewById(R.id.statusTv);


//        whereTv.setText(location);
        energyTv.setText(energy);
        timeTv.setText(lastTime);
        recordTv.setText(lastRecord);

//        billTv.setText(bill);
//        if(status){
//            statusTv.setText("ON");
//            statusTv.setTextColor(Color.GREEN);
//        }
//        else {
//            statusTv.setText("OFF");
//            statusTv.setTextColor(Color.RED);
//        }

        graph = (GraphView) rootView.findViewById(R.id.graph);

        energyConsumption = new double[10];

        for(int i = 0; i < 10; i++){
            energyConsumption[i] = 0d;
        }

        setDataPoint(energyConsumption);

        series = new LineGraphSeries<>(dataPoint);

        graph.addSeries(series);

        final DecimalFormat d = new DecimalFormat("0.00");

        graph.getSecondScale().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(Double.parseDouble(d.format(value)), isValueX) + " Ws        |";
                }
            }
        });

        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Current time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Energy Consumption (Ws)");
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getSecondScale().setMinY(0.1);
        graph.getSecondScale().setMaxY(getMax() + 2);

        graph.getViewport().setYAxisBoundsManual(true);

        deviceSelected = true;
//        final Handler mHandler = new Handler();
//        Runnable mTimer1 = new Runnable() {
//            @Override
//            public void run() {
//
//                    series.resetData(dataPoint);
//                    graph.getSecondScale().setMinY(0);
//                    graph.getSecondScale().setMaxY(getMax() + 5);
//                    mHandler.postDelayed(this, 2000);
//
//            }
//        };
//        mHandler.postDelayed(mTimer1, 100);

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                eachDeviceDialog.connection = null;
                dismiss();

                deviceSelected = false;
            }
        });
        return rootView;
    }

    public double getMax(){

        double max = 0;

        for(int i = 0; i < energyConsumption.length; i++){
            if(energyConsumption[i] > max){
                max = energyConsumption[i];
            }
        }

        return max;
    }

    public void setDataPoint(double[] ini){

            dataPoint = new DataPoint[ini.length];

            for(int i = 0; i < ini.length; i++){
                dataPoint[i] = new DataPoint(i, ini[i]);
            }
    }

    public void appendDataPoint(double newValue){

        for(int i = 0; i < 9; i++){
            energyConsumption[i] = energyConsumption[i+1];
        }

        energyConsumption[9] = newValue;

        setDataPoint(energyConsumption);

        Log.d("aaabbb", newValue+"");
    }

    public void updateData(Bundle bundle){

        String jall = bundle.getString("currentStatus/group_of_device");
        Log.v("newval", "1");
        JSONArray group_of_device_data = null;
        JSONObject group_of_device_data_row = null;
        double newValue = 5d; // current energy
        DecimalFormat df = new DecimalFormat("0.0000");

        try {
            group_of_device_data = new JSONArray(jall);
            group_of_device_data_row = (JSONObject) group_of_device_data.get(0);
            Log.v("newval", "2");



            this.energy = group_of_device_data_row.getString("sum_energy_god");
            Log.v("newval", "3");

            Double energydub = Double.parseDouble(this.energy);
            energydub = (energydub/1000)/3600;


            this.energy = df.format(energydub);

            this.lastTime = group_of_device_data_row.getString("year")+"/"
                    +group_of_device_data_row.getString("month")+"/"
                    +group_of_device_data_row.getString("date")+" "
                    +group_of_device_data_row.getString("hour")+":"
                    +group_of_device_data_row.getString("minute")+":"
                    +group_of_device_data_row.getString("second"); // current time usage
            Log.v("newval", "4");
            this.lastRecord = group_of_device_data_row.getString("energy"); // get current status
            Log.v("newval", "5");

            newValue = Double.parseDouble(this.lastRecord);
            Log.v("newval", "6");

        } catch (JSONException e) {
            e.printStackTrace();
        }

            Log.v("newval", newValue+"");



        energyTv.setText(this.energy + " kW/hr");
        timeTv.setText(this.lastTime);
        recordTv.setText(this.lastRecord + " Ws");
//        billTv.setText(this.bill);
//        if(this.status){
//            statusTv.setText("ON");
//            statusTv.setTextColor(Color.GREEN);
//        }
//        else {
//            statusTv.setText("OFF");
//            statusTv.setTextColor(Color.RED);
//        }
        appendDataPoint(newValue);
        //appendDataPoint(Double.parseDouble(this.energy));

        series.resetData(dataPoint);

        graph.getSecondScale().setMinY(0);
        graph.getSecondScale().setMaxY(getMax() + getMax() * 0.5);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(getMax() + getMax() * 0.5);

        //onUpdate = true;

        mHandler.postDelayed(r,3000);
        //onUpdate = false;



    }

    private class ChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent event) {


            if(event.getPropertyName().equals("currentStatus/group_of_device/"+eachDeviceDialog.id))
            {
                Bundle bundle;
                bundle = connection.getBundle();
                Log.v("eachdev", bundle.toString());
                eachDeviceDialog.updateData(bundle);







            }


        }
    }

    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Simple Dialog");
        builder.setMessage("Some message here");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
    */
}
