package fragments;

/**
 * Created by my131 on 28/4/2559.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senoir.newpmatry1.R;


public class MonthlyFragment extends Fragment{

    public MonthlyFragment() {
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
        //Log.d("MonthlyFragment", "Monthly was created again");
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }

}
