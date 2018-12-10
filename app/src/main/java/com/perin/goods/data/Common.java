package com.perin.goods.data;

/**
 * Created by AndroidStudio on 20/02/2018.
 */

public class Common {
    // Type of updtate:
    //  -1 - No actions
    //  1 - add a new item
    //  2 - update the item
    public static final int _NONE = -1;
    public static final int _ADD = 1;
    public static final int _UPDATE = 2;
    public static final int _REMOVE = 3;
    public static final int _SAVE = 4;
    public static final int CAMERA_REQUEST = 1;

    // Used to know if the locations need to be saved
//    public static final boolean isSaveLocations = false;

    // Locations
    public static final String LOCATION_POSITION = "LocationPosition";
    public static final String UPDATE_TYPE = "UpdtateType";
    public static final String LOCATIONFILENAME = "location.xml";


// Assets
//    public static AssetType currentAsset = null;
    //public static AssetRecyclerViewAdapter assetAdapter;
}
