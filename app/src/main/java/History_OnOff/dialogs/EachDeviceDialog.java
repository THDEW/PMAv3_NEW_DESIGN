package History_OnOff.dialogs;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import ElectricityCost.ElectricityBillFragment;
import billcalculate.BillCalculate;

/**
 * Created by Toshiba on 4/29/2016.
 */
public class EachDeviceDialog extends DialogFragment {

    private TextView whereTv;

    private TextView energyTv;

    private TextView timeTv;

    private TextView billTv;

    private TextView statusTv;

    private GraphView graph;

    private LineGraphSeries<DataPoint> series;

    private DataPoint[] dataPoint;

    private double realValue;
    private boolean increase;

    private boolean deviceSelected;

    private double[] energyConsumption;

    private String name, location, energy, bill, time;

    private boolean status;

    public EachDeviceDialog(){}

    public EachDeviceDialog(String name, String location,String energy/*,String bill*/,String time,boolean status){
        this.name = name;
        this.location = location;
        this.energy = energy;
//        this.bill = bill;
        this.time = time;
        this.status = status;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_device, container, false);
        getDialog().setTitle(name);

        whereTv = (TextView) rootView.findViewById(R.id.whereTv);
        energyTv = (TextView) rootView.findViewById(R.id.energyTv);
        timeTv = (TextView) rootView.findViewById(R.id.time_button);
//        billTv = (TextView) rootView.findViewById(R.id.billTv);
        statusTv = (TextView) rootView.findViewById(R.id.statusTv);


        whereTv.setText(location);
        energyTv.setText(energy);
        timeTv.setText(time);
//        billTv.setText(bill);
        if(status){
            statusTv.setText("ON");
            statusTv.setTextColor(Color.GREEN);
        }
        else {
            statusTv.setText("OFF");
            statusTv.setTextColor(Color.RED);
        }

        graph = (GraphView) rootView.findViewById(R.id.graph);

        energyConsumption = new double[10];

        for(int i = 0; i < 10; i++){
            energyConsumption[i] = 2d;
        }

        setDataPoint(energyConsumption);

        series = new LineGraphSeries<>(dataPoint);

        graph.addSeries(series);

        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        graph.getViewport().setYAxisBoundsManual(true);

        realValue = 4d;
        increase = true;

        deviceSelected = true;
        if(status) {
            final Handler mHandler = new Handler();

            Runnable mTimer1 = new Runnable() {
                @Override
                public void run() {
                    if (deviceSelected) {
                        if (increase) {
                            realValue += 2d;
                            if (realValue > 20d) {
                                increase = false;
                            }
                        } else {
                            realValue -= 2d;
                            if (realValue < 2d) {
                                increase = true;
                            }
                        }

                        appendDataPoint(realValue);

                        setDataPoint(energyConsumption);

                        series.resetData(dataPoint);

                        graph.getViewport().setMinY(0);
                        graph.getViewport().setMaxY(getMax() + 5);

                        mHandler.postDelayed(this, 100);
                    }
                }
            };

            mHandler.postDelayed(mTimer1, 200);
        }
        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

    }

    public void updateData(Bundle bundle){

        double newValue = 5d; // current energy
        double energy = Double.parseDouble(this.energy) + newValue;
        this.energy = energy+"";

//        BillCalculate billCal = new BillCalculate();

//        this.bill = billCal.getBillOfType1_1(energy) + "";
        this.time = " "; // current time usage
        this.status = false; // get current status

        energyTv.setText(this.energy);
        timeTv.setText(this.time);
//        billTv.setText(this.bill);
        if(this.status){
            statusTv.setText("ON");
            statusTv.setTextColor(Color.GREEN);
        }
        else {
            statusTv.setText("OFF");
            statusTv.setTextColor(Color.RED);
        }

        appendDataPoint(newValue);

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
