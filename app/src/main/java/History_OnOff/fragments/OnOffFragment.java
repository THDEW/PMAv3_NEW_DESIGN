package History_OnOff.fragments;

/**
 * Created by my131 on 26/4/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.example.senoir.newpmatry1.R;

import org.eclipse.paho.android.service.sample.Connection;
import org.eclipse.paho.android.service.sample.Connections;

import History_OnOff.fragments.on_off.OffFragment;
import History_OnOff.fragments.on_off.OnFragment;


public class OnOffFragment extends Fragment {
    private TabLayout tabLayout;
    private FragmentActivity myContext;
    private ViewPager viewPager;

    private String clientHandle;
    private Connection connection;
    private ChangeListener changeListener;

    public OnFragment onFragment;
    public OffFragment offFragment;

    View viewOn;

    public static Bundle bundle;

    public OnOffFragment() {

        // Required empty public constructor
    }

    public OnOffFragment(String clientHandle) {

        this.clientHandle = clientHandle;
        connection = Connections.getInstance(getActivity()).getConnection(clientHandle);
    }

    public void update(Activity context,Bundle bundle){
        if(viewPager != null) {


            FragmentActivity contextTemp = (FragmentActivity) context;
            FragmentManager fragManager = contextTemp.getSupportFragmentManager();
            ViewPagerAdapter adapter = new ViewPagerAdapter(fragManager);
            adapter.addFrag(new OnFragment(clientHandle), "ON");
            adapter.addFrag(new OffFragment(clientHandle), "OFF");
            Log.d("onOff", bundle.toString());
            this.bundle = bundle;
            Log.v("updateviewpager", "test");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

        }
    }

    public void update(Activity context){
        if(viewPager != null) {
            FragmentActivity contextTemp = (FragmentActivity) context;
            FragmentManager fragManager = contextTemp.getSupportFragmentManager();
            ViewPagerAdapter adapter = new ViewPagerAdapter(fragManager);;
            viewPager.setAdapter(adapter);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_on_off, container, false);
        viewOn = inflater.inflate(R.layout.fragment_on, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
//        setupViewPager(viewPager);


        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);


        return rootView;
    }




    public void setupViewPager(ViewPager viewPager) {
        Log.v("setupviewpager","test");
//        FragmentManager fragManager = myContext.getSupportFragmentManager();
//        ViewPagerAdapter adapter = new ViewPagerAdapter(fragManager);
//        adapter.addFrag(new OnFragment(clientHandle), "ON");
//        adapter.addFrag(new OffFragment(clientHandle), "OFF");
//        viewPager.setAdapter(adapter);

    }

    public ViewPager getViewPager()
    {
        return viewPager;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void updateData(){
            ((OnFragment) mFragmentList.get(0)).createDummyData();
            ((OffFragment) mFragmentList.get(1)).createDummyData();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(final Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    private class ChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {

        }
    }

}
