package com.perin.goods.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.perin.goods.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Singleton class managing the different locations of which we want to ic_save the assets' information
 * <p>
 * Point d'accès pour l'instance unique du singleton
 */

public class LocationContent implements Serializable {
    private static final int COUNT = 10;
    private static final LocationContent INSTANCE = new LocationContent();
    public static String IMAGE_FILENAME = "location@.bmp";
    //        String fileName = ((File) CONTEXT.getExternalFilesDir( LOCATIONFILENAME )).getAbsolutePath();
    //        String cacheDir = ((File) CONTEXT.getCacheDir()).getAbsolutePath();
    private static ArrayList<Location> LOCATIONS = new ArrayList<Location>();
    private static Location CURRENT_LOCATION = null;
    private static Location BACKUP_LOCATION = null;
    // separator used in the locations file
    private static String FILESEPARATOR = File.separator;
    private static boolean ISLOCEMPTY = false;
    /**
     * class managing the different locations of which we want to ic_save the assets' information
     */
    private static String LOCATIONFILENAME = "";
    private static Context CONTEXT;
    //    private static Location CURRENT_LOCATION = null;
    private static int CURRENT_POSITION = -1;
    private static File FILE;

    /**
     * Constructeur privé
     */
    private LocationContent() {
    }

    public static Location getCurrentLocation() {
        return CURRENT_LOCATION;
    }

    public static Location getLocationToUpdate() {
        return (Location) getCurrentLocation().clone();
    }

    public static void setCurrentLocation(Location currentLocation) {
        CURRENT_LOCATION = currentLocation;
    }

    public static void getInstance(Context context,
                                   String locationsDirectoryName) {
        if (!ISLOCEMPTY) {
            CONTEXT = context;
            LOCATIONFILENAME = locationsDirectoryName;
            INSTANCE.readLocationFile();
            ISLOCEMPTY = true;
        }
    }

    /*
    read the file containing all the locations and populate the list
     */

    public static void saveLocations() {

        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(FILE);
            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // loop over the locations, parse into xml and then write it
            // Note that write() does not automatically append a newline character.
            for (int i = 0; i < LOCATIONS.size(); i++) {
                System.out.println("Count is: " + i);
                bufferedWriter.write(LOCATIONS.get(i).writeLocationToXML(LOCATIONS.get(i)).toString());
                bufferedWriter.newLine();
                saveImage(LOCATIONS.get(i), i);
            }
            // Always close files.
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("***** ***** Error writing to file '" + FILE.getName() + "'");
            ex.printStackTrace();
        }
    }

    private static void saveImage(Location location, int i) {
        if (location.getImageLocation() != null) {
            String filename = IMAGE_FILENAME.replaceAll("@", String.valueOf(i));
            File imageFile = new File(((File) CONTEXT.getExternalFilesDir(null)).getAbsolutePath(), filename);
            if (!imageFile.exists()) {
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                location.getImageLocation().compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(imageFile));
            } catch (FileNotFoundException e) {
                System.out.println(LocationContent.class.getName() + " Impossible to ic_save the image location");
                e.printStackTrace();
            }
        } else {
            System.out.println(LocationContent.class.getName() + " image location is null");
        }
    }

    public static ArrayList<Location> getLOCATIONS() {
        return LOCATIONS;
    }

    public static Location findLocation(String slocation) {
        Location location = null;
        String[] loc = slocation.split(";");
        String address = loc[0];
        String city = loc[1];
        String posCode = loc[2];
        String resType = loc[3];
        for (int i = 0; i < LOCATIONS.size(); i++) {
            location = LOCATIONS.get(i);
            if (location.getAddress().equalsIgnoreCase(address) &&
                    location.getCity().equalsIgnoreCase(city) &&
                    location.getPostalCode().equalsIgnoreCase(posCode) &&
                    location.getResidenceType().equalsIgnoreCase(resType)
                    ) {
                return location;
            }
        }
        return location;
    }

    public static void addLocation(Location loc) {

        LocationContent.getLOCATIONS().add(loc);
        // clone the current location to be the next backup
        if (getCurrentLocation() != null)
            BACKUP_LOCATION = (Location) getCurrentLocation().clone();
        setCurrentLocation(loc);
    }

    private void readLocationFile() {
        // The name of the file to open.
//        String fileNameRoot = ((File) CONTEXT.getExternalFilesDir( LOCATIONFILENAME )).getAbsolutePath();
//        FILE = new File( ((File) CONTEXT.getFilesDir()).getAbsolutePath(), LOCATIONFILENAME );

        ArrayList<Location> locations = new ArrayList<>();
        FILE = new File(((File) CONTEXT.getExternalFilesDir(null)).getAbsolutePath(), LOCATIONFILENAME);
        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // This will reference one line at a time
        String line;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(FILE);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int row = -1;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("***** ***** " + line);
                row++;
                // parse the line and create location
                Location curLocation = createLocation(Location.parseLocationLine(line));
                locations.add(curLocation);

                // find the location image
                String filename = IMAGE_FILENAME.replaceAll("@", String.valueOf(row));
                Bitmap bitmap = readLocationImage(filename);
                curLocation.setImageLocation(bitmap);
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("***** ***** Unable to open file '" + LOCATIONFILENAME + "'");
            System.out.println("***** ***** We're gonna create one file '" + LOCATIONFILENAME + "'");

        } catch (IOException ex) {
            System.out.println("Error reading file '" + LOCATIONFILENAME + "'");
            // Or we could just do this:
            ex.printStackTrace();
        }
        LOCATIONS = locations;
    }

    private Bitmap readLocationImage(String filename) {
        Bitmap bitmap = null;
        File imageFile = new File(((File) CONTEXT.getExternalFilesDir(null)), filename);
        if (!imageFile.exists()) {
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileInputStream inputStreamImage = null;
        try {
            inputStreamImage = new FileInputStream(new File(imageFile.getPath()));
            bitmap = BitmapFactory.decodeStream(inputStreamImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("***** ***** Test - image file is null");
            bitmap = BitmapFactory.decodeResource(CONTEXT.getResources(), R.drawable.ic_menu_camera);
        }
        return bitmap;
    }

    private Location createLocation(ArrayList<String> sLocation) {
        System.out.println(sLocation.toString());
        return (new Location(sLocation.get(0), sLocation.get(1), sLocation.get(2),
                sLocation.get(3), false));
    }
}