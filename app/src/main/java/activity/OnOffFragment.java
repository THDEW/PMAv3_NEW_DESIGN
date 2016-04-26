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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.senoir.newpmatry1.R;
import fragments.OnFragment;
import fragments.OffFragment;


public class OnOffFragment extends Fragment {
    private TabLayout tabLayout;
    private FragmentActivity myContext;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_tab_offline,
            R.drawable.ic_tab_online
    };


    public OnOffFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_on_off, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return rootView;

    }

    private void setupTabIcons() {

        TextView tabOn = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOn.setText("ON");
        tabOn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_online, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOn);

        TextView tabOff = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOff.setText("OFF");
        tabOff.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_offline, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabOff);

    }

    private void setupViewPager(ViewPager viewPager) {
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
