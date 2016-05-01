package activity;

/**
 * Created by my131 on 1/5/2559.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senoir.newpmatry1.R;

import java.util.ArrayList;

import adapter.RecyclerViewForSettingAdapter;
import dialog.LoginDialog;
import model.SectionDataModel;
import model.SingleItemModel;


public class SettingFragments extends Fragment{

    private FragmentActivity myContext;
    ArrayList<SectionDataModel> allSampleData;
    FragmentManager fm;
    String[] type;

    public SettingFragments() {
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
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        type = new String[]{"Data Type", "Data Detail", "Power Node"
        ,"Location" ,"Group of Device", "Device" };

        FragmentManager fm = getFragmentManager();
        LoginDialog dialog = new LoginDialog();
        dialog.show(fm, "Login");

        allSampleData = new ArrayList<>();
        createDummyData();

        RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);

        RecyclerViewForSettingAdapter adapter = new RecyclerViewForSettingAdapter(myContext, allSampleData, fm);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);

        return rootView;
    }

    public void createDummyData() {
        for (int i = 0; i < 6; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(type[i]);

            ArrayList<SingleItemModel> singleItem = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "a"+j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

}
