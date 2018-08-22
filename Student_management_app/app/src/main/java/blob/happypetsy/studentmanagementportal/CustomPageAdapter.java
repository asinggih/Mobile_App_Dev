
package blob.happypetsy.studentmanagementportal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<Fragment>();

    public CustomPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        return fragmentList.get(pos);       // index of the arrayList links to the corresponding fragments
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }
}
