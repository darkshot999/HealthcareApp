package uop.mbl404_team_b.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import uop.mbl404_team_b.fragments.TabContacts;
import uop.mbl404_team_b.fragments.TabLocations;
import uop.mbl404_team_b.fragments.TabSearch;

public class TabNavAdapter extends FragmentPagerAdapter {
    int tabCount;

    public TabNavAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabSearch tab1 = new TabSearch();
                return tab1;
            case 1:
                TabLocations tab2 = new TabLocations();
                return tab2;
            case 2:
                TabContacts tab3 = new TabContacts();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    private String[] tabTitles = new String[]{"Search", "Locations", "Contact"};

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
