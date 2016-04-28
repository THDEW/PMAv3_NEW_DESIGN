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


public class YearlyFragment extends Fragment{

    public YearlyFragment() {
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
       // Log.d("YearlyFragment", "Yearly was created again");
        return inflater.inflate(R.layout.fragment_yearly, container, false);
    }

}
