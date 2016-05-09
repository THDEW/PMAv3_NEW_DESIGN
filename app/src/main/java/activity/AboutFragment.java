package activity;

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
import com.example.senoir.newpmatry1.R;

import java.util.List;

import adapter.RecyclerViewAboutAdapter;
import model.PersonModel;


public class AboutFragment extends Fragment {
    private FragmentActivity myContext;
    private PersonModel personModel;
    public AboutFragment() {
        // Required empty public constructor
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
        personModel = new PersonModel();
        personModel.initializeData();
        RecyclerViewAboutAdapter adapter = new RecyclerViewAboutAdapter(personModel.persons);
        rv.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }
}
