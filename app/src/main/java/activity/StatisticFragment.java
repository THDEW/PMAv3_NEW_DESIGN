package activity;

/**
 * Created by my131 on 26/4/2559.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fragments.DailyFragment;
import fragments.MonthlyFragment;
import fragments.YearlyFragment;

import com.example.senoir.newpmatry1.R;


public class StatisticFragment extends Fragment {
    private FragmentActivity myContext;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    AdapterView<?> parent;

    public StatisticFragment() {
        // Required empty public constructor
    }

    public void updateStatistic(Activity context, int index){
        if(viewPager != null) {
            FragmentActivity contextTemp = (FragmentActivity) context;
            FragmentManager fragManager = contextTemp.getSupportFragmentManager();
            viewPagerAdapter = new ViewPagerAdapter(fragManager);
            viewPagerAdapter.addFragment(new DailyFragment(), "Daily");
            viewPagerAdapter.addFragment(new MonthlyFragment(), "Monthly");
            viewPagerAdapter.addFragment(new YearlyFragment(), "Yearly");
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(index);
        }
    }

    public int getCurrentPosition(){
        if(viewPager != null) {
            return viewPager.getCurrentItem();
        } else {
            return 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.statistic_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.statistic_tabs);
        tabLayout.setupWithViewPager(viewPager);


        return rootView;
    }


    private void setupViewPager(ViewPager viewPager) {
        Activity context;
        context = (FragmentActivity) getActivity();
        FragmentActivity contextTemp = (FragmentActivity) context;
        FragmentManager fragManager = contextTemp.getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fragManager);
        viewPagerAdapter.addFragment(new DailyFragment(), "Daily");
        viewPagerAdapter.addFragment(new MonthlyFragment(), "Monthly");
        viewPagerAdapter.addFragment(new YearlyFragment(), "Yearly");
        viewPager.setAdapter(viewPagerAdapter);
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myContext = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
