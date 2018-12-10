package com.perin.goods.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.perin.goods.R;
import com.perin.goods.data.Location;
import com.perin.goods.data.LocationContent;


public class BuildingFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    Location currentLocation = null;

    private int mPosition = -1;
    private View fLocDetailsView;

    public BuildingFragment() {
        // Required empty public constructor
    }

    public static BuildingFragment newInstance(int param1) {
        BuildingFragment fragment = new BuildingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setPosition(getArguments().getInt(ARG_POSITION));
            if (getPosition() >= 0 && getPosition() < LocationContent.getLOCATIONS().size()) {
                setCurrentLocation(LocationContent.getLOCATIONS().get(getPosition()));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fLocDetailsView =
                inflater.inflate(R.layout.fragment_building, container, false);
        fillFragmentDetails(fLocDetailsView);
        return fLocDetailsView;
    }


    private void fillFragmentDetails(View fragment) {


        // Camera
        final ImageView ivLocationThumbnail = fragment.findViewById(R.id.building_thumbnail);
        final TextView tvBuilding = fragment.findViewById(R.id.building_textview);

        //update the view with the location values
        if (currentLocation != null) {
            String cRLF = System.getProperty("line.separator");
            String text = getCurrentLocation().getAddress() + cRLF
                    + getCurrentLocation().getCity() + " - "
                    + getCurrentLocation().getPostalCode();
            tvBuilding.setText(text);

            if (getCurrentLocation().getImageLocation() != null) {
                ivLocationThumbnail.setImageBitmap(getCurrentLocation().getImageLocation());
            }
//            else {
//                ivLocationThumbnail.setImageBitmap(null);
//                ivLocationThumbnail.setImageBitmap(null);
//            }
        }
    }

    @Override
    public void onDestroyView() {

//        ViewPager mViewPager = (ViewPager) this.getActivity().findViewById(R.id.container);
//        mViewPager.getAdapter().notifyDataSetChanged();
        super.onDestroyView();
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        if (fLocDetailsView != null)
            fillFragmentDetails(fLocDetailsView);
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

}