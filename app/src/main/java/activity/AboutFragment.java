package activity;

/**
 * Created by my131 on 6/5/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;


public class AboutFragment extends Fragment {

    private int show;

    private String clientHandle = null;
    private Connection connection = null;

    public AboutFragment() {
        // Required empty public constructor
    }




    public AboutFragment(String clientHandle)
    {

        this.clientHandle = clientHandle;
        connection = Connections.getInstance(getActivity()).getConnection(clientHandle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        if(show == 1) show();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void show()
    {
        Toast.makeText(getActivity(),"show",Toast.LENGTH_SHORT).show();

    }

    public void setShow(int sh)
    {
        show = sh;
    }
}
