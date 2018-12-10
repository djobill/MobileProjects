package com.perin.goods.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.perin.goods.fragment.BuildingFragment;

import java.util.ArrayList;


public class BuildingAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "BuildingAdapter";
    private ArrayList<BuildingFragment> fragments = new ArrayList<BuildingFragment>();
    private BuildingFragment currentBuildingFragment;


    public BuildingAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // refresh all fragments when data set changed
        return FragmentStatePagerAdapter.POSITION_NONE;
    }

    public void addBuildingFragment(int position) {
        BuildingFragment buildingFragment = BuildingFragment.newInstance(position);
        fragments.add(buildingFragment);
        Log.d(TAG, "Building adapter: fragment " + position + " added.");

        return;
    }

    public void addBlankBuildingFragment() {
        // add a blank fragment
        BuildingFragment buildingFragment = new BuildingFragment();
        fragments.add(buildingFragment);
        notifyDataSetChanged();
        Log.d(TAG, "Building adapter: blank fragment " + 0 + " added.");

        return;
    }

    public void removeBuildingFragment(int position) {
        fragments.remove(position);
    }

    public void removeBuildingFragment(BuildingFragment fragment) {
        int pos = fragment.getPosition();
        fragments.remove(fragment);
        notifyDataSetChanged();
        Log.d(TAG, "Building adapter: fragment " + pos + " removed.");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Immeuble " + (position + 1) + "/" + getCount();
    }

    @Override
    public int getCount() {
        // Show all buildings, 1 per page.
        return fragments.size();
    }

    public void setCurrentFragment(BuildingFragment fragment) {
        currentBuildingFragment = fragment;
    }

    public ArrayList<BuildingFragment> getFragments() {
        return fragments;
    }

    public BuildingFragment getCurrentFragment() {
        return currentBuildingFragment;
    }
}
