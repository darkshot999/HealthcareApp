package uop.mbl404_team_b;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import uop.mbl404_team_b.helpers.DBHelper;
import uop.mbl404_team_b.helpers.TabNavAdapter;

public class MainActivity extends AppCompatActivity {
    public static ViewPager viewPager;

    public static SharedPreferences preferenceSettings;
    public static SharedPreferences.Editor preferenceEditor;
    public static DBHelper dbHelper;
    public static Activity m_context;

    private static final int PREFERENCE_MODE_PRIVATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        m_context = this;

        this.deleteDatabase("clinics.db");
        dbHelper = new DBHelper(this);
        dbHelper.open();

        /*preferenceEditor.clear();
        preferenceEditor.commit();*/

        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Locations"));
        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new TabNavAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    public static void setCurrentTab(int tabNum){
        viewPager.setCurrentItem(tabNum);
    }
}
