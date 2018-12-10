package com.perin.goods.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.perin.goods.R;
import com.perin.goods.adapter.BuildingAdapter;
import com.perin.goods.data.Common;
import com.perin.goods.data.Location;
import com.perin.goods.data.LocationContent;
import com.perin.goods.fragment.BuildingDetailsFragment;
import com.perin.goods.fragment.BuildingFragment;

import java.util.List;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int savedAction = -1;

    public BuildingAdapter getBuildingsPagerAdapter() {
        return mBuildingsPagerAdapter;
    }

    private BuildingAdapter mBuildingsPagerAdapter;
    // The {@link ViewPager} that will host the section contents.
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "On create: Started");
        // load the buildings
        LocationContent.getInstance(this.getBaseContext(), Common.LOCATIONFILENAME);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Circular Menu
        createCircularMenu();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        // Create the adapter that will return a fragment for each building
        mBuildingsPagerAdapter = new BuildingAdapter(getSupportFragmentManager());
        mBuildingsPagerAdapter.notifyDataSetChanged();

        // For each building, we create a new fragement
        setUpViewPager();
        mViewPager.setAdapter(mBuildingsPagerAdapter);
        //Sets the section
        //int nSections = mBuildingsPagerAdapter.getCount();
        if (mBuildingsPagerAdapter.getCount() == 0) {
            // No buildings to show. Show a blank fragment
            mBuildingsPagerAdapter.addBlankBuildingFragment();
        }

        mViewPager.setCurrentItem(0);

    }

    private void setUpViewPager() {
        int nBuildings = LocationContent.getLOCATIONS().size();
        for (int i = 0; i < nBuildings; i++) {
            mBuildingsPagerAdapter.addBuildingFragment(i);
        }
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
    // Circular Menu

    private void createCircularMenu() {
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.ic_buildings);

        final FloatingActionButton floatingActionButton = new FloatingActionButton.Builder(this).setContentView(icon).build();
        floatingActionButton.setTooltipText(getBaseContext().getString(R.string.title_activity_editing_building));

        SubActionButton.Builder builder = new SubActionButton.Builder(this);
        builder.setLayoutParams(new FloatingActionButton.LayoutParams(180, 180));

        ImageView addIcon = new ImageView(this);
        addIcon.setImageResource(R.drawable.ic_add_building);
        SubActionButton addButton = builder.setContentView(addIcon, (FloatingActionButton.LayoutParams) null).build();
        addButton.setTooltipText("Add a new Building");

        ImageView removeIcon = new ImageView(this);
        removeIcon.setImageResource(R.drawable.ic_remove_building);
        SubActionButton removeButton = builder.setContentView(removeIcon, (FloatingActionButton.LayoutParams) null).build();
//        removeButton.setTooltipText( "Remove selected Building" );

        ImageView editIcon = new ImageView(this);
        editIcon.setImageResource(R.drawable.ic_edit_building);
        SubActionButton editButton = builder.setContentView(editIcon, (FloatingActionButton.LayoutParams) null).build();
//        editButton.setTooltipText( "Edit selected building" );

        ImageView saveIcon = new ImageView(this);
        saveIcon.setImageResource(R.drawable.ic_save);
        SubActionButton saveButton = builder.setContentView(saveIcon, (FloatingActionButton.LayoutParams) null).build();
//        saveButton.setTooltipText( "Save" );

        final FloatingActionMenu floatingActionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(addButton)
                .addSubActionView(removeButton)
                .addSubActionView(editButton)
                .addSubActionView(saveButton)
                .attachTo(floatingActionButton)
                .build();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "add button clicked", Toast.LENGTH_SHORT).show();
                floatingActionMenu.close(true);

                Intent intent = new Intent(MainActivity.this, BuildingDetailsActivity.class);
                Bundle args = new Bundle();
                args.putInt(Common.LOCATION_POSITION, -1);
                args.putInt(Common.UPDATE_TYPE, Common._ADD);
                intent.putExtras(args);
                startActivity(intent, args);
                savedAction = Common._ADD;
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "adit button clicked", Toast.LENGTH_SHORT).show();
                floatingActionMenu.close(true);
                savedAction = Common._UPDATE;
                List<BuildingFragment> fragments = mBuildingsPagerAdapter.getFragments();

                try {
                    if (mViewPager.getCurrentItem() >= 0) {
                        BuildingFragment myFragment = (BuildingFragment) fragments.get(mViewPager.getCurrentItem());
                        mBuildingsPagerAdapter.setCurrentFragment(myFragment);
                        LocationContent.setCurrentLocation(myFragment.getCurrentLocation());
                    }

                } catch (ClassCastException cce) {

                }

                // will handle an existing location. To do so, start a new activity
                Intent intent = new Intent(MainActivity.this, BuildingDetailsActivity.class);
                Bundle args = new Bundle();
                args.putInt(Common.LOCATION_POSITION, LocationContent.getLOCATIONS()
                        .indexOf(LocationContent.getCurrentLocation()));
                args.putInt(Common.UPDATE_TYPE, Common._UPDATE);
                intent.putExtras(args);
                startActivity(intent, args);
                mViewPager.getAdapter().notifyDataSetChanged();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "remove button clicked", Toast.LENGTH_SHORT).show();
                floatingActionMenu.close(true);
                savedAction = Common._REMOVE;
                List<BuildingFragment> fragments = mBuildingsPagerAdapter.getFragments();

                if (mViewPager.getCurrentItem() >= 0) {
                    BuildingFragment myFragment = (BuildingFragment) fragments.get(mViewPager.getCurrentItem());

                    LocationContent.getLOCATIONS().remove(LocationContent.getCurrentLocation());
                    LocationContent.setCurrentLocation(null);
                    mBuildingsPagerAdapter.removeBuildingFragment(myFragment);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "save button clicked", Toast.LENGTH_SHORT).show();
                floatingActionMenu.close(true);
                LocationContent.saveLocations();
                mViewPager.getAdapter().notifyDataSetChanged();
                savedAction = Common._SAVE;
            }
        });
    }

    @Override
    protected void onResume() {
        if (savedAction == Common._ADD) {
            mBuildingsPagerAdapter.addBuildingFragment(mBuildingsPagerAdapter.getCount());
        }

        mBuildingsPagerAdapter.notifyDataSetChanged();
        super.onResume();

    }
}
