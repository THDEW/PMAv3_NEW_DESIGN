package activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senoir.newpmatry1.R;

/**
 * Created by my131 on 29/4/2559.
 */
public class ElectricityBillFragment extends Fragment {

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
        //Log.d("DailyFragment", "Daily was created again");
        return inflater.inflate(R.layout.fragment_electricitybill, container, false);
    }

}
