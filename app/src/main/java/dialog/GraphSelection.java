package dialog;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;
import com.jjoe64.graphview.LegendRenderer;

import activity.LocationFragment;

/**
 * Created by Toshiba on 5/3/2016.
 */
public class GraphSelection extends DialogFragment{
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_graph, container, false);
        getDialog().setTitle("Graph type");

        Button bt1 = (Button) rootView.findViewById(R.id.bar_graph_bt);
        Button bt2 = (Button) rootView.findViewById(R.id.line_graph_bt);

        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LocationFragment.isBarGraph = true;
                dismiss();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LocationFragment.isBarGraph = false;
                LocationFragment.isBarGraphSet = false;
                LocationFragment.addNew = true;
                dismiss();
            }
        });

        return rootView;
    }
}
