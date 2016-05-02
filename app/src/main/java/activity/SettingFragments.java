package activity;

/**
 * Created by my131 on 1/5/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.senoir.newpmatry1.R;

import java.util.ArrayList;

import adapter.DividerItemDecoration;
import adapter.RecyclerViewForSettingAdapter;
import dialog.LoginDialog;
import model.ItemDataModel;
import model.SectionDataModel;
import model.SingleItemModel;
import model.TableDataModel;


public class SettingFragments extends Fragment{

    private FragmentActivity myContext;
    ArrayList<TableDataModel> allSampleData;
    FragmentManager fm;
    String[] type;

    View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        Button reloginbt = (Button) rootView.findViewById(R.id.reloginbt);
        Button logout = (Button) rootView.findViewById(R.id.logout);

        fm = getFragmentManager();

        reloginbt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LoginDialog dialog = new LoginDialog(rootView);
                dialog.show(fm, "Login");
                dialog.setCancelable(false);

            }
        });

        type = new String[]{"Data Type", "Data Detail", "Power Node"
        ,"Location" ,"Group", "Device" };

        if(!Home.login){

            LoginDialog dialog = new LoginDialog(rootView);
            dialog.show(fm, "Login");
            dialog.setCancelable(false);

        } else {
            RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            my_recycler_view.setVisibility(View.VISIBLE);

            logout.setVisibility(View.VISIBLE);
        }


        allSampleData = new ArrayList<>();
        createDummyData();

        RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewForSettingAdapter adapter = new RecyclerViewForSettingAdapter(myContext, allSampleData, fm);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.addItemDecoration(new DividerItemDecoration(myContext, LinearLayoutManager.VERTICAL));

        my_recycler_view.setAdapter(adapter);
        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
                my_recycler_view.setVisibility(View.INVISIBLE);
                Button logoutinside = (Button) rootView.findViewById(R.id.logout);
                logoutinside.setVisibility(View.INVISIBLE);


                Button relogin = (Button) rootView.findViewById(R.id.reloginbt);
                relogin.setVisibility(View.VISIBLE);
                TextView plslogin = (TextView) rootView.findViewById(R.id.plsLogin);
                plslogin.setVisibility(View.VISIBLE);

                Home.login = false;

            }
        });



        return rootView;
    }

    public void createDummyData() {
        for (int i = 0; i < 6; i++) {

            TableDataModel dm = new TableDataModel();

            dm.setHeaderTitle(type[i]);

            ArrayList<ItemDataModel> singleItem = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                singleItem.add(new ItemDataModel(type[i] + " " + j, type[i]));

                switch (i){
                    case 0:
                        singleItem.get(j).addData("Data Type Name");
                        singleItem.get(j).addData("Description");
                        break;
                    case 1:
                        singleItem.get(j).addData("Data Type 5");
                        singleItem.get(j).addData("Brand");
                        singleItem.get(j).addData("Model");
                        singleItem.get(j).addData("Power_Watt");
                        singleItem.get(j).addData("BTN");
                        singleItem.get(j).addData("Description");
                        break;
                    case 2:
                        singleItem.get(j).addData("MAC Address");
                        singleItem.get(j).addData("IP Address");
                        singleItem.get(j).addData("Power Node name");
                        singleItem.get(j).addData("Description");
                        singleItem.get(j).addData("Location 4");
                        break;
                    case 3:
                        singleItem.get(j).addData("Location Name");
                        singleItem.get(j).addData("Description");
                        break;
                    case 4:
                        singleItem.get(j).addData("Pin");
                        singleItem.get(j).addData("Power Node 7");
                        singleItem.get(j).addData("Location 4");
                        singleItem.get(j).addData("Status");
                        singleItem.get(j).addData("Description");
                        break;
                    case 5:
                        singleItem.get(j).addData("Device Name");
                        singleItem.get(j).addData("Data Detail 4");
                        singleItem.get(j).addData("Group 5");
                        singleItem.get(j).addData("Description");
                        break;
                }

            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myContext = (FragmentActivity) activity;
    }
}
