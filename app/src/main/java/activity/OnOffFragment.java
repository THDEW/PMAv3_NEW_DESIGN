package activity;

/**
 * Created by my131 on 26/4/2559.
 */
import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.senoir.newpmatry1.R;


import fragments.OnFragment;
import fragments.OffFragment;
import fragments.YearlyFragment;


public class OnOffFragment extends Fragment {
    private TabLayout tabLayout;
    private FragmentActivity myContext;
    private ViewPager viewPager;


    public OnOffFragment() {

        // Required empty public constructor
    }
    public void update(Activity context){
        if(viewPager != null) {
            FragmentActivity contextTemp = (FragmentActivity) context;
            FragmentManager fragManager = contextTemp.getSupportFragmentManager();
            ViewPagerAdapter adapter = new ViewPagerAdapter(fragManager);
            adapter.addFrag(new OnFragment(), "ON");
            adapter.addFrag(new OffFragment(), "OFF");
            viewPager.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_on_off, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        return rootView;
    }




    public void setupViewPager(ViewPager viewPager) {
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragManager);
        adapter.addFrag(new OnFragment(), "ON");
        adapter.addFrag(new OffFragment(), "OFF");
        viewPager.setAdapter(adapter);
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

}
