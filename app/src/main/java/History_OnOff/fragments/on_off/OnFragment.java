package History_OnOff.fragments.on_off;

/**
 * Created by my131 on 26/4/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.senoir.newpmatry1.R;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import History_OnOff.adapter.RecyclerViewDataAdapter;
import History_OnOff.model.SectionDataModel;
import History_OnOff.model.SingleItemModel;
import adapter.DividerItemDecoration;


public class OnFragment extends Fragment{

    private FragmentActivity myContext;
    ArrayList<SectionDataModel> allSampleData;
    FragmentManager fm;

    public OnFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_on, container, false);

        allSampleData = new ArrayList<>();
        createDummyData();

        fm = getFragmentManager();

        RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(myContext, allSampleData, fm, true);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));

        my_recycler_view.setAdapter(adapter);

        return rootView;
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Location " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "a"+j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


}
