package com.perin.goods.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.perin.goods.data.Common;
import com.perin.goods.fragment.BuildingDetailsFragment;

public class BuildingDetailsAdapter extends FragmentStatePagerAdapter {

    public BuildingDetailsAdapter(FragmentManager fm) {
        super( fm );
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
//        Bundle args = new Bundle();
//        args.putInt( MainActivity.LOCATION_POSITION, position );
        return BuildingDetailsFragment.newInstance( Common._UPDATE, position );
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 1;
    }
}
