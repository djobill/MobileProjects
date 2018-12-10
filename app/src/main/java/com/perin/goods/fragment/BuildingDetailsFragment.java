package com.perin.goods.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.perin.goods.R;
import com.perin.goods.data.Common;
import com.perin.goods.data.Location;
import com.perin.goods.data.LocationContent;

import static android.Manifest.permission;

public class BuildingDetailsFragment extends Fragment {
    private static final String TAG = "BuildingDetailsFragment";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    public ImageView ivLocationThumbnail;
    private int iUpdateType;
    private int iPosition;
    private Location currentLocation = null;
    private Location backupLocation = null;
    private boolean updatePossible = false;
    private View fLocDetailsView;

    public BuildingDetailsFragment() {
        // Required empty public constructor
    }

    public static BuildingDetailsFragment newInstance(int locationPosition, int iUpdateType) {
        BuildingDetailsFragment fragment = new BuildingDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Common.LOCATION_POSITION, locationPosition);
        args.putInt(Common.UPDATE_TYPE, iUpdateType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            iPosition = getArguments().getInt(Common.LOCATION_POSITION);
            iUpdateType = getArguments().getInt(Common.UPDATE_TYPE);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d(TAG, "onActivityResult: RESULT_CANCELED");

        } else if (requestCode == Common.CAMERA_REQUEST) {
            Bitmap bitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");
            currentLocation.setImageLocation(bitmap);
            ivLocationThumbnail.setImageBitmap(bitmap);
            updatePossible = true;
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    takePicture();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "Taking a picture is not allowed", Toast.LENGTH_LONG).show();

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void takePicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Common.CAMERA_REQUEST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fLocDetailsView = inflater.inflate(R.layout.fragment_building_details, container, false);

        // Camera
        ivLocationThumbnail = (ImageView) fLocDetailsView.findViewById(R.id.ivLocationThumbnail);
        ivLocationThumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int hasCameraPermission = getActivity().checkSelfPermission(permission.CAMERA);
                // Permission not granted. Ask for permission
                if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{permission.CAMERA},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    return;
                }
                // Permission already granted, so take the picture.
                takePicture();
            }

        });

        if (iUpdateType == Common._UPDATE) {
            // Update a clone of the existing location
            currentLocation = LocationContent.getLocationToUpdate();
        } else if (iUpdateType == Common._ADD) {
            // Add new location
            currentLocation = new Location();
        }

        //set the listeners to know when the text has changed
        final TextView tvAddress = fLocDetailsView.findViewById(R.id.etAddress);
        tvAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                BuildingDetailsFragment.this.currentLocation.setAddress(tvAddress.getText().toString());
                updatePossible = true;
            }
        });

        final TextView tvCity = fLocDetailsView.findViewById(R.id.etCity);
        tvCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                BuildingDetailsFragment.this.currentLocation.setCity(tvCity.getText().toString());
                updatePossible = true;
            }
        });

        final TextView tvPostalCode = fLocDetailsView.findViewById(R.id.etPostalCode);
        tvPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                BuildingDetailsFragment.this.currentLocation.setPostalCode(tvPostalCode.getText().toString());
                updatePossible = true;
            }
        });

        final RadioGroup rbResidenceType = ((RadioGroup) fLocDetailsView.findViewById(R.id.rgResType));
        final RadioButton rbResidencePrincipal = (RadioButton) fLocDetailsView.findViewById(R.id.rbPrincipal);
        rbResidenceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int rb = radioGroup.getCheckedRadioButtonId();
                String res = "P";
                if (rb != rbResidencePrincipal.getId())
                    res = "S";
                BuildingDetailsFragment.this.currentLocation.setResidenceType(res);
                updatePossible = true;
            }
        });

        final Button btnSave = fLocDetailsView.findViewById(R.id.save_building_details);
        btnSave.setOnClickListener(new View.OnClickListener() {
            int pos = -1;

            @Override
            public void onClick(View v) {
                if (iUpdateType == Common._ADD)
                    LocationContent.addLocation(currentLocation);
                else if (iUpdateType == Common._UPDATE) {

                    pos = LocationContent.getLOCATIONS().indexOf(LocationContent.getCurrentLocation());
                    LocationContent.getLOCATIONS().set(pos, currentLocation);
                    LocationContent.setCurrentLocation(currentLocation);
                }
                //Refresh the view
                tvAddress.setText(currentLocation.getAddress());
                tvCity.setText(currentLocation.getCity());
                tvPostalCode.setText(currentLocation.getPostalCode());
                if (currentLocation.getResidenceType().contentEquals("P")) {
                    rbResidenceType.check(R.id.rbPrincipal);
                } else {
                    rbResidenceType.check(R.id.rbSecondary);
                }
                if (currentLocation.getImageLocation() != null) {
                    ivLocationThumbnail.setImageBitmap(currentLocation.getImageLocation());
                }

                LocationContent.saveLocations();
            }
        });

        //update the view with the location values
        if (currentLocation != null) {
            tvAddress.setText(currentLocation.getAddress());
            tvCity.setText(currentLocation.getCity());
            tvPostalCode.setText(currentLocation.getPostalCode());
            if (currentLocation.getResidenceType().contentEquals("P")) {
                rbResidenceType.check(R.id.rbPrincipal);
            } else {
                rbResidenceType.check(R.id.rbSecondary);
            }
            if (currentLocation.getImageLocation() != null) {
                ivLocationThumbnail.setImageBitmap(currentLocation.getImageLocation());
            }
        }
        return fLocDetailsView;
    }
}