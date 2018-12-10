package com.perin.goods.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.perin.goods.R;
import com.perin.goods.data.Common;
import com.perin.goods.data.Location;
import com.perin.goods.fragment.BuildingDetailsFragment;


public class BuildingDetailsActivity extends AppCompatActivity {

    private static final String TAG = "BuildingDetailsActivity";

    private int iUpdateType = -1;
    private int locationPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();
        iUpdateType = args.getInt(Common.UPDATE_TYPE);
        locationPosition = args.getInt(Common.LOCATION_POSITION);

        setContentView(R.layout.activity_building_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_building_details, BuildingDetailsFragment.newInstance(locationPosition, iUpdateType))
                    .commitNow();
        }


        try {
            //add back button
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (NullPointerException npe) {
            Log.d(TAG, "onCreate: " + this.getClass().getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
