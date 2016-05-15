package History_OnOff.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import History_OnOff.adapter.RecyclerViewDataAdapter;
import History_OnOff.fragments.LocationFragment;
import adapter.DividerItemDecoration;

/**
 * Created by Toshiba on 4/29/2016.
 */
public class TimeSelectionDialog extends DialogFragment {

    private TextView startTv;

    private TextView endTv;

    private DatePicker datePicker;

    private boolean startIsSet = false;

    private Context myContext;
    private View view;
    private RecyclerViewDataAdapter adapter;

    private int[] date = new int[6];

    private String currentDate;
    private int currentDay;
    private int currentMonth;
    private int currentYear;

    private String startDate;
    private String endDate;
    private boolean endDateIsSet;

    public TimeSelectionDialog(Context myContext,View view, RecyclerViewDataAdapter adapter){
        this.myContext = myContext;
        this.view = view;
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_time, container, false);
        getDialog().setTitle("Time Selection");

        startTv = (TextView) rootView.findViewById(R.id.startTv);
        endTv = (TextView) rootView.findViewById(R.id.endTv);

        datePicker = (DatePicker) rootView.findViewById(R.id.calendar);

        currentDate = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getYear();

        currentDay = datePicker.getDayOfMonth();
        currentMonth = (datePicker.getMonth()+1);
        currentYear =  datePicker.getYear();

        Button startBt = (Button) rootView.findViewById(R.id.startBt);
        startBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date[0] = datePicker.getYear();
                date[1] = (datePicker.getMonth()+1);
                date[2] = datePicker.getDayOfMonth();
                if (isBeforeToday(date[2], date[1], date[0])){
                startTv.setText(date[2] + "/" + date[1] + "/" + date[0]);

                startDate = date[2] + "/" + date[1] + "/" + date[0];
                startIsSet = true;
                } else {
                    Toast.makeText(getContext(), "Please! set a date before today" , Toast.LENGTH_LONG).show();
                }
            }
        });

        Button endBt = (Button) rootView.findViewById(R.id.endBt);
        endBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(startIsSet){
                    date[3] = datePicker.getYear();
                    date[4] = (datePicker.getMonth()+1);
                    date[5] = datePicker.getDayOfMonth();


                    if(compareDate() && isBeforeToday(date[5], date[4], date[3])) {

                        endTv.setText(date[5] + "/" + date[4] + "/" + date[3]);
                        endDate = date[5] + "/" + date[4] + "/" + date[3];
                        endDateIsSet = true;

                    } else if (!compareDate() && isBeforeToday(date[5], date[4], date[3])){
                        Toast.makeText(getContext(), "Please! set a date after or same a first date", Toast.LENGTH_LONG).show();
                    } else if (compareDate() && !isBeforeToday(date[5], date[4], date[3])){
                        Toast.makeText(getContext(), "Please! set a date before today", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Please! set a date before today and after or as started day", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please set a first date", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button ok = (Button) rootView.findViewById(R.id.okBt);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView tv = (TextView) view.findViewById(R.id.periodOfTime);
                if(startIsSet && endDateIsSet) {
                    if (!startDate.equals(endDate)) {
                        tv.setText(startDate + "\nto " + endDate);
                    }
                    else {
                        tv.setText(startDate);
                    }

                    LocationFragment.canSelectData = true;

                    RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);

                    my_recycler_view.setHasFixedSize(true);

                    my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

                    my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));

                    my_recycler_view.setAdapter(adapter);

                    LocationFragment.data.clear();

                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Please set your date completely", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button cancel = (Button) rootView.findViewById(R.id.cancelBt);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button today = (Button) rootView.findViewById(R.id.currentDay);
        today.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView tv = (TextView) view.findViewById(R.id.periodOfTime);

                tv.setText(currentDate);
                dismiss();
            }
        });

        Button selectedDay = (Button) rootView.findViewById(R.id.selectedDay);
        selectedDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView tv = (TextView) view.findViewById(R.id.periodOfTime);

                tv.setText(datePicker.getDayOfMonth() +"/"+ (datePicker.getMonth()+1) +"/"+ datePicker.getYear());
                dismiss();
            }
        });

        return rootView;
    }

    public boolean compareDate(){
        if(date[0] < date[3]){
            return true;
        } else if(date[0] > date[3]) {
            return false;
        } else {
            if(date[1] < date[4]){
                return true;
            } else if(date[1] > date[4]){
                return false;
            } else {
                if(date[2] > date[5]){
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public boolean isBeforeToday(int day, int month, int year){
        if(year > currentYear){
            return false;
        } else {
            if(month > currentMonth){
                return false;
            } else {
                if(day >= currentDay){
                    return false;
                }
            }
        }

        return true;
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
