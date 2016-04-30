package activity;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.senoir.newpmatry1.R;



public class Home extends  AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    public static int page = 0;

    OnOffFragment fragment = null;
    LocationFragment fragment0 = null;
    StatisticFragment fragment1 = null;
    ElectricityBillFragment fragment2 = null;
    SettingFragments fragment3 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbars);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        // display the first navigation drawer view on app launch
        fragment = new OnOffFragment();
        fragment0 = new LocationFragment();
        fragment1 = new StatisticFragment();
        fragment2 = new ElectricityBillFragment();
        fragment3 = new SettingFragments();
        displayView(1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        if(position == 0){
            fragment.update(this);
        }
        if(position == 2){
            fragment1.updateStatistic(this);
        }
        if(position != 0) {
            LocationFragment.data.clear();
        }

        displayView(position);
    }
    private void displayView(int position) {

        String title = getString(R.string.app_name);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                fragmentTransaction.replace(R.id.container_body, fragment);
                title = getString(R.string.title_home);
                page = 0;
                break;
            case 1:
                fragmentTransaction.replace(R.id.container_body, fragment0);
                title = getString(R.string.title_Location);
                page = 1;
                break;
            case 2:
                fragmentTransaction.replace(R.id.container_body, fragment1);
                title = getString(R.string.title_statistic);
                page = 2;
                break;
            case 3:
                fragmentTransaction.replace(R.id.container_body, fragment2);
                title = "Electricity Bill";
                page = 3;
                break;
            case 4:
                fragmentTransaction.replace(R.id.container_body, fragment3);
                title = "Setting";
            default:
                break;
        }
        getSupportActionBar().setTitle(title);
        fragmentTransaction.commit();
    }

}
