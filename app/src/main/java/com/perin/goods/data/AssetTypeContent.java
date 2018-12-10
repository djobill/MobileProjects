package com.perin.goods.data;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Singleton class managing the Assets
 * <p>
 * Point d'accès pour l'instance unique du singleton
 */

public class AssetTypeContent implements Serializable {
    // separator used in the assets file
    private static String FILESEPARATOR = File.separator;
    private static boolean IS_ASSET_EMPTY = false;
    /**
     * class managing the different Assets'information of all locations
     */
    private static String ASSETFILENAME = "";
    private static Context CONTEXT;

    public static ArrayList<AssetType> ASSETS = new ArrayList<AssetType>();
    private static final int COUNT = 10;
    private static File FILE;

    /**
     * Constructeur privé
     */
    private AssetTypeContent() {
    }

    private static final AssetTypeContent INSTANCE = new AssetTypeContent();

    public static AssetTypeContent getInstance(Context context,
                                               String locationsDirectoryName) {
        if (!IS_ASSET_EMPTY) {
            CONTEXT = context;
            ASSETFILENAME = locationsDirectoryName;
            INSTANCE.readAssetFile();
            IS_ASSET_EMPTY = true;
        }
        return INSTANCE;
    }

    /*
    read the file containing all the assets and populate the list
     */

    private void readAssetFile() {
        // The name of the file to open.
//        String fileNameRoot = ((File) CONTEXT.getExternalFilesDir( LOCATIONFILENAME )).getAbsolutePath();
//        FILE = new File( ((File) CONTEXT.getFilesDir()).getAbsolutePath(), LOCATIONFILENAME );

        ArrayList<AssetType> assets = new ArrayList<>();
        FILE = new File( ((File) CONTEXT.getExternalFilesDir( null )).getAbsolutePath(), ASSETFILENAME );
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
            FileReader fileReader = new FileReader( FILE );

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader( fileReader );

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println( "***** ***** " + line );
                // parse the line and create location

                assets.add( createAsset( AssetType.parseAssetLine( line ) ) );
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println( "***** ***** Unable to open file '" + ASSETFILENAME + "'" );
            System.out.println( "***** ***** We're gonna create one file '" + ASSETFILENAME + "'" );

        } catch (IOException ex) {
            System.out.println( "Error reading file '" + ASSETFILENAME + "'" );
            // Or we could just do this:
            ex.printStackTrace();
        }
        ASSETS = assets;
    }

    public static void saveAssets() {

        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter( FILE );
            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

            // loop over the locations, parse into xml and then write it
            // Note that write() does not automatically append a newline character.
            for (int i = 0; i < ASSETS.size(); i++) {
                System.out.println( "Count is: " + i );
                bufferedWriter.write( ASSETS.get( i ).writeAssetToXML( ASSETS.get( i ) ).toString() );
                bufferedWriter.newLine();
            }
            // Always close files.
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println( "***** ***** Error writing to file '" + FILE.getName() + "'" );
            ex.printStackTrace();
        }
    }

    private AssetType createAsset(ArrayList<String> sAsset) {
        System.out.println( sAsset.toString() );

        return (new AssetType(
                sAsset.get( 0 ),
                sAsset.get( 1 ),
                sAsset.get( 2 ),
                sAsset.get( 3 ),
                sAsset.get( 4 ),
                sAsset.get( 5 ),
                sAsset.get( 6 ) ));
    }

    public static ArrayList<AssetType> getASSETS() {
        return ASSETS;
    }
}