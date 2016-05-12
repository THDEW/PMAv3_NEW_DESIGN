package About;

/**
 * Created by my131 on 6/5/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private FragmentActivity myContext;
    private HowtoModel howto;

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
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rvAbout);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(myContext);
        rv.setLayoutManager(llm);
        howto = new HowtoModel();
        howto.initializeData();
        AboutAdapter adapter = new AboutAdapter(howto.howtouse);
        rv.setAdapter(adapter);
        //if(show == 1) show();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


}
